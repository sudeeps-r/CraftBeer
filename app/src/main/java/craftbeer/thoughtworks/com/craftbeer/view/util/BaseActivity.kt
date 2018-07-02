package craftbeer.thoughtworks.com.craftbeer.view.util

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import craftbeer.thoughtworks.com.craftbeer.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}
