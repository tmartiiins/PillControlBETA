package com.marti.pillcontrol

import java.util.*

class Medicamento{
    var  tratamento:String? = null
    var  medicamento:String? = null
    var inicio:String? = null
    var termino:String? = null
    var hora:String? = null
    var observacoes:String? = null
    var id:String? = null

    constructor(tratamento:String, medicamento:String, inicio:String, termino:String, hora:String, observacoes:String){


        this.tratamento = tratamento
        this.medicamento = medicamento
        this.inicio = inicio
        this.termino = termino
        this.hora = hora
        this.observacoes = observacoes
    }

    constructor()
}