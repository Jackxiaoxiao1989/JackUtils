package com.example.kotlinstudy.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlinstudy.R
import com.example.kotlinstudy.databinding.ActivityCustmerNavigationBinding
import com.example.kotlinstudy.utils.KotlinUtils

class CustomerNavigationActivity : RootActivity() {
    var tag = "CustomerNavigationActivity"
    var navHostFragment: NavHostFragment? = null
    var navController: NavController? = null
    private val binding: ActivityCustmerNavigationBinding? = null
    var mfrag: Fragment? = null
    var curid = 0
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding= DataBindingUtil.setContentView(this,R.layout.activity_custmer_navigation);
        setContentView(R.layout.activity_custmer_navigation)
        navHostFragment = getSupportFragmentManager().findFragmentById(R.id.demo_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController
        navController!!.addOnDestinationChangedListener { controller, destination, arguments ->
            val curid = destination.id
            KotlinUtils.log(tag, "onDestinationChanged,curid=$curid")
            //val curCount = navHostFragment!!.requireFragmentManager().backStackEntryCount
            val curCount = navHostFragment!!.fragmentManager?.backStackEntryCount
            KotlinUtils.log(tag, "onDestinationChanged,curCount=$curCount")

            //destination.
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.home -> {}
        }
        return true
    }
}