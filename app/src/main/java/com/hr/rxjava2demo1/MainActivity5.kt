package com.hr.rxjava2demo1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main5.*
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * 过滤操作符, 上游和下游之间
 */
class MainActivity5 : AppCompatActivity() {

    var womenPicUrl = "https://cdn.pixabay.com/photo/2016/06/11/12/13/pink-hair-1450045__340.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
    }


    fun demo1(view: View) {
        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("ceshi")
            it.onComplete()
            Log.e("hello","上游线程" + Thread.currentThread().name)
        })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("hello",it)
                Log.e("hello","下游线程" + Thread.currentThread().name)
            }
    }

    fun showPicture(view: View) {
        Observable.just(womenPicUrl)
            .map {
                //将发射的url 转换为BitMap发送给下游
                var url = URL(it)
                var connection = url.openConnection()
                var httpURLConnection = connection as HttpURLConnection
                var responseCode = httpURLConnection.responseCode
                if(HttpURLConnection.HTTP_OK == responseCode) {
//                    var bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)
//                    bitmap

                    var inputStream = httpURLConnection.getInputStream()
                    if(inputStream != null) {
                        var data = readStream(inputStream)
                        if(data != null) {
                            var bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                            bitmap

                        }else {

                        }
                    }

                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {object :Observer<Bitmap>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Bitmap) {
                    iv.setImageBitmap(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("hellow", e.toString())
                }

            }
            }
    }


    /*
	 * 得到图片字节流 数组大小
	 * */
    @Throws(Exception::class)
    fun readStream(inStream: InputStream): ByteArray? {
        val outStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = 0
        while (inStream.read(buffer).also({ len = it }) != -1) {
            outStream.write(buffer, 0, len)
        }
        outStream.close()
        inStream.close()
        return outStream.toByteArray()
    }





}

