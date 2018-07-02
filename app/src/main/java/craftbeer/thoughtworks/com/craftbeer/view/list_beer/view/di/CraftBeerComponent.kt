package craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.di

import craftbeer.thoughtworks.com.craftbeer._core._di.scope.ViewScope
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.ListBeer
import dagger.Module
import dagger.Subcomponent

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
@ViewScope
@Subcomponent(modules = arrayOf(CraftBeerModule::class))
interface CraftBeerComponent {

    fun inject(listBeer: ListBeer)
}