package craftbeer.thoughtworks.com.craftbeer.view.util

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
interface Presenter<V : View> {

    fun attachView(view: V)

    fun detachView()
}