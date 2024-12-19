package com.example.kotlinstudy.info

import android.util.Log

// 类关键词：类使用关键字 class 声明，后面紧跟类名;
// 构造方法：带构造方法的类 (constructor关键词 可省略)；
open class Person constructor (name: String){
    var tag : String="Person"
    // 类属性：非空属性必须在定义的时候初始化；
    // 类属性：Kotlin提供了 后端变量field 机制，但 field关键词 只能应用于属性的访问器；
    var mName: String = "xia"
        get() {
            Log.d(tag,"mName get");
            return field.toUpperCase()   // 将变量赋值后转换为大写
        }

        set(value) {
            Log.d(tag,"mName set");
            field=value
        }

    var mAge: Int = 16
        get() {
            Log.d(tag,"mAge get");
            return field   // 将变量赋值后转换为大写
        }
        set(value) {
            Log.d(tag,"mAge set");
            if (value < 20) {       // 如果传入的值小于 20 返回该值
                field = value
            } else {
                field = 18         // 否则返回18
            }
        }

    // 构造方法：主构造器中不能包含任何代码，初始化代码可放在 init 代码段中；
    init {
        this.mName = name
        Log.d(tag,"init mName="+this.mName);
        //println("init name $mName")
        //println("init age $mAge")
    }
    // 次构造方法：如果类有主构造方法，每个次构造方法都要 直接或间接通过另一个次构造方法 代理主构造方法；
    // 次构造方法：在同一个类中代理另一个构造方法使用 this 关键字；
    constructor (name: String, age:Int) : this(name) {
        // 次构造方法...
        this.mName = name
        this.mAge = age
        Log.d(tag,"constructor mName="+this.mName);
        Log.d(tag,"constructor mAge="+this.mAge);
        //println("constructor init name $mName")
        //println("constructor init age $mAge")
    }

    open fun study(){

    }
}

class Student1:Person{
    constructor (name: String, age:Int):super(name, age){

    }

    override fun study() {
        super.study()
    }
}

interface AInterface{
    var gege:String

    fun changeFun1()

    fun changeFun2(){
        Log.d("AInterface","changeFun2")
    }
}

class Student2:AInterface{
    override var gege: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun changeFun1() {
        TODO("Not yet implemented")
    }

}

class Student3{
    lateinit var minter:AInterface

    fun setInterface(interobj:AInterface) {
        this.minter=interobj;
    }
}


