package craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.di

import craftbeer.thoughtworks.com.craftbeer._core._di.scope.ViewScope
import craftbeer.thoughtworks.com.craftbeer._core.api.BeerListApi
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.db.DBAdapter
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.presenter.CraftBeerPresenter
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.presenter.CraftedBeerImpl
import dagger.Module
import dagger.Provides

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
@ViewScope
@Module
class CraftBeerModule{

    @Provides
    fun provideCraftedBeerImpl( dbAdapter: DBAdapter,beerListApi: BeerListApi):CraftBeerPresenter{

        return CraftedBeerImpl(beerListApi,dbAdapter)

    }
}