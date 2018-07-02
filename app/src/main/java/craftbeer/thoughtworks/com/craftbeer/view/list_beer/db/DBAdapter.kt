package craftbeer.thoughtworks.com.craftbeer.view.list_beer.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import craftbeer.thoughtworks.com.craftbeer._core.db.DBConstant
import craftbeer.thoughtworks.com.craftbeer._core.model.CraftBeer
import javax.inject.Inject

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class DBAdapter @Inject constructor(val context:Context){


    fun updateBeerList(values:List<CraftBeer>){

        val contentValues = arrayOfNulls<ContentValues>(values.size)

        var i:Int=0
        for(value in values){
            var cv:ContentValues= ContentValues();
            cv.put(DBConstant.BEER_LIST_ABV,value?.abv)
            cv.put(DBConstant.BEER_LIST_ID,value?.id)
            cv.put(DBConstant.BEER_LIST_NAME,value?.name)
            cv.put(DBConstant.BEER_LIST_STYLE,value?.style)
            cv.put(DBConstant.BEER_LIST_OUNCE,value?.ounces)
            contentValues.set(i,cv)
            i++
        }
       val count= this.context.contentResolver.bulkInsert(DBConstant.BEER_LIST_URI,contentValues);
        Log.e("Inserted",""+count)
    }


}