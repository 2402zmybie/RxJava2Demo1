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
class MainActivity4 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
    }


    fun startWith(view: View) {
        Observable.create(ObservableOnSubscribe<Int> {
            it.onNext(1)
            it.onNext(2)
            it.onComplete()
        }).startWith(Observable.create(ObservableOnSubscribe {
            it.onNext(100)
            it.onNext(200)
            it.onComplete()
        })).subscribe {
            Log.e("hello", it.toString())
        }
    }


    fun concatWith(view: View) {
        Observable
            .just(1,2)
            .concatWith(Observable.just(100,200))
            .subscribe {
                Log.e("hello",it.toString())
            }
    }


    fun toMain5(view: View) {
        var intent = Intent(this,MainActivity5::class.java)
        startActivity(intent)
    }





}

