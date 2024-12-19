package com.example.kotlinstudy.fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.example.kotlinstudy.utils.KotlinUtils

open class JetpackBaseFragment : Fragment() {
    private val mBtnInputFragment2: Button? = null
    var listener: onAvtivityHandler? = null
    //open var tag = "JetpackBaseFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    interface onAvtivityHandler {
        fun onBackHandeler()
    }

    //当Fragment和Activity建立关联的时候调用
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as onAvtivityHandler
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
open fun setNavigateTo(veiw: View,id:Int){
    val navOption = navOptions {
        anim {
            enter = com.example.kotlinstudy.R.anim.common_slide_in_right
            exit = com.example.kotlinstudy.R.anim.common_slide_out_left
            popEnter = com.example.kotlinstudy.R.anim.common_slide_in_left
            popExit = com.example.kotlinstudy.R.anim.common_slide_out_right
        }
    }
    Navigation.findNavController(veiw)
        .navigate(id,null,navOption)
}
    open fun setNavigateTo(veiw: View,bundle:Bundle,id:Int){
        val navOption = navOptions {
            anim {
                enter = com.example.kotlinstudy.R.anim.common_slide_in_right
                exit = com.example.kotlinstudy.R.anim.common_slide_out_left
                popEnter = com.example.kotlinstudy.R.anim.common_slide_in_left
                popExit = com.example.kotlinstudy.R.anim.common_slide_out_right
            }
        }
        Navigation.findNavController(veiw)
            .navigate(id,bundle,navOption)
    }
    open fun setBackAction() {
        KotlinUtils.log("ddd", "setBackAction")
        Navigation.findNavController(requireView()).popBackStack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.home -> setBackAction()
        }
        return true
    }
}