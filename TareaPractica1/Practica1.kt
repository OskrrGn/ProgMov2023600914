fun main() {
    val scanner = Scanner(System.`in`)

    var opcion: Int
    do {
        println("\nMenu:")
        println("1. Sumar tres numeros.")
        println("2. Ingresar nombre completo.")
        println("3. Calcular tiempo vivido.")
        println("4. Salir del programa.")
        print("Seleccione una opcion (1-4): ")
        opcion = scanner.nextInt()

        when (opcion) {
            1 -> sumarNumeros()
            2 -> ingresarNombre()
            3 -> calcularTiempoVivido()
            4 -> println("Adios Popo")
            else -> println("Opcion no valida. Intente de nuevo.")
        }

    } while (opcion != 4)

    scanner.close()
}

fun sumarNumeros() {
    val scanner = Scanner(System.`in`)
    println("Ingrese tres numeros:")
    val num1 = scanner.nextDouble()
    val num2 = scanner.nextDouble()
    val num3 = scanner.nextDouble()
    val suma = num1 + num2 + num3
    println("La suma de los numeros es: $suma")
}

fun ingresarNombre() {
    val scanner = Scanner(System.`in`)
    println("Ingrese su nombre completo:")
    scanner.nextLine() // Consumir salto de línea pendiente
    val nombre = scanner.nextLine()
    println("Hola, $nombre!")
}

fun calcularTiempoVivido() {
    val scanner = Scanner(System.`in`)
    println("Ingrese su fecha de nacimiento (DD-MM-AAAA):")
    val fechaNacimientoStr = scanner.next()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    try {
        val fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter)
        val fechaActual = LocalDate.now()

        val mesesVividos = ChronoUnit.MONTHS.between(fechaNacimiento, fechaActual)
        val semanasVividas = ChronoUnit.WEEKS.between(fechaNacimiento, fechaActual)
        val diasVividos = ChronoUnit.DAYS.between(fechaNacimiento, fechaActual)

        val fechaNacimientoTiempo = fechaNacimiento.atStartOfDay()
        val fechaActualTiempo = LocalDateTime.now()

        val horasVividas = ChronoUnit.HOURS.between(fechaNacimientoTiempo, fechaActualTiempo)
        val minutosVividos = ChronoUnit.MINUTES.between(fechaNacimientoTiempo, fechaActualTiempo)
        val segundosVividos = ChronoUnit.SECONDS.between(fechaNacimientoTiempo, fechaActualTiempo)

        println("Ha vivido aproximadamente:")
        println("- $mesesVividos meses")
        println("- $semanasVividas semanas")
        println("- $diasVividos dias")
        println("- $horasVividas horas")
        println("- $minutosVividos minutos")
        println("- $segundosVividos segundos")

    } catch (e: Exception) {
        println("Formato de fecha incorrecto. Use DD-MM-AAAA.")
    }
}
