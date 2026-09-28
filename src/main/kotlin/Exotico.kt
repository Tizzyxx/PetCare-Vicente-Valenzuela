package org.example

class Exotico(
    codigoAtencion: String,
    nombre: String,
    especie: String,
    tipoDueno: TipoDueno,
    val silvestre: Boolean
) : Paciente(
    codigoAtencion,
    nombre,
    especie,
    tipoDueno
) {

    val tarifaBase: Double = 20000.0

    override fun calcularCosto(minutos: Int): Double {

        val horas = minutos / 60.0
        var costo = horas * tarifaBase

        if (silvestre) {
            costo = costo * 1.30
        }

        return costo
    }

    override fun mostrarDatos() {

        super.mostrarDatos()

        if (silvestre) {
            println("Animal silvestre: Si")
        } else {
            println("Animal silvestre: No")
        }
    }
}