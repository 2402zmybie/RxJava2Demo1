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
 * 变换操作符 插在上游和下游之间
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun demo1(view: View) {
        //被观察者, 上游
        Observable.create(ObservableOnSubscribe<Int> {
            //上游发射
            it.onNext(1)
            it.onNext(2)
            it.onNext(3)
            //发射完毕
            it.onComplete()
            //订阅 (观察者(下游))
        }).subscribe(object :Observer<Int> {
            override fun onComplete() {
                Log.e("hello","完成")
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Int) {
               Log.e("hello", t.toString())
            }

            override fun onError(e: Throwable) {

            }

        })
    }

    //1 RxJava变换型操作符 ----map
    fun demo2(view: View) {
        Observable.just(1)
            .map {
                "[${it}]"
            }.subscribe {
                Log.e("hello", it)
            }
    }



    //2 RxJava变换型操作符 ----flatMap(返回一个ObservableSource,可在里面再次发射多次)
    fun demo3(view: View) {
        Observable.just(111)
            .flatMap {
                //变换操作符转化成 Observable, 再发送两次
                Observable.just("${it},flatMap变换操作符发射1","${it},flatMap变换操作符发射2")
            }.subscribe {
                //111,flatMap变换操作符发射1
                //111,flatMap变换操作符发射2
                Log.e("hello", it)
            }
    }

    //3 RxJava变换型操作符 ----concatMap(区别于flatMap,flatMap是不排序的, contactMap是排序的)
    fun demo4(view: View) {
        Observable.just(111,222,333)
            .concatMap {
                Observable.just(it).delay(5,TimeUnit.SECONDS)
            }.subscribe {
                Log.e("hello",it.toString())
            }
    }

    //4 RxJava变换型操作符 ----groupBy
    fun demo5(view: View) {
        Observable.just(1111,2222,3333,4444)
            .groupBy {
                if(it > 2000) {
                    "高端价格"
                }else {
                    "低端价格"
                }
            }.subscribe {groupObservable->
                groupObservable.subscribe {
                    Log.e("hello", "类别:" + groupObservable.key + "价格:" + it.toString())
                }
            }
    }

    //5 RxJava变换型操作符 ----buffer(很多数据不想全部一起发射出去, 分批次,先缓存到buffer)
    fun demo6(view: View) {
        Observable.create(ObservableOnSubscribe<Int> {
            for(index in 1..100) {
                it.onNext(index)
            }
            it.onComplete()
        })
            .buffer(50)
            .subscribe {
                //得到的是两个集合
                Log.e("hello",it.toString())
            }
    }



    fun btFillter(view: View) {
        val intent = Intent(this,MainActivity2::class.java)
        startActivity(intent)
    }




}

