package com.hr.rxjava2demo1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit


/**
 * 过滤操作符, 上游和下游之间
 */
class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }


    fun filter(view: View) {
        Observable.just("三鹿","合生元","天鹤")
            .filter {
                if(it.equals("三鹿")) false else true
            }
            .subscribe {
                Log.e("hello", it)
            }
    }

    //1 过滤操作符take, 只有在定时器运行基础上,加入take过滤操作符, 才能有take过滤操作符的价值
    fun take(view: View) {
        Observable.interval(1,TimeUnit.SECONDS)
                //定时器执行8次 就停止
            .take(8)
            .subscribe {
                Log.e("hello", it.toString())
            }
    }

    //过滤操作符, 过滤掉重复的发射
    fun disctink(view: View) {
        Observable.just(1,2,1,4,5)
            .distinct()
            .subscribe {
                Log.e("hello",it.toString())  //1 2 4 5
            }

    }

    //过滤操作符, 指定输出特定下标的数据
    fun elementAt(view:View) {
        Observable.just(1,2,3,5)
            .elementAt(2)
            .subscribe {
                Log.e("hello",it.toString())  //1 2 4 5
            }
    }


    fun toMain3(view: View) {
        var intent = Intent(this,MainActivity3::class.java)
        startActivity(intent)
    }



}

