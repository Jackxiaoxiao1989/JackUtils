package com.example.kotlinstudy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.kotlinstudy.R
import com.example.kotlinstudy.utils.KotlinUtils

class TwoFragment : JetpackBaseFragment() {
    private var mBtnInputFragment3: Button? = null
    private var mBtnBack: Button? = null
    var mtag = "TwoFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        var data= arguments?.getString("data")
        KotlinUtils.log(mtag,"data=$data")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_two, container, false)
        mBtnInputFragment3 = view.findViewById(R.id.two_btn_ok)
        mBtnBack = view.findViewById(R.id.two_btn_cancle)
        mBtnInputFragment3?.setOnClickListener(View.OnClickListener {
            //Navigation.findNavController(requireView()).navigate(R.id.action_two_to_three) //进入第三个碎片
            setNavigateTo(requireView(),R.id.action_two_to_three)
        })
        mBtnBack?.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(requireView()).popBackStack() //返回上一个碎片
        })
        return view
    }

    override fun setBackAction() {
        super.setBackAction()
        KotlinUtils.log(mtag, "setBackAction")
    }
}