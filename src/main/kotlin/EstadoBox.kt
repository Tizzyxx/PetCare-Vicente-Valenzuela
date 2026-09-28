package org.example

sealed class EstadoBox {

    object Libre : EstadoBox()

    class EnAtencion(
        val paciente: Paciente
    ) : EstadoBox()

    class EnProceso(
        val motivo: String
    ) : EstadoBox()

    class FueraDeServicio(
        val motivo: String
    ) : EstadoBox()
}