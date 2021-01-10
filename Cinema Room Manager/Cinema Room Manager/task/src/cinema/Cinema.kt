package cinema

import kotlin.math.roundToInt

fun main() {
    Cinema
}

object Cinema {
    private val rooms = mutableListOf<Room>()

    init {
        if (rooms.size == 0) {
            createRoom()
        }
        menu()
    }

    private fun createRoom() {
        println("Enter the number of rows:")
        val rows = readLine()!!.toInt()
        println("Enter the number of seats in each row:")
        val seatsInRow = readLine()!!.toInt()
        val room = Room(rows, seatsInRow)
        rooms.add(room)
    }

    private fun menu() {
        println("""
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent())
        when (readLine()!!) {
            "1" -> {
                println(rooms.first().toString())
                menu()
            }
            "2" -> {
                rooms.first().book()
                menu()
            }
            "3" -> {
                getStat()
                menu()
            }
            "0" -> return
            else -> {
                println("Wrong command!")
                menu()
            }
        }
    }

    private fun getStat() {
        val booked = rooms.sumBy { it.getBookedCount() }
        val capacity = rooms.sumBy { it.capacity }
        val percentage: String = String.format("%.2f", (booked.toFloat() / capacity.toFloat()) * 100.00)
        val currentIncome = rooms.sumBy { it.getCurrentIncome() }
        val plannedIncome = rooms.sumBy { it.getPlannedIncome() }
        println("""
            Number of purchased tickets: $booked
            Percentage: $percentage%
            Current income: $$currentIncome
            Total income: $$plannedIncome
        """.trimIndent())
    }

    private fun calculateEarnings(): Int {
        val rows = rooms.first().rows
        val seatsInRow = rooms.first().seats
        val seats = rooms.first().capacity
        return when {
            seats < 60 -> seats * 10
            seats % 2 == 0 -> {
                (seats / 2) * 18
            }
            else -> {
                (rows / 2) * seatsInRow * 10 + seatsInRow * (rows / 2 + 1) * 8
            }
        }
    }
}

class Room(val rows: Int, val seats: Int) {
    private val hall = List(rows) { MutableList(seats) { true } }
    val capacity = rows * seats

    fun book() {
        println("Enter a row number:")
        val r = readLine()!!.toInt() - 1
        println("Enter a seat number in that row:")
        val s = readLine()!!.toInt() - 1
        if (r !in hall.indices || s !in hall[r].indices) {
            println("Wrong input!")
            book()
        } else if (hall[r][s]) {
            hall[r][s] = false
            println("Ticket price: \$${getPrice(r)}")
        } else {
            println("That ticket has already been purchased!")
            book()
        }
    }

    fun getBookedCount(): Int {
        var count = 0
        for (row in hall) {
            for (seat in row) {
                if (!seat) count++
            }
        }
        return count
    }

    fun getCurrentIncome(): Int {
        var sum = 0
        for (r in hall.indices) {
            for (s in hall[r].indices) {
                if (!hall[r][s]) sum += getPrice(r)
            }
        }
        return sum
    }

    fun getPlannedIncome(): Int {
        var sum = 0
        for (r in hall.indices) {
            for (s in hall[r].indices) {
                sum += getPrice(r)
            }
        }
        return sum
    }

    private fun getPrice(r: Int):Int {
        return when {
            capacity < 60 -> {
                10
            }
            r < rows / 2 -> {
                10
            }
            else -> {
                8
            }
        }
    }

    override fun toString(): String {
        var result = "Cinema:\n "
        repeat(seats) { result += " ${it + 1}" }
        result += "\n"
        for (row in hall.indices) {
            result += row + 1
            for (seat in hall[row]) {
                result += if (seat) " S" else " B"
            }
            result += "\n"
        }
        return result
    }
}