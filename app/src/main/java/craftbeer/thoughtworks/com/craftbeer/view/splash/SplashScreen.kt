package craftbeer.thoughtworks.com.craftbeer.view.splash

import android.os.Bundle
import android.os.Handler
import craftbeer.thoughtworks.com.craftbeer.R
import craftbeer.thoughtworks.com.craftbeer.view.ViewContainer
import craftbeer.thoughtworks.com.craftbeer.view.util.BaseActivity

/**
 * Created by Sudeep SR on 01/07/18.
 * Company <Reliance Payment Solutions Ltd.>
 * Email <sudeep.sr@ril.com>
 */
class SplashScreen : BaseActivity() {

    private  var handler: Handler=Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        supportActionBar?.hide()
        handler.postDelayed(Runnable {
            startActivity(ViewContainer.newInstance(baseContext))
            finish()
        },5000) //Not handled exit and background
    }



}