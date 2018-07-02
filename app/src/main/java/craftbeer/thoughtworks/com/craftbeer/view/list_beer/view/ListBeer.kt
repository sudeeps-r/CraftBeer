package craftbeer.thoughtworks.com.craftbeer.view.list_beer.view

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
import craftbeer.thoughtworks.com.craftbeer.R.id.c_al_container
import craftbeer.thoughtworks.com.craftbeer.view.CraftBeerOperation
import craftbeer.thoughtworks.com.craftbeer.view.cart.db.DBCartAdapter
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.di.CraftBeerModule
import kotlinx.android.synthetic.main.list_container.*


/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class ListBeer :CraftBeer , BaseFragment() , LoaderManager.LoaderCallbacks<Cursor> ,CartOperation{

    private lateinit var cartDBAdapter:DBCartAdapter
    private lateinit var craftBeerOperation: CraftBeerOperation
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.craftBeerOperation = context as CraftBeerOperation
    }

    override fun showLoader() {
        waveLoadingView.visibility=View.VISIBLE
    }

    override fun hideLoader() {
        waveLoadingView.visibility=View.GONE
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        if(loader.id == LIST_ALL){
            if(data.count == 0){
                presenter?.fetCraftedBeer()
            }else{
                adapter?.addCursor(data)
                hideLoader()

            }
        }else if(loader.id == SEARCH){
            adapter?.addCursor(data)
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        if(id == LIST_ALL){
            return CursorLoader(context!!,DBConstant.BEER_LIST_URI,null,null,null,null)
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
        fun newInstance():ListBeer{
            return ListBeer()
        }
    }

    private var adapter:BeerAdapter= BeerAdapter(this@ListBeer)

    private  val SEARCH:Int=100
    private  val FILTER:Int=101
    private  val LIST_ALL=102;

    private  val SEARCH_KEY:String= "search_text"
    private  val FILTER_VALUE:String="filter_value"

    @Inject lateinit var presenter: CraftBeerPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        appComponent().plus(CraftBeerModule()).inject(this) //Release obj
    }
    override fun getLayoutId(): Int {
        return R.layout.list_container
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.list_menu,menu)
        handleSearch(menu!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        cartDBAdapter=DBCartAdapter(activity!!.applicationContext)
        beerList.adapter=adapter
        loaderManager.restartLoader(LIST_ALL,null,this@ListBeer)
        handleFilter()

    }

    fun handleSearch(menu:Menu){
        val manager =activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val search = menu.findItem(R.id.m_search).actionView as SearchView

        search.setSearchableInfo(manager.getSearchableInfo(activity?.getComponentName()))
        search.setQueryHint("Search your beer");
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
            R.id.m_cart->this.craftBeerOperation.navigateCart()
        }
        return super.onOptionsItemSelected(item)
    }
    fun toggleFilter(){
       if(c_al_container.visibility == View.VISIBLE){
           c_al_container.visibility=View.GONE
       }else{
           c_al_container.visibility=View.VISIBLE
       }
    }
    fun handleFilter(){
        alchPercent.keyProgressIncrement=10
        alchPercent.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    tvAlcoholPercent.setText("Alcohol percentage "+p1)

                filterAlcohol((p1.toDouble()/100).toDouble())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }
    fun searchName(name:String){
        if(TextUtils.isEmpty(name)){
            this.loaderManager.restartLoader(LIST_ALL,null,this@ListBeer)
            return
        }
        var bundle :Bundle= Bundle()
        bundle.putString(SEARCH_KEY,name)
        this.loaderManager.restartLoader(SEARCH,bundle,this@ListBeer)
    }

    fun filterAlcohol(percent:Double){
        Log.e("Alch",">>"+percent)
        var bundle :Bundle= Bundle()
        bundle.putDouble(FILTER_VALUE,percent)
        this.loaderManager.restartLoader(SEARCH,bundle,this@ListBeer)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}