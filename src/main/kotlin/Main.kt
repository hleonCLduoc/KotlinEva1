import model.*
import kotlinx.courutines.runBlocking

class Main ()= runBloking {
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
        val cobroPerro = sistema.registrarSalida(2, 15)
        println("Salida Box 1 (${perro1.nombre}): $$cobroPerro")    }


}