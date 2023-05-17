package com.example.pawpal.screens.home.head_page

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.pawpal.core.App
import com.example.pawpal.core.BaseViewModel
import com.example.pawpal.data.network.NetworkManager
import com.example.pawpal.entity.Banner
import com.example.pawpal.screens.home.head_page.entity.Item
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class HeadViewModel(application: Application) : BaseViewModel(application) {
    private val networkManager: NetworkManager
    val progressLD = MutableLiveData(false)
    val foodLD = MutableLiveData<List<Item>>()
    val toysLD = MutableLiveData<List<Item>>()
    val bannerLD = MutableLiveData<Banner>()

    init {
        networkManager = App.getInstance(application.applicationContext).manager
    }

    fun loadData() {
        val disposable =
            Single.zip(
                networkManager.getFood(),
                networkManager.getToys(),
                networkManager.getBanner()
            ) { food, toys, banner ->
                Triple(food, toys, banner)
            }
                .map { (food, toys, banner) ->
                    Triple(
                        toItem(food) { Item.FoodItem(it) },
                        toItem(toys) { Item.ToyItem(it) },
                        banner
                    )
                }
                .delay(3, TimeUnit.SECONDS)
                .doOnSubscribe { progressLD.postValue(true) }
                .doFinally { progressLD.postValue(false) }
                .subscribe { (food, toys, banner) ->
                    foodLD.postValue(food)
                    toysLD.postValue(toys)
                    bannerLD.postValue(banner)
                }
        compositeDisposable.add(disposable)
    }

    private fun <T> toItem(objects: List<T>, mapper: (T) -> Item): List<Item> {
        return if (objects.size <= MAX_SIZE) {
            objects.map(mapper)
        } else {
            buildList {
                addAll(objects.take(MAX_SIZE).map(mapper))
                add(Item.SeeAll)
            }
        }
    }

    companion object {
        private const val MAX_SIZE = 3
    }
}
