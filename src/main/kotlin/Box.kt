package org.example

class Box(
    val numero: Int,
    var estado: EstadoBox = EstadoBox.Libre
) {

    fun mostrarEstado() {

        when (val estadoActual = estado) {

            is EstadoBox.Libre -> {
                println("Box $numero: Libre")
            }

            is EstadoBox.EnAtencion -> {
                println(
                    "Box $numero: En atencion - Paciente: " +
                            estadoActual.paciente.nombre
                )
            }

            is EstadoBox.EnProceso -> {
                println(
                    "Box $numero: En proceso - Motivo: " +
                            estadoActual.motivo
                )
            }

            is EstadoBox.FueraDeServicio -> {
                println(
                    "Box $numero: Fuera de servicio - Motivo: " +
                            estadoActual.motivo
                )
            }
        }
    }
}