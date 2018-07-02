package craftbeer.thoughtworks.com.craftbeer.view.list_beer.presenter

import android.content.CursorLoader
import android.support.v4.app.LoaderManager
import craftbeer.thoughtworks.com.craftbeer.view.list_beer.view.CraftBeer
import craftbeer.thoughtworks.com.craftbeer.view.util.Presenter
import java.util.function.Predicate

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
interface CraftBeerPresenter :Presenter<CraftBeer>{

    fun fetCraftedBeer()




}