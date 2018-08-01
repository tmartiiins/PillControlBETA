package com.marti.pillcontrol

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.layout_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

       button_login.setOnClickListener(View.OnClickListener {
           startActivity(Intent (this, ActivityLogin::class.java))

       })

        button_cadastro.setOnClickListener(View.OnClickListener {
           startActivity(Intent( this, ActivityCadastro::class.java))
       })
    }
}
