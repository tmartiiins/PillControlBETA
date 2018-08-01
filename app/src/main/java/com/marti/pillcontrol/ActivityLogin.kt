package com.marti.pillcontrol

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.layout_login.*
import java.util.*
import kotlin.collections.HashMap

class ActivityLogin : AppCompatActivity() {

    private val firebasedatabase:FirebaseDatabase  = FirebaseDatabase.getInstance()
    private val databaseReference:DatabaseReference = firebasedatabase.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)


        button_entrar.setOnClickListener(View.OnClickListener {

            pegarlogin(edt_login.text.toString(), edt_senha_login.text.toString())
        })

    }

    private fun pegarlogin(login:String, senha:String){

        val query = databaseReference.child("usuarios").orderByChild("login").equalTo(login)

        query.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(p0: DataSnapshot?) {

                for(s in p0!!.children) {

                    val usuario = s.getValue(Usuario::class.java)
                    var senhaUser = usuario!!.senha
                    var loginUser = usuario!!.login

                    if(senha != senhaUser)
                           Toast.makeText( this@ActivityLogin  ,"Login ou senha incorretos!",Toast.LENGTH_SHORT).show()

                    else{

                        val intent = Intent(this@ActivityLogin,ActivityControle::class.java)

                        intent.putExtra("login",usuario!!.login)
                        intent.putExtra("senha",usuario!!.senha)
                        intent.putExtra("nome",usuario!!.nome)
                        intent.putExtra("id",usuario!!.id)
                        intent.putExtra("data",usuario!!.data)
                        intent.putExtra("sexo",usuario!!.sexo)
                        intent.putExtra("email",usuario!!.email)
                        intent.putExtra("diagnostico",usuario!!.diagnostico)

                        startActivity(intent)
                    }

             }
            }

            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        })
    }


}
