import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    // put your code here
    var inp = scanner.nextInt()
    print(inp)
    while (inp > 1) {
        inp = if (inp % 2 == 0) inp / 2 else inp * 3 + 1
        print(" $inp")
    }
}
