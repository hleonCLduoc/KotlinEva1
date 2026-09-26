package model

import java.time.LocalDateTime

open class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val fechaIngreso: LocalDateTime = LocalDateTime.now(),
    val tipoDueno: String
) {
    open val tarifaBaseHora: Double = 0.0

    open fun calcularMontoCobro(minutosUso: Long): Double {
        val horas = minutosUso / 60.0
        return horas * tarifaBaseHora
    }
}

class Canino(
    codigoAtencion: String,
    nombre: String,
    tipoDueno: String
) : Paciente(codigoAtencion, nombre, "Canino", tipoDueno = tipoDueno) {
    override val tarifaBaseHora: Double = 12000.0

    override fun calcularMontoCobro(minutosUso: Long): Double {
        val montoBase = super.calcularMontoCobro(minutosUso)
        return if (tipoDueno.lowercase() == "convenio") {
            montoBase * 0.80
        } else {
            montoBase
        }
    }
}

class Felino(
    codigoAtencion: String,
    nombre: String,
    tipoDueno: String
) : Paciente(codigoAtencion, nombre, "Felino", tipoDueno = tipoDueno) {
    override val tarifaBaseHora: Double = 9000.0

    override fun calcularMontoCobro(minutosUso: Long): Double {
        if (minutosUso < 20) {
            return 0.0
        }
        return super.calcularMontoCobro(minutosUso)
    }
}

class Exotico(
    codigoAtencion: String,
    nombre: String,
    tipoDueno: String,
    val esSilvestre: Boolean
) : Paciente(codigoAtencion, nombre, "Exotico", tipoDueno = tipoDueno) {
    override val tarifaBaseHora: Double = 20000.0

    override fun calcularMontoCobro(minutosUso: Long): Double {
        val montoBase = super.calcularMontoCobro(minutosUso)
        return if (esSilvestre) {
            montoBase * 1.30
        } else {
            montoBase
        }
    }
}