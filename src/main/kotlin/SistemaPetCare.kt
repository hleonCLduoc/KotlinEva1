import model.*
import kotlinx.coroutines.delay

data class RegistroAtencion(
    val paciente: Paciente,
    val numeroBox : Int,
    val minutosUso : Long,
    val montoFinal: Double

)



class SistemaPetCare {
    val boxes: List<Box> = List(10) { Box(it + 1) }
    val historial: MutableList<RegistroAtencion> = mutableListOf()

    fun validarCodigoAtencion(codigo: String): Boolean {
        val regex = Regex("^[A-Za-z]{2}\\d{2}[A-Za-z]{2}\$")
        return regex.matches(codigo)
    }

    suspend fun registrarEntrada(numeroBox: Int, paciente: Paciente) {
        val box = boxes.find { it.numero == numeroBox }
            ?: throw IllegalArgumentException("El box $numeroBox no existe.")

        if (box.estado !is EstadoBox.Libre) {
            throw IllegalStateException("El box $numeroBox no esta libre.")
        }

        if (!validarCodigoAtencion(paciente.codigoAtencion)) {
            throw IllegalArgumentException("Codigo de atencion invalido: ${paciente.codigoAtencion}")
        }

        box.estado = EstadoBox.EnProceso("Procesando sensor de entrada")
        delay(3000)
        box.estado = EstadoBox.EnAtencion(paciente)
    }

    suspend fun registrarSalida(numeroBox: Int, minutosUso: Long): Double {
        val box = boxes.find { it.numero == numeroBox }
            ?: throw IllegalArgumentException("El box $numeroBox no existe.")

        val estadoActual = box.estado
        if (estadoActual !is EstadoBox.EnAtencion) {
            throw IllegalStateException("El box $numeroBox no tiene una atencion activa.")
        }
        val paciente = estadoActual.paciente
        box.estado = EstadoBox.EnProceso("Procesando sensor de salida")
        delay(6500)

        val montoBase = paciente.calcularMontoCobro(minutosUso)
        var montoFinal = montoBase * 1.19

        if (paciente.tipoDueno.lowercase() == "municipal") {
            montoFinal *= 0.85

        }
        historial.add(RegistroAtencion(paciente, numeroBox, minutosUso, montoFinal))
        box.estado = EstadoBox.Libre

        return montoFinal

    }

    fun obtenerBoxesLibres(): List<Box> {
        return boxes.filter { it.estado is EstadoBox.Libre }
    }

    fun obtenerPacientesConvenio(): List<Paciente>{
    return historial
    .map { it.paciente }
    .filter { it.tipoDueno.lowercase() == "convenio" }
    }




    fun obtenerIngresoPromedio(): Double{
    if (historial.isEmpty()) return 0.0
        return historial.sumOf{it.montoFinal} / historial.size

    }
    fun generarReporteCierre(){
        println("=== * REPORTE DE CIERRE DE TURNO * ====")
        println("Toal Atenciones realizadas: ${historial.size}")
        val totalRecaudado = historial.sumOf{it.montoFinal}
        println("Monto total recaudado (Con iva/descuentos): $$totalRecaudado")
        println("Ingreso promedio por atencion: $${obtenerIngresoPromedio()}")
        println("Boxes actualmente libres: ${obtenerBoxesLibres().size} de 10")

    }
}





