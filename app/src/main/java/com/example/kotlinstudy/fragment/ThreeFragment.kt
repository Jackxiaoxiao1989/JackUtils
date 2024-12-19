package com.example.kotlinstudy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.KotlinUtils

class ThreeFragment : JetpackBaseFragment() {
    private var mBtnInputFragment2: Button? = null
    var mtag = "ThreeFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_three, container, false)
        mBtnInputFragment2 = view.findViewById(R.id.three_btn_cancle)
        mBtnInputFragment2?.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(requireView()).popBackStack() //这个id就是navigation里的action的id
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