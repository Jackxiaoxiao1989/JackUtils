package com.example.kotlinstudy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.KotlinUtils

class OneFragment : JetpackBaseFragment() {
    private var mBtnInputFragment2: Button? = null
    var mtag = "OneFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        mBtnInputFragment2 = view.findViewById(R.id.one_btn_ok)
        mBtnInputFragment2?.setOnClickListener(View.OnClickListener {
            var bundle=Bundle()
            bundle.putString("data","form one")
            setNavigateTo(requireView(),bundle,R.id.action_one_to_two)
        })
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun setBackAction() {
        super.setBackAction()
        KotlinUtils.log(mtag, "setBackAction")
    }
}