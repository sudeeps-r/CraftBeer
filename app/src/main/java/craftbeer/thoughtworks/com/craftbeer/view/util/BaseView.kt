package craftbeer.thoughtworks.com.craftbeer.view.util

import io.reactivex.Scheduler

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
interface BaseView {

    fun getScheduler():Scheduler

    fun showError(message:String?)

    fun showNetworkEror()

    fun showSocketTimeoutError()
}