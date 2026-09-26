import model.*
import kotlinx.coroutines.delay

data class RegistroAtencion(
    val paciente: Paciente,
    val numeroBox : Int,
    val minutosUso : Long,
    val montoFinal: Double

)



class SistemaPetCare {
    val boxes : List<Box> = List (10){Box(it +1 )}
    val historial : MutableList<RegistroAtencion> = mutableListOf()

    fun validarCodigoAtencion(codigo: String): Boolean {
        val regex = Regex("^[A-Za-z]{2}\\d{2}[A-Za-z]{2}\$")
        return regex.matches(codigo)
    }

    suspend fun registrarEntrada(numeroBox : Int, paciente : Paciente) : RegistroAtencion {
        val box = boxes.find { it.numero == numeroBox }
            ?: throw IllegalArgumentException("El box $numeroBox no existe.")

            if (box.estado !is EstadoBox.Libre){
                throw IllegalArgumentException("El box $box no esta libre.")
            }

            if(!validarCodigoAtencion(paciente.codigoAtencion)){
                throw IllegalArgumentException("Codigo de Atencion Invalido ${paciente.codigoAtencion} .")
            }

            box.estado = EstadoBox.EnProceso("Procesando snesor de entrada")
        delay(3000)
        box.estado = EstadoBox.EnProceso(paciente)

}