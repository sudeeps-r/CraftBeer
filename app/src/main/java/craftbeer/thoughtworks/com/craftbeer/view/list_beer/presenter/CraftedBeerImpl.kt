package craftbeer.thoughtworks.com.craftbeer.view.list_beer.presenter

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.util.Log
import craftbeer.thoughtworks.com.craftbeer._core.api.BeerListApi
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.db.DBAdapter
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.CraftBeer
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class CraftedBeerImpl(var beerListApi: BeerListApi, var dbAdapter: DBAdapter) : CraftBeerPresenter{


    private lateinit var view: CraftBeer
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun fetCraftedBeer() {

        this.view.let {
            view?.showLoader()
            val disposable: Disposable = this.beerListApi.getCraftedBeer()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(view.getScheduler())
                    .subscribeWith(object: DisposableObserver<List<craftbeer.thoughtworks.com.craftbeer._core.model.CraftBeer>>(){
                        override fun onComplete() {
                            Log.e("beer","Oncomplete")
                            view?.hideLoader()
                        }

                        override fun onNext(value: List<craftbeer.thoughtworks.com.craftbeer._core.model.CraftBeer>) {
                            Log.e("beer",">>"+value?.size)
                            value.let {
                                dbAdapter.updateBeerList(value)
                            }
                            view?.hideLoader()
                        }

                        override fun onError(e: Throwable?) {
                            e?.printStackTrace()
                            view?.showError(e?.message)
                            view?.hideLoader()
                        }

                    });

            this.compositeDisposable.add(disposable)

        }
    }

    override fun attachView(view: CraftBeer) {
        this.view = view
    }

    override fun detachView() {
    if(!compositeDisposable.isDisposed){
        compositeDisposable.dispose()
    }
    }
}