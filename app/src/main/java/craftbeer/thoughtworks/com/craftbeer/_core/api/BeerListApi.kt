package craftbeer.thoughtworks.com.craftbeer._core.api

import craftbeer.thoughtworks.com.craftbeer._core.model.CraftBeer
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
interface BeerListApi {
    @GET("/beercraft")
    fun getCraftedBeer():Observable<List<CraftBeer>>

}