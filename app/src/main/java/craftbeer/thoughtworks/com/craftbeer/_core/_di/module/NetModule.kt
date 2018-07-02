package craftbeer.thoughtworks.com.craftbeer._core._di.module

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import craftbeer.thoughtworks.com.craftbeer.BuildConfig
import craftbeer.thoughtworks.com.craftbeer._core._di._AppConstant
import craftbeer.thoughtworks.com.craftbeer._core._di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
@ApplicationScope
@Module
class NetModule{

    @Provides
    @Named(_AppConstant.BASIC_HTTP)
    fun provideRetrofit() : Retrofit{
       return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}