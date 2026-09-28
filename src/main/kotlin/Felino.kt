package org.example

class Felino(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    tipoDueno: TipoDueno
) : Paciente(
    codigoAtencion,
    nombre,
    especie,
    tipoDueno
) {

    val tarifaBase: Double = 9000.0

    override fun calcularCosto(minutos: Int): Double {

        if (minutos < 20) {
            return 0.0
        }

        val horas = minutos / 60.0
        val costo = horas * tarifaBase

        return costo
    }
}