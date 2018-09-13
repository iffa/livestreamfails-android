package digital.sogood.livestreamfails.mobile.ui.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.ui.fail.FailFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FailFragment())
                    .commit()
        }
    }
}
