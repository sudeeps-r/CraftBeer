package craftbeer.thoughtworks.com.craftbeer._core.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class DBHelper (var context: Context) :SQLiteOpenHelper(context,DBConstant.dbName,null,DBConstant.dbversion) {

    override fun onCreate(p0: SQLiteDatabase?) {

        p0?.execSQL(DBConstant.CREATE_TABLE_BEER_LIST)
        p0?.execSQL(DBConstant.CREATE_TABLE_BEER_CART)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0?.execSQL("DROP TABLE $DBConstant.TABLE_BEER_LIST")
        p0?.execSQL("DROP TABLE $DBConstant.TABLE_BEER_CART")
        onCreate(p0)
    }
}