package com.marti.pillcontrol

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_adicionar.*

class ActivityAdicionar : AppCompatActivity() {

    var lista:ArrayList<Medicamento?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar)

        lista = ArrayList<Medicamento?>()


        if(!intent.getBooleanExtra("isSave",false)){//quando a ação é da lista

            button_salvar_medic.isEnabled = false
            button_editar_medic.isEnabled = true

            habilitarCampus(false)


            //Toast.makeText( this@ActivityAdicionar  ,"CHEIO",Toast.LENGTH_SHORT).show()
            edt_tratamento.setText(intent.getStringExtra("tratamento"))
            edt_medicamento.setText(intent.getStringExtra("medicamento"))
            edt_inicio_trat.setText(intent.getStringExtra("inicio"))
            edt_termino_trat.setText(intent.getStringExtra("termino"))
            edt_hora_dosagem.setText(intent.getStringExtra("hora"))
            edt_observacoes.setText(intent.getStringExtra("observacoes"))

        }else{

            button_salvar_medic.isEnabled = true
            button_editar_medic.isEnabled = false

            habilitarCampus(true)

        }


        var userId = intent.getStringExtra("userId")

        /**
         * Ação do Botão Salvar
         * */
        button_salvar_medic.setOnClickListener(View.OnClickListener {

            val tratamento = edt_tratamento.text.toString()
            val medicamento = edt_medicamento.text.toString()
            val inicio = edt_inicio_trat.text.toString()
            val termino = edt_termino_trat.text.toString()
            val hora = edt_hora_dosagem.text.toString()
            val observacoes = edt_observacoes.text.toString()

            val medic = Medicamento(tratamento, medicamento, inicio, termino, hora, observacoes)

            if(button_editar_medic.isEnabled){

                    val position = intent.getIntExtra("position",0)
                salvarMedicamentos(userId, medic, position.toString() )

            }
            else
                salvarMedicamentos(userId, medic)

            finish()
        })

        /**
         * Ação do Botão Editar
         * */

        button_editar_medic.setOnClickListener(View.OnClickListener {
            habilitarCampus(true)
            button_salvar_medic.isEnabled = true


        })
    }

    private fun habilitarCampus(habilite:Boolean){

        edt_tratamento.isEnabled = habilite
        edt_medicamento.isEnabled = habilite
        edt_inicio_trat.isEnabled = habilite
        edt_termino_trat.isEnabled = habilite
        edt_hora_dosagem.isEnabled = habilite
        edt_observacoes.isEnabled = habilite
    }

    private fun salvarMedicamentos(userId:String, medicamento: Medicamento, position:String = ""){


        val ref = FirebaseDatabase.getInstance().getReference()
        var query = ref.child("usuarios").child(userId).child("medicamentos")


        query.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {

               Toast.makeText(this@ActivityAdicionar,"ERROR",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot?) {

                val ref2 = FirebaseDatabase.getInstance().getReference("usuarios")

                if(position == ""){
                    // salvar novo medicamento

                    var   key = 0

                    if(p0!!.children.count() != 0) {
                        var m = p0!!.children.elementAt(p0!!.children.count()-1)
                        key = m.key.toInt() + 1
                    }

                    ref2.child(userId).child("medicamentos").child(key.toString()).setValue(medicamento)
                }
                else
                    ref2.child(userId).child("medicamentos").child(position).setValue(medicamento)

            }

        })

    }
}
