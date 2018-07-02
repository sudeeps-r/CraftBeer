package craftbeer.thoughtworks.com.craftbeer.view.util

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import craftbeer.thoughtworks.com.craftbeer.CraftBeerApplication
import craftbeer.thoughtworks.com.craftbeer._core._di.component.AppComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
open abstract class BaseFragment : Fragment(), craftbeer.thoughtworks.com.craftbeer.view.util.View {



    abstract fun getLayoutId(): Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val lId = getLayoutId();
        if (lId > 0) {
            return inflater.inflate(lId, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun showError(message: String?) {

        Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG)
               .show()
    }

    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun showNetworkEror() {

    }

    override fun showSocketTimeoutError() {

    }

    fun appComponent():AppComponent{
        return (activity?.applicationContext as CraftBeerApplication).component

    }

}

