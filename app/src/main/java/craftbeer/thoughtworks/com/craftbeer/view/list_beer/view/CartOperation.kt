package craftbeer.thoughtworks.com.craftbeer.view.list_beer.view

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
interface CartOperation {

    fun addItem(skuId:String)

    fun removeItem(skuId: String)
}