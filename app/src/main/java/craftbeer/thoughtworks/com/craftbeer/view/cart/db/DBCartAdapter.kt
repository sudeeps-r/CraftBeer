package craftbeer.thoughtworks.com.craftbeer.view.cart.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import craftbeer.thoughtworks.com.craftbeer._core.db.DBConstant
import javax.inject.Inject

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class DBCartAdapter @Inject constructor(var context:Context) {

    fun addBeer(skuId:String):Int{

        var count = getCount(skuId)
        var cursor:Cursor=getData(skuId)
        if(cursor.moveToFirst()){
            var cv:ContentValues= ContentValues()
            count=count+1
            cv.put(DBConstant.BEER_LIST_ABV,getText(cursor,DBConstant.BEER_LIST_ABV))
            cv.put(DBConstant.BEER_LIST_STYLE,getText(cursor,DBConstant.BEER_LIST_STYLE))
            cv.put(DBConstant.BEER_LIST_NAME,getText(cursor,DBConstant.BEER_LIST_NAME))
            cv.put(DBConstant.BEER_LIST_ID,getText(cursor,DBConstant.BEER_LIST_ID))
            cv.put(DBConstant.BEER_CART_COUNT,count)
            if(count <=1){
                this.context.contentResolver.insert(DBConstant.BEER_CART_URI,cv)
            }else{
                this.context.contentResolver.update(DBConstant.BEER_CART_URI,cv,DBConstant.BEER_LIST_ID+" = ?", arrayOf(skuId))
            }
        }
        return count++
    }

    fun removeBeer(skuId: String):Int{
        var count=getCount(skuId)
        if(count<=0){
            return -2
        }
        count=count-1
        var cv:ContentValues= ContentValues()
        cv.put(DBConstant.BEER_CART_COUNT,count)
        if(count==0){
            this.context.contentResolver.delete(DBConstant.BEER_CART_URI,DBConstant.BEER_LIST_ID+" = ?", arrayOf(skuId))
            return count;
        }
        this.context.contentResolver.update(DBConstant.BEER_CART_URI,cv,DBConstant.BEER_LIST_ID+" = ?", arrayOf(skuId))
        return count
    }

    fun getCount(skuId: String):Int{


       var cursor:Cursor= this.context.contentResolver.query(DBConstant.BEER_CART_URI,null,DBConstant.BEER_LIST_ID+" = ?", arrayOf(skuId),null)
        cursor.moveToFirst()
       return cursor.count

    }

    fun getData(skuId: String):Cursor{


        return this.context.contentResolver.query(DBConstant.BEER_LIST_URI,null,DBConstant.BEER_LIST_ID+" = ?", arrayOf(skuId),null)


    }
    fun getText(cursor:Cursor,colName:String):String{
        return cursor.getString(cursor.getColumnIndex(colName))
    }
}