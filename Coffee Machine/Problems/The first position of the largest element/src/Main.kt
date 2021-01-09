import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var position = 0
    var value: Long = Long.MIN_VALUE
    var counter = 1
    while (scanner.hasNextLong()) {
        val input = scanner.nextLong()
        if (input > value) {
            value = input
            position = counter
        }
        counter++
    }
    println("$value $position")
}