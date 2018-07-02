package craftbeer.thoughtworks.com.craftbeer._core._di.module

import craftbeer.thoughtworks.com.craftbeer._core._di._AppConstant
import craftbeer.thoughtworks.com.craftbeer._core._di.scope.ApplicationScope
import craftbeer.thoughtworks.com.craftbeer._core.api.BeerListApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */

@ApplicationScope
@Module(includes = arrayOf(NetModule::class))
class DomainApiModule {


     @Provides
     fun provideCraftedBeer( @Named(_AppConstant.BASIC_HTTP)  retrofit: Retrofit ):BeerListApi{
        return retrofit.create(BeerListApi::class.java)
    }


}