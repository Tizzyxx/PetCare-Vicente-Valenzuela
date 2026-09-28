package org.example

fun validarCodigoAtencion(codigo: String): Boolean {

    if (codigo.length != 6) {
        return false
    }

    val primerasLetras =
        codigo[0] in 'A'..'Z' &&
                codigo[1] in 'A'..'Z'

    val ultimasLetras =
        codigo[4] in 'A'..'Z' &&
                codigo[5] in 'A'..'Z'

    if (!primerasLetras || !ultimasLetras) {
        return false
    }

    return try {

        codigo.substring(2, 4).toInt()

        true

    } catch (e: NumberFormatException) {

        false
    }
}