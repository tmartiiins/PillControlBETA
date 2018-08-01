package com.marti.pillcontrol

import java.util.*
import kotlin.collections.ArrayList

class Usuario{

    var login: String? = null
    var senha: String? = null
    var id: String? = null
    var nome:String? = null
    var data:String? = null
    var sexo:String? = null
    var email:String? = null
    var diagnostico : ArrayList<String>? = null
    var medicamentos : ArrayList<Medicamento>? = null

    constructor( login: String,  senha: String,  nome:String,
                 data:String, sexo:String,  email:String){

        this.senha = senha
        this.login = login
        this.nome = nome
        this.data = data
        this.sexo = sexo
        this.email = email
        this.diagnostico = diagnostico
        this.medicamentos = ArrayList<Medicamento>()
        this.medicamentos!!.add(Medicamento())
    }

    constructor()



}