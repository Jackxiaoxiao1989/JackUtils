package com.example.kotlinstudy.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.AttributeSet
import android.view.View
import android.view.View.getDefaultSize


class CustomView : View {
    private var paint: Paint? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paint = Paint()
        paint!!.setColor(Color.BLUE)
        paint!!.setStyle(Paint.Style.FILL)
    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultSize = 200
        setMeasuredDimension(
            getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
            getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec)
        )
    }

    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制一个简单的蓝色圆形
        canvas.drawCircle(
            getWidth().toFloat() / 2,
            getHeight().toFloat() / 2,
            Math.min(getWidth().toFloat() , getHeight().toFloat() ) / 4,
            paint!!
        )

    }
}