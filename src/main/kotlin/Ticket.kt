package org.example

data class Ticket(
    val numero: Int,
    val tipoPaciente: String,
    val codigoAtencion: String,
    val minutosAtencion: Int,
    val montoPagado: Double
) {

    fun mostrarTicket() {

        println("----- TICKET $numero -----")
        println("Tipo: $tipoPaciente")
        println("Codigo: $codigoAtencion")
        println("Tiempo: $minutosAtencion minutos")
        println("Monto pagado: $$montoPagado")
    }
}