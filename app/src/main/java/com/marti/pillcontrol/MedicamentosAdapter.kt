package com.marti.pillcontrol

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class MedicamentosAdapter( context: Context,val layout: Int, val list:List<Medicamento>):
        ArrayAdapter<Medicamento>(context, layout,list){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(layout,null)

        val nome = view.findViewById<TextView>(R.id.nome)
        val horario = view.findViewById<TextView>(R.id.horario)

        val medic = list[position]

        nome.text = medic.medicamento
        horario.text = medic.hora

        return view
    }


}