package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {

    println("====================================")
    println("       SISTEMA PETCARE")
    println("====================================")

    val sistema = SistemaPetCare()

    sistema.crearBoxes()


    // Corrutina principal de atenciones
    launch {

        // ====================================
        // PACIENTE 1 - MAX
        // ====================================

        println()
        println("========== PACIENTE 1 ==========")

        val max = Canino(
            "CA12CD",
            "Max",
            "Golden Retriever",
            TipoDueno.CONVENIO
        )

        sistema.registrarEntrada(max)

        sistema.registrarSalida(
            "CA12CD",
            75
        )


        // ====================================
        // PACIENTE 2 - LUNA
        // ====================================

        println()
        println("========== PACIENTE 2 ==========")

        val luna = Canino(
            "CA99ZA",
            "Luna",
            "Labrador",
            TipoDueno.PARTICULAR
        )

        sistema.registrarEntrada(luna)

        sistema.registrarSalida(
            "CA99ZA",
            180
        )


        // ====================================
        // PACIENTE 3 - MISI
        // ====================================

        println()
        println("========== PACIENTE 3 ==========")

        val misi = Felino(
            "FE22TO",
            "Misi",
            "Siames",
            TipoDueno.PARTICULAR
        )

        sistema.registrarEntrada(misi)

        sistema.registrarSalida(
            "FE22TO",
            18
        )


        // ====================================
        // PACIENTE 4 - LORO
        // ====================================

        println()
        println("========== PACIENTE 4 ==========")

        val loro = Exotico(
            "EX44RG",
            "Loro",
            "Amazonico",
            TipoDueno.MUNICIPAL,
            true
        )

        sistema.registrarEntrada(loro)

        sistema.registrarSalida(
            "EX44RG",
            120
        )


        // ====================================
        // PACIENTE 5 - IGUANA
        // ====================================

        println()
        println("========== PACIENTE 5 ==========")

        val iguana = Exotico(
            "EX77RG",
            "Iguana",
            "Verde",
            TipoDueno.PARTICULAR,
            false
        )

        sistema.registrarEntrada(iguana)

        sistema.registrarSalida(
            "EX77RG",
            45
        )


        // ====================================
        // CONSULTAS
        // ====================================

        println()
        println("========== CONSULTAS ==========")

        println(
            "Boxes disponibles: ${sistema.contarBoxesDisponibles()}"
        )


        println()
        println("Pacientes convenio:")

        val pacientesConvenio =
            sistema.obtenerPacientesConvenio()

        for (paciente in pacientesConvenio) {

            println(
                "${paciente.codigoAtencion} - ${paciente.nombre}"
            )
        }


        println()
        println(
            "Ingreso promedio: $${sistema.calcularIngresoPromedio()}"
        )


        println()
        println("Codigos finalizados:")

        val codigos =
            sistema.obtenerCodigosFinalizados()

        for (codigo in codigos) {
            println(codigo)
        }


        println()

        val pacienteMayorTiempo =
            sistema.obtenerPacienteMayorTiempo()

        if (pacienteMayorTiempo != null) {

            println(
                "Paciente con mayor tiempo: ${pacienteMayorTiempo.nombre}"
            )
        }


        // ====================================
        // CIERRE DEL TURNO
        // ====================================

        sistema.mostrarReporteCierre()
    }


    // ====================================
    // DEMOSTRACION DE ASINCRONIA
    // ====================================

    launch {

        // Mientras se esperan los 3 segundos de entrada
        delay(1000)

        println()
        println("------------------------------------")
        println("El sistema continua funcionando")
        println("mientras se registra la entrada.")
        println(
            "Boxes disponibles: " +
                    sistema.contarBoxesDisponibles()
        )
        println("------------------------------------")


        // Esperamos hasta que la salida se este procesando
        delay(4000)

        println()
        println("------------------------------------")
        println("El sistema continua funcionando")
        println("mientras se calcula la salida.")
        println(
            "Boxes disponibles: " +
                    sistema.contarBoxesDisponibles()
        )
        println("------------------------------------")
    }
}