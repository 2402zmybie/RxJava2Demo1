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
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit


/**
 * 过滤操作符, 上游和下游之间
 */
class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
    }

    //all: All全部为true才是true, 只要有一个为false, 就是false
    fun all(view: View) {
        Observable.just("a","b","c")
            .all {
                //有一个不满足就是false
                it.equals("a")  //false
            }
            .subscribe(Consumer {
                Log.e("hello",it.toString())
            })
    }



    //any: 全部为false,才是false, 只要有一个为true,就是true
    fun any(view: View) {
        Observable.just("a","b","c")
            .any {
                it .equals("a")  //true
            }
            .subscribe(Consumer {
                Log.e("hello", it.toString())
            })
    }


    fun toMain4(view: View) {
        var intent = Intent(this,MainActivity4::class.java)
        startActivity(intent)
    }


}

