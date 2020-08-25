package com.baskoroadi.firebasestorage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var fragment:Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bottomMenu_upload -> {
                    fragment = UploadFragment()
                    openFragment(fragment)
                    true
                }
                R.id.bottomMenu_list -> {
                    fragment = ListFragment()
                    openFragment(fragment)
                    true
                }
                else -> false
            }
        }

        bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_SELECTED

        //Default Fragment
        fragment = UploadFragment()
        openFragment(fragment)
    }

    private fun openFragment(fragments: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, fragments)
        transaction.commit()
    }
}