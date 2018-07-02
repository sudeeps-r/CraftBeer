package craftbeer.thoughtworks.com.craftbeer.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import craftbeer.thoughtworks.com.craftbeer.R
import craftbeer.thoughtworks.com.craftbeer.view.cart.view.di.CartList
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.ListBeer
import craftbeer.thoughtworks.com.craftbeer.view.util.BaseActivity
import craftbeer.thoughtworks.com.craftbeer.view.util.inTransaction
import java.util.*

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class ViewContainer:BaseActivity(),CraftBeerOperation {
    override fun navigateCart() {
        supportFragmentManager.inTransaction { replace(R.id.view_container,CartList.newInstance()) }
    }

    companion object {
        fun newInstance(context: Context):Intent{
            val intent:Intent=Intent(context,ViewContainer::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportFragmentManager.inTransaction { replace(R.id.view_container,ListBeer.newInstance()) }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount==1){
            this.finish()
        }else{
            super.onBackPressed()
        }

    }
}