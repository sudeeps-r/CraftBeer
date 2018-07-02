package craftbeer.thoughtworks.com.craftbeer.view.cart.view.di

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import craftbeer.thoughtworks.com.craftbeer.R
import craftbeer.thoughtworks.com.craftbeer._core.db.DBConstant
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.presenter.CraftBeerPresenter
import craftbeer.thoughtworks.com.craftbeer.view.util.BaseFragment
import javax.inject.Inject
import android.app.SearchManager
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SeekBar
import craftbeer.thoughtworks.com.craftbeer.R.id.alchPercent
import craftbeer.thoughtworks.com.craftbeer.R.id.beerList
import craftbeer.thoughtworks.com.craftbeer.view.cart.db.DBCartAdapter
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.BeerAdapter
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.CartOperation
import kotlinx.android.synthetic.main.list_container.*


/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class CartList :  BaseFragment() , LoaderManager.LoaderCallbacks<Cursor> , CartOperation {

    private lateinit var cartDBAdapter:DBCartAdapter
    override fun addItem(skuId: String) {
        cartDBAdapter.addBeer(skuId)
        showError("ItemAdded")
    }

    override fun removeItem(skuId: String) {
      var count:Int=cartDBAdapter.removeBeer(skuId)
        if(count<-1){
            showError("Not found in cart")
        }else{
            showError("Removed from cart")

        }
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        if(loader.id == LIST_ALL){
            if(data.count == 0){
           //     presenter?.fetCraftedBeer()
                showError("Your cart is empty,please start add items")
            }else{



            }
            adapter?.addCursor(data)
        }else if(loader.id == SEARCH){
            adapter?.addCursor(data)
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if(id == LIST_ALL){
            return CursorLoader(context!!,DBConstant.BEER_CART_URI,null,null,null,null)
        }else{
            var selection:String=""
            var orderBy:String="";
            var selArgs= arrayOfNulls<String>(1)
            if(args!!.containsKey(SEARCH_KEY)){
                selection="${DBConstant.BEER_LIST_NAME } LIKE ? COLLATE NOCASE"
                selArgs= arrayOf(" '%${args.get(SEARCH_KEY)}%'")
            }else{
                selection="${DBConstant.BEER_LIST_ABV } <= ? "
                selArgs= arrayOf("${args.get(FILTER_VALUE)}")
                orderBy=DBConstant.BEER_LIST_ABV +" ASC "
            }
            return CursorLoader(context!!,DBConstant.BEER_LIST_URI,null,selection,selArgs,orderBy)
        }
    }



    override fun onLoaderReset(loader: Loader<Cursor>) {
     /*    adapter?.addCursor(null)*/
    }


    companion object {
        fun newInstance():CartList{
            return CartList()
        }
    }

    private var adapter: BeerAdapter = BeerAdapter(this@CartList)

    private  val SEARCH:Int=100
    private  val FILTER:Int=101
    private  val LIST_ALL=102;

    private  val SEARCH_KEY:String= "search_text"
    private  val FILTER_VALUE:String="filter_value"

    @Inject lateinit var presenter: CraftBeerPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)

    }
    override fun getLayoutId(): Int {
        return R.layout.list_container
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartDBAdapter=DBCartAdapter(activity!!.applicationContext)
        beerList.adapter=adapter
        loaderManager.restartLoader(LIST_ALL,null,this@CartList)
        waveLoadingView.visibility=View.GONE


    }

    fun handleSearch(menu:Menu){
        val manager =activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val search = menu.findItem(R.id.m_search).actionView as SearchView

        search.setSearchableInfo(manager.getSearchableInfo(activity?.getComponentName()))

        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
             return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                 searchName(p0!!)
                return true
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.m_filter-> toggleFilter()
        }
        return super.onOptionsItemSelected(item)
    }
    fun toggleFilter(){

    }
    fun handleFilter(){

    }
    fun searchName(name:String){
        if(TextUtils.isEmpty(name)){
            this.loaderManager.restartLoader(LIST_ALL,null,this@CartList)
            return
        }
        var bundle :Bundle= Bundle()
        bundle.putString(SEARCH_KEY,name)
        this.loaderManager.restartLoader(SEARCH,bundle,this@CartList)
    }

    fun filterAlcohol(percent:Double){
        Log.e("Alch",">>"+percent)
        var bundle :Bundle= Bundle()
        bundle.putDouble(FILTER_VALUE,percent)
        this.loaderManager.restartLoader(SEARCH,bundle,this@CartList)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}