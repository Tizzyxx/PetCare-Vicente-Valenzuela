package org.example

import kotlinx.coroutines.delay

class SistemaPetCare {

    val boxes = mutableListOf<Box>()

    val historialTickets = mutableListOf<Ticket>()

    val historialPacientes = mutableListOf<Paciente>()

    var recaudacionTotal: Double = 0.0

    var recaudacionCanino: Double = 0.0
    var recaudacionFelino: Double = 0.0
    var recaudacionExotico: Double = 0.0

    var numeroTicket: Int = 1


    fun crearBoxes() {

        for (numero in 1..10) {
            boxes.add(Box(numero))
        }
    }


    fun mostrarBoxes() {

        for (box in boxes) {
            box.mostrarEstado()
        }
    }


    fun contarBoxesDisponibles(): Int {

        var contador = 0

        for (box in boxes) {

            if (box.estado is EstadoBox.Libre) {
                contador++
            }
        }

        return contador
    }


    fun buscarPrimerBoxLibre(): Box? {

        for (box in boxes) {

            if (box.estado is EstadoBox.Libre) {
                return box
            }
        }

        return null
    }


    fun buscarBoxPorCodigo(codigo: String): Box? {

        for (box in boxes) {

            val estadoActual = box.estado

            if (estadoActual is EstadoBox.EnAtencion) {

                if (estadoActual.paciente.codigoAtencion == codigo) {
                    return box
                }
            }
        }

        return null
    }
    fun codigoYaRegistrado(codigo: String): Boolean {

        for (box in boxes) {

            val estadoActual = box.estado

            if (estadoActual is EstadoBox.EnAtencion) {

                if (estadoActual.paciente.codigoAtencion == codigo) {
                    return true
                }
            }
        }

        for (paciente in historialPacientes) {

            if (paciente.codigoAtencion == codigo) {
                return true
            }
        }

        return false
    }



    fun obtenerTipoPaciente(paciente: Paciente): String {

        return when (paciente) {

            is Canino -> "Canino"

            is Felino -> "Felino"

            is Exotico -> "Exotico"

            else -> "Paciente"
        }
    }


    suspend fun registrarEntrada(paciente: Paciente) {

        if (!validarCodigoAtencion(paciente.codigoAtencion)) {
            println("Error: Codigo de atencion invalido")
            return
        }
        if (codigoYaRegistrado(paciente.codigoAtencion)) {
            println("Error: Codigo de atencion ya registrado")
            return
        }

        val boxLibre = buscarPrimerBoxLibre()

        if (boxLibre == null) {
            println("Error: No hay boxes disponibles")
            return
        }

        boxLibre.estado = EstadoBox.EnProceso(
            "Registrando entrada"
        )

        println(
            "Procesando entrada del paciente ${paciente.nombre}..."
        )

        boxLibre.mostrarEstado()

        delay(3000)

        boxLibre.estado = EstadoBox.EnAtencion(
            paciente
        )

        println("Entrada registrada correctamente")

        boxLibre.mostrarEstado()
    }


    suspend fun registrarSalida(
        codigo: String,
        minutosAtencion: Int
    ) {

        val box = buscarBoxPorCodigo(codigo)

        if (box == null) {
            println("Error: Paciente no encontrado")
            return
        }

        val estadoActual = box.estado

        if (estadoActual is EstadoBox.EnAtencion) {

            val paciente = estadoActual.paciente

            box.estado = EstadoBox.EnProceso(
                "Calculando tarifa"
            )
            if (minutosAtencion < 0) {
                println("Error: Tiempo de atencion invalido")
                return
            }

            println(
                "Procesando salida de ${paciente.nombre}..."
            )

            box.mostrarEstado()

            delay(6500)

            try {

                val total = paciente.calcularTotal(
                    minutosAtencion
                )

                val felinoGratis =
                    paciente is Felino &&
                            minutosAtencion < 20

                if (total < 0.0) {
                    throw IllegalArgumentException(
                        "Resultado de tarifa invalido"
                    )
                }

                if (total == 0.0 && !felinoGratis) {
                    throw IllegalArgumentException(
                        "Resultado de tarifa invalido"
                    )
                }

                val ticket = Ticket(
                    numeroTicket,
                    obtenerTipoPaciente(paciente),
                    paciente.codigoAtencion,
                    minutosAtencion,
                    total
                )

                historialTickets.add(ticket)

                historialPacientes.add(paciente)

                recaudacionTotal =
                    recaudacionTotal + total
                when (paciente) {

                    is Canino -> {
                        recaudacionCanino =
                            recaudacionCanino + total
                    }

                    is Felino -> {
                        recaudacionFelino =
                            recaudacionFelino + total
                    }

                    is Exotico -> {
                        recaudacionExotico =
                            recaudacionExotico + total
                    }
                }

                numeroTicket++

                box.estado = EstadoBox.Libre

                println("Salida registrada correctamente")

                ticket.mostrarTicket()

                println(
                    "Box ${box.numero} liberado"
                )

            } catch (e: IllegalArgumentException) {

                println(
                    "Error: ${e.message}"
                )

                box.estado = EstadoBox.EnAtencion(paciente)
            }
        }
    }
    fun obtenerPacientesConvenio(): MutableList<Paciente> {

        val pacientesConvenio = mutableListOf<Paciente>()

        for (paciente in historialPacientes) {

            if (paciente.tipoDueno == TipoDueno.CONVENIO) {
                pacientesConvenio.add(paciente)
            }
        }

        return pacientesConvenio
    }

    fun calcularIngresoPromedio(): Double {

        if (historialTickets.isEmpty()) {
            return 0.0
        }

        return recaudacionTotal / historialTickets.size
    }


    fun obtenerCodigosFinalizados(): MutableList<String> {

        val codigos = mutableListOf<String>()

        for (ticket in historialTickets) {
            codigos.add(ticket.codigoAtencion)
        }

        return codigos
    }


    fun obtenerPacienteMayorTiempo(): Paciente? {

        if (historialTickets.isEmpty()) {
            return null
        }

        var ticketMayor = historialTickets[0]

        for (ticket in historialTickets) {

            if (ticket.minutosAtencion > ticketMayor.minutosAtencion) {
                ticketMayor = ticket
            }
        }

        for (paciente in historialPacientes) {

            if (paciente.codigoAtencion == ticketMayor.codigoAtencion) {
                return paciente
            }
        }

        return null
    }
    fun obtenerTipoMayorIngreso(): String {

        var totalCanino = 0.0
        var totalFelino = 0.0
        var totalExotico = 0.0

        for (ticket in historialTickets) {

            when (ticket.tipoPaciente) {

                "Canino" -> {
                    totalCanino = totalCanino + ticket.montoPagado
                }

                "Felino" -> {
                    totalFelino = totalFelino + ticket.montoPagado
                }

                "Exotico" -> {
                    totalExotico = totalExotico + ticket.montoPagado
                }
            }
        }

        if (historialTickets.isEmpty()) {
            return "Sin atenciones"
        }

        if (totalCanino >= totalFelino &&
            totalCanino >= totalExotico
        ) {
            return "Canino"
        }

        if (totalFelino >= totalCanino &&
            totalFelino >= totalExotico
        ) {
            return "Felino"
        }

        return "Exotico"
    }

    fun mostrarReporteCierre() {

        println()
        println("========== CIERRE DE TURNO ==========")

        println()
        println("Pacientes atendidos:")

        for (ticket in historialTickets) {

            println()

            println("Ticket: ${ticket.numero}")
            println("Tipo: ${ticket.tipoPaciente}")
            println("Codigo: ${ticket.codigoAtencion}")
            println("Tiempo: ${ticket.minutosAtencion} minutos")
            println("Monto: $${ticket.montoPagado}")
        }

        println()
        println("-------------------------------------")

        println(
            "Total recaudado: $$recaudacionTotal"
        )
        println(
            "Recaudacion Canino: $$recaudacionCanino"
        )

        println(
            "Recaudacion Felino: $$recaudacionFelino"
        )

        println(
            "Recaudacion Exotico: $$recaudacionExotico"
        )

        println(
            "Cantidad de pacientes atendidos: ${historialTickets.size}"
        )

        println(
            "Ingreso promedio: $${calcularIngresoPromedio()}"
        )

        println(
            "Tipo con mayor ingreso: ${obtenerTipoMayorIngreso()}"
        )

        println(
            "Boxes disponibles: ${contarBoxesDisponibles()}"
        )

        println("=====================================")
    }
}