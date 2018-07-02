package craftbeer.thoughtworks.com.craftbeer._core.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class DBProvider : ContentProvider() {

    private val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private lateinit var dbHelper: DBHelper
    private lateinit var readbleSqliteDb: SQLiteDatabase
    private lateinit var writeableSqliteDb: SQLiteDatabase

    init {
        sURIMatcher.addURI(DBConstant.dbprovider,DBConstant.TABLE_BEER_LIST,DBConstant.beer_code)
        sURIMatcher.addURI(DBConstant.dbprovider,DBConstant.TABLE_BEER_CART,DBConstant.beer_cart_code)
    }
    override fun insert(uri: Uri, p1: ContentValues?): Uri {

        val table = getTableName(uri)

        val value = writeableSqliteDb.insert(table, null, p1)
        if (value > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return Uri.withAppendedPath(uri, value.toString())
    }

    override fun query(uri: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor {
        val table = getTableName(uri)
        val db = readbleSqliteDb
        val cursor = db.query(table, p1, p2, p3, null, null, p4)
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context)
        readbleSqliteDb = dbHelper.readableDatabase
        writeableSqliteDb = dbHelper.writableDatabase
        return true
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        val table = getTableName(p0)

        val update = writeableSqliteDb.update(table, p1, p2, p3)
        if (update > 0) {
            context!!.contentResolver.notifyChange(p0, null)
        }
        return update
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<out String>?): Int {
        val table = getTableName(uri)
        val db = writeableSqliteDb
        val delete = db.delete(table, p1, p2)
        if (delete > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return delete
    }

    override fun getType(p0: Uri?): String {
       return "";
    }

    override fun bulkInsert(uri: Uri, values: Array<out ContentValues>): Int {
        var numInserted = 0
        val table = getTableName(uri)
        val sqlDB = writeableSqliteDb
        sqlDB.beginTransaction()
        try {
            for (cv in values) {
                val newID = sqlDB.insertOrThrow(table, null, cv)
                if (newID <= 0) {
                    throw SQLException("Failed to insert row into $uri")
                }
            }
            sqlDB.setTransactionSuccessful()
            numInserted = values.size


        } finally {
            sqlDB.endTransaction()
            if (numInserted > 0) {
                context!!.contentResolver.notifyChange(uri, null)

            }
        }

        return numInserted
    }

    fun getTableName(uri:Uri):String{
        var tableName:String=""
        when (sURIMatcher.match(uri)){
            DBConstant.beer_code->tableName=DBConstant.TABLE_BEER_LIST
            DBConstant.beer_cart_code->tableName=DBConstant.TABLE_BEER_CART
        }
        return tableName
    }

}