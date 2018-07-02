package craftbeer.thoughtworks.com.craftbeer.view.list_beer.view

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import craftbeer.thoughtworks.com.craftbeer.R
import craftbeer.thoughtworks.com.craftbeer._core.db.DBConstant
import kotlinx.android.synthetic.main.beer_item.view.*

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class BeerAdapter(var cartOperation:CartOperation) : RecyclerView.Adapter<BeerAdapter.BeerViewHolder>(),View.OnClickListener {



    override fun onClick(p0: View?) {
        var id=p0?.id
        var sku=p0!!.tag.toString()
        if(id == R.id.btn_add_to_cart){
            cartOperation.addItem(sku)
        }else{
            cartOperation.removeItem(sku)
        }
    }

    private lateinit var cursor: Cursor
    private var size: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {

        return BeerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.beer_item, parent, false))
    }

    override fun getItemCount(): Int {

        return this.size
    }

    fun addCursor(cursor: Cursor) {

        cursor.let {
            cursor.moveToFirst()
            this.cursor = cursor;
            size = this.cursor.count
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {

        if(this.cursor.moveToPosition(position)){
            holder.name.setText(getText(cursor,DBConstant.BEER_LIST_NAME))
            holder.styl.setText(getText(cursor,DBConstant.BEER_LIST_STYLE))
            holder.alc.setText("Alcohol content ${getText(cursor,DBConstant.BEER_LIST_ABV)}")
            var id=getLong(cursor,DBConstant.BEER_LIST_ID)
            Log.e("id","id"+id)
            holder.addItem.tag=id
            holder.remItem.tag=id
            holder.addItem.setOnClickListener(this@BeerAdapter)
            holder.remItem.setOnClickListener(this@BeerAdapter)
        }
    }


    class BeerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.tv_title
        val styl = view.tv_style
        val alc = view.tv_alcohol_content
        val addItem= view.btn_add_to_cart
        val remItem= view.btn_remove_from_cart

    }

    fun getText(cursor:Cursor,colName:String):String{
        return cursor.getString(cursor.getColumnIndex(colName))
    }

    fun getLong(cursor:Cursor,colName:String):Long{
        return cursor.getLong(cursor.getColumnIndex(colName))
    }


}