package craftbeer.thoughtworks.com.craftbeer

import android.app.Application
import craftbeer.thoughtworks.com.craftbeer._core._di.component.AppComponent
import craftbeer.thoughtworks.com.craftbeer._core._di.component.DaggerAppComponent
import craftbeer.thoughtworks.com.craftbeer._core._di.module.AppModule
import craftbeer.thoughtworks.com.craftbeer._core._di.module.DomainApiModule

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class CraftBeerApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder().domainApiModule(DomainApiModule())
                .appModule(AppModule(this))
                .build()
    }
}