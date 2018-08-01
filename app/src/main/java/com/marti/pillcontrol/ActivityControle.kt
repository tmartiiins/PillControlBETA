package com.marti.pillcontrol

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.layout_controle.*
import kotlinx.android.synthetic.main.layout_main.*
import java.util.*




class ActivityControle : AppCompatActivity() {

    private var medicamentos:ArrayList<Medicamento> = ArrayList<Medicamento>()
    private var litsAdapter: MedicamentosAdapter? =  null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.layout_controle)



        var nome = intent.getStringExtra("nome")
        var data = intent.getStringExtra("data")
        var userId = intent.getStringExtra("id")
        var diagnostico = intent.getStringArrayListExtra("diagnostico")


        var stringDiag = ""

        for(s in diagnostico)
            stringDiag += s + "-"

        tv_nome_controle.text = nome
        tv_data_controle.text = data
        tv_diagnostico_controle.text = stringDiag

        button_adicionar.setOnClickListener(View.OnClickListener {

          var intent =   Intent(this, ActivityAdicionar::class.java)

            intent.putExtra("userId",userId)
            intent.putExtra("isSave",true)

            startActivity(intent)
        })
        /**
         * Evento de click em um elemento da lista
         * */
        lv_medicamentos.setOnItemClickListener { parent, view, position, id ->


            var intent =   Intent(this, ActivityAdicionar::class.java)

            intent.putExtra("userId",userId)
            intent.putExtra("tratamento", medicamentos[position].tratamento)
            intent.putExtra("medicamento", medicamentos[position].medicamento)
            intent.putExtra("inicio", medicamentos[position].inicio)
            intent.putExtra("termino", medicamentos[position].termino)
            intent.putExtra("hora", medicamentos[position].hora)
            intent.putExtra("observacoes", medicamentos[position].observacoes)
            intent.putExtra("position", position)

            intent.putExtra("isSave",false)

            startActivity(intent)

        }

        /**
         * Evento de segurar elemento da lista
         * */
        lv_medicamentos.setOnItemLongClickListener { parent, view, position, id ->


            var alertDialog = AlertDialog.Builder(this)

            alertDialog.setTitle("Aviso!") // O Titulo da notificação
            alertDialog.setMessage("Deseja realmente excluir o medicamento?") // a mensagem ou alerta

            alertDialog.setPositiveButton("SIM", { _, _ ->

                var m = medicamentos[position]

                deletarMedicamento(userId, m.id!!)
            })

            alertDialog.setNegativeButton("Não", { _, _ -> })

            alertDialog.show()

            true

        }


        atualizarLista(userId)
    }

    private fun atualizarLista(userId:String){

        val ref = FirebaseDatabase.getInstance().getReference()

        var query = ref.child("usuarios").child(userId).child("medicamentos")


        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?) {

                medicamentos.clear()

                for(s in p0!!.children) {
                    var me =  s.getValue(Medicamento::class.java)
                    me!!.id = s.key
                    medicamentos.add(me!!)
               }
                litsAdapter = MedicamentosAdapter(this@ActivityControle ,R.layout.layout_lista_medicamentos,medicamentos)
                lv_medicamentos.adapter = litsAdapter
            }

        })


    }

    private fun deletarMedicamento(userId: String, position:String){

        val ref = FirebaseDatabase.getInstance().getReference()
        var query = ref.child("usuarios").child(userId).child("medicamentos")
                .child(position).removeValue()
    }


    override fun onBackPressed() {


        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alerta") // O Titulo da notificação
        alertDialog.setMessage("Deseja sair da sua conta?") // a mensagem ou alerta

        alertDialog.setPositiveButton("Sim", { _, _ ->
           // startActivity(Intent(this, MainActivity::class.java))
            super.onBackPressed()
            finish()
        })

        alertDialog.setNegativeButton("Não", { _, _ ->

        })
        alertDialog.show()
    }
}
