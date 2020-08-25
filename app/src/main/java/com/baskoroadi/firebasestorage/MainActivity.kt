package com.baskoroadi.firebasestorage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fragment:Fragment
    private var posFragment:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottomMenu_upload -> {
                    fragment = UploadFragment()
                    openFragment(fragment)
                    posFragment = R.id.bottomMenu_upload
                    true
                }
                R.id.bottomMenu_list -> {
                    fragment = ListFragment()
                    openFragment(fragment)
                    posFragment = R.id.bottomMenu_list
                    true
                }
                else -> false
            }
        }

        bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED

        if (savedInstanceState == null){
            //Default Fragment
            fragment = UploadFragment()
            openFragment(fragment)
        }
        else{
            checkSavedInstanceState(savedInstanceState)
        }
    }

    private fun checkSavedInstanceState(theBundle:Bundle){
        when(theBundle.getInt("posFragment")) {
            R.id.bottomMenu_upload -> {
                fragment = UploadFragment()
                openFragment(fragment)
                posFragment = R.id.bottomMenu_upload
            }
            R.id.bottomMenu_list -> {
                fragment = ListFragment()
                openFragment(fragment)
                posFragment = R.id.bottomMenu_list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("posFragment",posFragment)

        super.onSaveInstanceState(outState)
    }

    private fun openFragment(fragments: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, fragments)
        transaction.commit()
    }
}