package com.example.kotlinstudy.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


@SuppressLint("AppCompatCustomView")
class SyncImageView: ImageView {
    var tag="SyncImageView"
    var url:String?=null
    var deId:Int=0
    var context:Activity?=null
    constructor(context: Context?):super(context) {
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?):super(context,attrs) {
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr)  {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):super(context,attrs,defStyleAttr,defStyleRes) {
        initView()
    }

    fun initView(){

    }

    fun setSyncView(context:Activity,url:String?,def:Int){
        this.deId=def
        this.url=url
        this.context=context
        if(url==null) {
            Glide.with(this.context).load(def).crossFade().override(200, 200).into(this)
        }else {
            Glide.with(this.context).load(File(url)).crossFade().override(200, 200).into(this)
        }
    }
/*
        Glide.with(this).load(R.mipmap.ic_launcher)
                //模糊
                .bitmapTransform(new BlurTransformation(this))
                //圆角
                .bitmapTransform(new RoundedCornersTransformation(this, 24, 0, RoundedCornersTransformation.CornerType.ALL))
                //遮盖
                .bitmapTransform(new MaskTransformation(this, R.mipmap.ic_launcher))
                //灰度
                .bitmapTransform(new GrayscaleTransformation(this))
                //圆形
                .bitmapTransform(new CropCircleTransformation(this))
                .into(binding.image2);

*/
    /*Glide.with(this).load(file).asBitmap().override(300,300).into(new SimpleTarget<Bitmap>(){
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
            binding.image2.setImageBitmap(bitmap);
        }
    });*/
}