import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    var counter = 0
    var s = 1
    while (counter < n) {
        repeat(s) {
            if (counter < n) print("$s ")
            counter++
        }
        s++
    }
}
