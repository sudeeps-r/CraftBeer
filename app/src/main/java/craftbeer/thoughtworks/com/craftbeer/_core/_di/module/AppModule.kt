package craftbeer.thoughtworks.com.craftbeer._core._di.module

import android.content.Context
import craftbeer.thoughtworks.com.craftbeer.CraftBeerApplication
import craftbeer.thoughtworks.com.craftbeer._core._di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
@ApplicationScope
@Module
class AppModule(var context: CraftBeerApplication) {

    @Provides
   fun provideContext():Context{
        return context.applicationContext;
    }
}