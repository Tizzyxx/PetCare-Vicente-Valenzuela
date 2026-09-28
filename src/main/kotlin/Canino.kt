package org.example

class Canino(
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

    val tarifaBase: Double = 12000.0

    override fun calcularCosto(minutos: Int): Double {

        val horas = minutos / 60.0
        var costo = horas * tarifaBase

        if (tipoDueno == TipoDueno.CONVENIO) {
            costo = costo * 0.80
        }

        return costo
    }
}