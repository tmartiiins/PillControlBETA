package com.marti.pillcontrol

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_cadastro.*


class ActivityCadastro : AppCompatActivity() {

    private val diagnostico_list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_cadastro)

        button_salvar.setOnClickListener(View.OnClickListener {

            var vazio = false

            val nome = edt_nome.text.toString()
            val data = edt_nascimeno.text.toString()
            val sexo = if (rbtn_F.isChecked) "F" else "M"
            val email = edt_email.text.toString()
            val login = edt_login_cadastro.text.toString()
            val senha = edt_senha_cadastro.text.toString()
            val confsenha = edt_senha_confirma.text.toString()


            if (nome == "") {
                edt_nome.hint = "Campo Obrigatório"
                edt_nome.setHintTextColor(Color.RED)
                vazio = true
            } else
                vazio = false

            if (data == "") {
                edt_nascimeno.hint = "Campo Obrigatório"
                edt_nascimeno.setHintTextColor(Color.RED)
                vazio = true
            } else
                vazio = false

            if (email == "") {
                edt_email.hint = "Campo Obrigatório"
                edt_email.setHintTextColor(Color.RED)
                vazio = true
            } else
                vazio = false

            if (login == "") {
                edt_login_cadastro.hint = "Campo Obrigatório"
                edt_login_cadastro.setHintTextColor(Color.RED)
                vazio = true
            } else
                vazio = false

            if (senha == "") {
                edt_senha_cadastro.hint = "Campo Obrigatório"
                edt_senha_cadastro.setHintTextColor(Color.RED)
                vazio = true
            } else
                vazio = false

            if (edt_senha_confirma.text.toString() == "") {
                edt_senha_confirma.hint = "Campo Obrigatório"
                edt_senha_confirma.setHintTextColor(Color.RED)
                vazio = true
            } else
                vazio = false

            if (checkbox_diabetes.isChecked)
                diagnostico_list.add(getString(R.string.check_d1))
            //Toast.makeText(this,getString(R.string.check_d1),Toast.LENGTH_SHORT).show()

            if (checkbox_alzheimer.isChecked)
                diagnostico_list.add(getString(R.string.check_d3))

            if (checkbox_asma.isChecked)
                diagnostico_list.add(getString(R.string.check_d7))

            if (checkbox_depressao.isChecked)
                diagnostico_list.add(getString(R.string.check_d4))

            if (checkbox_gastrite.isChecked)
                diagnostico_list.add(getString(R.string.check_d8))

            if (checkbox_hipertensao.isChecked)
                diagnostico_list.add(getString(R.string.check_d5))

            if (checkbox_osteoporose.isChecked)
                diagnostico_list.add(getString(R.string.check_d2))

            if (checkbox_parkinson.isChecked)
                diagnostico_list.add(getString(R.string.check_d6))

            if (vazio) {
            } else if (!edt_senha_cadastro.text.toString().equals(edt_senha_confirma.text.toString()))

                Toast.makeText(applicationContext, "Senha não confere!", Toast.LENGTH_SHORT).show()
            else {
                val user = Usuario(login, senha, nome, data, sexo, email)

                salvar(user)

                var alertDialog = AlertDialog.Builder(this)

                alertDialog.setTitle("Aviso!") // O Titulo da notificação
                alertDialog.setMessage("Cadastro realizado com sucesso!") // a mensagem ou alerta

                alertDialog.setPositiveButton("Ok", { _, _ ->

                    val intent = Intent(this@ActivityCadastro, ActivityControle::class.java)

                    intent.putExtra("login", user!!.login)
                    intent.putExtra("senha", user!!.senha)
                    intent.putExtra("nome", user!!.nome)
                    intent.putExtra("id", user!!.id)
                    intent.putExtra("data", user!!.data)
                    intent.putExtra("sexo", user!!.sexo)
                    intent.putExtra("email", user!!.email)
                    intent.putExtra("diagnostico", user!!.diagnostico)

                    startActivity(intent)

                    finish()


                })

                alertDialog.show()
            }
        })
    }

    fun salvar(user: Usuario) {

        val ref = FirebaseDatabase.getInstance().getReference("usuarios")
        user.diagnostico = diagnostico_list

        if(user.id != null){
            ref.child(user.id).setValue(user)
        }
        else{
            user.id = ref.push().key
            ref.child(user.id).setValue(user).addOnCompleteListener {}
        }
    }
}