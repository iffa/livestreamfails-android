package digital.sogood.livestreamfails.mobile.ui.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.ui.base.view.FloatingTextButton
import digital.sogood.livestreamfails.mobile.ui.fail.FailFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {
    private lateinit var failFragment: FailFragment

    companion object {
        private const val FAIL_FRAGMENT_KEY = "FailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (savedInstanceState != null) {
            failFragment = supportFragmentManager.getFragment(savedInstanceState, FAIL_FRAGMENT_KEY) as FailFragment
        } else {
            failFragment = FailFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, failFragment, FAIL_FRAGMENT_KEY)
                    .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        if (outState != null) {
            supportFragmentManager.putFragment(outState, FAIL_FRAGMENT_KEY, failFragment)
        }
    }

    fun getScrollToTopButton(): FloatingTextButton? {
        return scrollToTopFab
    }
}