import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var i = scanner.nextInt()
    var counter = 0
    while (i != 0) {
        i = scanner.nextInt()
        counter++
    }
    println(counter)
}