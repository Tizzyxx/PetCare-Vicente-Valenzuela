package org.example

import java.time.LocalDateTime

open class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val tipoDueno: TipoDueno
) {

    val fechaHoraIngreso: LocalDateTime = LocalDateTime.now()

    open fun calcularCosto(minutos: Int): Double {
        return 0.0
    }

    fun calcularTotal(minutos: Int): Double {

        val costo = calcularCosto(minutos)

        val costoConIva = costo * 1.19

        if (tipoDueno == TipoDueno.MUNICIPAL) {
            return costoConIva * 0.50
        }

        return costoConIva
    }

    open fun mostrarDatos() {

        println("Codigo de atencion: $codigoAtencion")
        println("Nombre: $nombre")
        println("Especie: $especie")
        println("Fecha y hora de ingreso: $fechaHoraIngreso")
        println("Tipo de dueno: $tipoDueno")
    }
}