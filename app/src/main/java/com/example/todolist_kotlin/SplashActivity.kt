package com.example.todolist_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /**
         * delay handler
         */

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            /**
             * 원래 비동기 네트워크 작업 등을 수행
             */
            val intent = Intent(this,ListMainActivity::class.java)
            startActivity(intent)
            finish();
            //1.5초 뒤에 진행
        },1500)
    }
}