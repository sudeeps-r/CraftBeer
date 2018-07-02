package craftbeer.thoughtworks.com.craftbeer._core._di.component

import craftbeer.thoughtworks.com.craftbeer.CraftBeerApplication
import craftbeer.thoughtworks.com.craftbeer._core._di.module.AppModule
import craftbeer.thoughtworks.com.craftbeer._core._di.module.DomainApiModule
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.di.CraftBeerComponent
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.di.CraftBeerModule
import dagger.Component

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
@Component(modules = arrayOf(DomainApiModule::class,AppModule::class))
interface AppComponent {

    fun inject(context: CraftBeerApplication)

    fun plus(crafteBeerModule: CraftBeerModule): CraftBeerComponent

}