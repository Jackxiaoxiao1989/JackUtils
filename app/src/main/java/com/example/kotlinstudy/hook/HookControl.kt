package com.example.kotlinstudy.hook

import android.R.attr.targetClass
import com.xiaoxiao.jackutils.utils.SoftUtils
import java.lang.reflect.Field
import java.lang.reflect.Proxy


object HookControl {
    var tag="HookControl"
    fun startHook(real:RealClass){
        try {
            val field =RealClass::class.java.getDeclaredField("baseMethod")
            field.isAccessible = true
            var baseMethod:BaseMethod= field[real] as BaseMethod
            var fakeMethod=FakeMethod(baseMethod)
            //val handler: BaseHook = BaseHook(baseMethod.)
            field[real] = fakeMethod
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var base1=BaseMethod()
                val handler: BaseHook = BaseHook(base1)
          val proxy= Proxy.newProxyInstance(
              MethodInterface::class.java.getClassLoader(),
              arrayOf(MethodInterface::class.java) ,
              handler
          ) as MethodInterface
        //proxy.doSomething()
        var methods=BaseMethod::class.java.getDeclaredMethods()
        for(m in methods){
            //SoftUtils.log(tag,"get $m in BaseMethod")
        }
        var fileds=BaseMethod::class.java.getDeclaredFields()
        for(f in fileds){
            SoftUtils.log(tag,"get $f in BaseMethod")
        }
        var oriMothed=BaseMethod::class.java.getDeclaredMethod("doSomething")
        methods=proxy::class.java.getDeclaredMethods()
        for(m in methods){
            SoftUtils.log(tag,"get $m in proxy")
        }
        var repMethod=proxy::class.java.getDeclaredMethod("doSomething")
        oriMothed.isAccessible=true

        //oriMothed=repMethod
        SoftUtils.log(tag,"test replace")
        base1.doSomething()
        //base1.doSomething()
        //var method=BaseMethod::class.java.getDeclaredMethod("doSomething")
        //method[]
        /*
        try {
        val field: Field = BaseMethod::class.java.getDeclaredField("INSTANCE")
        field.setAccessible(true)
        field.set(null, ReplaceMothed())
        base1.doSomething()
        } catch (e: Exception) {
            e.printStackTrace()
        }
         */

    }
}