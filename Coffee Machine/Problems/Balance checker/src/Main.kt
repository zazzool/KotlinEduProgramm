import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var balance = scanner.nextInt()
    var totalSpend = 0
    while (scanner.hasNextInt() && balance >= 0) {
        val spend = scanner.nextInt()
        if (balance >= spend) {
            balance -= spend
            totalSpend += spend
        } else {
            println("Error, insufficient funds for the purchase. You have $balance, but you need $spend.")
            balance -= spend
        }
    }
    if (balance >= 0) println("Thank you for choosing us to manage your account! You have $balance money.")
}
