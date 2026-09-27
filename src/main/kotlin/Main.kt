import kotlinx.coroutines.runBlocking
import model.*

fun main ()= runBlocking {
    val sistema = SistemaPetCare()

    println("===INICIANDO TURNO EN PETCARE===")

    val perro1 = Canino("CA12CD", "Pewito", "convenio")
    val gato1 = Felino("FE34AB", "Michis", "particular")
    val exotico1 = Exotico("EX56XY", "Iguana", "municipal", true)

    try {
        println("\n Registrando entrada de pacientes...")
        sistema.registrarEntrada(1, perro1)
        println("Entrada registrada: ${perro1.nombre} en Box 1")

        sistema.registrarEntrada(2, gato1)
        println("Entrada registrada: ${gato1.nombre} en Box 2")

        sistema.registrarEntrada(3, exotico1)
        println("Entrada registrada: ${exotico1.nombre} en Box 3")

    }catch (e: Exception){
        println("Error durante al entrada: ${e.message}")

    }
    try{
        println("\n Probando Validacion de codigo invalido...")
        val pacienteInvalido = Canino("Invalido", "Error", "particular")
        sistema.registrarEntrada(4, pacienteInvalido)

    } catch (e: Exception){
        println("Error capturado correctamente: ${e.message}")
    }

    try {
        println("\n Registando salidas y calculando cobros...")
        val cobroPerro = sistema.registrarSalida(1, 60)
        println("Salida Box 1 (${perro1.nombre}): $$cobroPerro")

        val cobroGato = sistema.registrarSalida(2,15)
        println("Salida Box 2 (${gato1.nombre}): $$cobroGato")

        val cobroExotico = sistema.registrarSalida(3,90)
        println("Salida Box 3 (${exotico1.nombre}): $$cobroExotico")

    }catch (e: Exception){
        println("Error durante la salida: ${e.message}")
    }
    println("\n === PACIENTES CON CONVENIO ===")
    sistema.obtenerPacientesConvenio().forEach{
        println("- ${it.nombre}(${it.especie})")
    }
    println()
    sistema.generarReporteCierre()
}