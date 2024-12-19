package com.example.kotlinstudy.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.kotlinstudy.R


class CustomTitleBar : RelativeLayout {
    private var mBgColor: Int = Color.BLUE
    private var mTextColor: Int = Color.WHITE
    private var mTitleText: String? = ""
    private var btn_left: ImageView? = null
    private var btn_right: ImageView? = null
    private var tvTitle: TextView? = null
    private var relativeLayout: RelativeLayout? = null

    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initTypeValue(context, attrs)
        initView(context)
    }

    fun initTypeValue(context: Context, attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CusTitleBar)
        mBgColor = a.getColor(R.styleable.CusTitleBar_bg_color, Color.YELLOW)
        mTitleText = a.getString(R.styleable.CusTitleBar_title_text)
        mTextColor = a.getColor(R.styleable.CusTitleBar_text_color, Color.RED)
        a.recycle()
    }

    fun initView(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_titlebar, this, true)
        btn_left = findViewById(R.id.btn_left)
        btn_right = findViewById(R.id.btn_right)
        tvTitle = findViewById(R.id.tv_title)
        relativeLayout = findViewById(R.id.layout_titlebar_root)
        relativeLayout!!.setBackgroundColor(mBgColor)
        tvTitle!!.setTextColor(mTextColor)
        tvTitle!!.setText(mTitleText)
    }

    fun setBackClickListener(listener: OnClickListener?) {
        btn_left!!.setOnClickListener(listener)
    }

    fun setRightClickListener(listener: OnClickListener?) {
        btn_right!!.setOnClickListener(listener)
    }

    fun setTitleText(str: String?) {
        if (!TextUtils.isEmpty(str)) {
            tvTitle!!.text = str
        }
    }
}