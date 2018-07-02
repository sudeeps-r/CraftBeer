package craftbeer.thoughtworks.com.craftbeer._core.db

import android.net.Uri
import craftbeer.thoughtworks.com.craftbeer.BuildConfig

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class DBConstant {
    companion object {
        const val dbversion: Int = 1
        const val dbName: String = "craftedBeer"
        const val dbprovider: String = BuildConfig.APPLICATION_ID + ".dbprovider"
        const val base_uri = "content://$dbprovider/"


        //Beer listing
        const val beer_code:Int=100
        const val TABLE_BEER_LIST:String="ListBeer"
        const val BEER_LIST_ID="_id"
        const val BEER_LIST_NAME="name"
        const val BEER_LIST_STYLE="style"
        const val BEER_LIST_OUNCE="ounce"
        const val BEER_LIST_ABV="abv"
        const val CREATE_TABLE_BEER_LIST:String = "CREATE TABLE $TABLE_BEER_LIST ( $BEER_LIST_ID INTEGER PRIMARY KEY , $BEER_LIST_NAME VARCHAR , $BEER_LIST_STYLE VARCHAR , " +
                "$BEER_LIST_OUNCE REAL , $BEER_LIST_ABV REAL)"
         val BEER_LIST_URI:Uri= Uri.parse(base_uri+ TABLE_BEER_LIST);

        const val beer_cart_code:Int=101
        const val TABLE_BEER_CART:String="cartBeer"
        const val BEER_CART_COUNT="item_count"
        const val CREATE_TABLE_BEER_CART:String = "CREATE TABLE $TABLE_BEER_CART ( $BEER_LIST_ID INTEGER PRIMARY KEY , $BEER_LIST_NAME VARCHAR , $BEER_LIST_STYLE VARCHAR , " +
                "$BEER_LIST_OUNCE REAL , $BEER_LIST_ABV REAL , $BEER_CART_COUNT REAL )"
        val BEER_CART_URI:Uri= Uri.parse(base_uri+ TABLE_BEER_CART);

    }
}