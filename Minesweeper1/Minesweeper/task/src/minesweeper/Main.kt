package minesweeper

import java.util.*
import kotlin.random.Random

fun main() {
    Game
}

object Game {
    private val input = Scanner(System.`in`)
    private var field: Field
    private var ended: Boolean = false

    init {
        print("How many mines do you want on the field? ")
        field = Field(mines = input.nextInt())
        println(field.toString())
        turn()
    }

    private fun turn() {
        print("Set/unset mines marks or claim a cell as free: ")
        val x = input.nextInt()
        val y = input.nextInt()
        val command = input.next()

        if (y !in 1..field.height || x !in 1..field.width) {
            println("You've entered wrong coordinates. Try again!")
            turn()
            return
        }

        while (!ended) {
            when (command){
                "free" -> {
                    val gameOver = field.openCell(y, x)
                    when {
                        gameOver -> {
                            field.revealAll()
                            println(field.toString())
                            println("You stepped on a mine and failed!")
                            ended = true
                        }
                        field.isWin() -> {
                            field.revealAll()
                            println(field.toString())
                            println("Congratulations! You found all the mines!")
                            ended = true
                        }
                        else -> {
                            println(field.toString())
                        }
                    }

                }
                "mine" -> {

                    if (field.markCell(y, x)) {
                        field.revealAll()
                        ended = true
                        if (!field.isWin()) {
                            println("You stepped on a mine and failed!")
                        } else {
                            println("Congratulations! You found all the mines!")
                        }

                    } else {
                        println(field.toString())
                    }
                }
                else -> {
                    println("You've entered wrong command. You can use \"free\" or \"mine\" commands. Try again!")
                }
            }

            if(!ended) turn()

        }
    }


}

data class Field(val width: Int = 9, val height: Int = 9, val mines: Int = 0) {
    private val state = Array(height) { Array<Cell>(width) { Cell(0) } }
    private val totalCells: Int = width * height
    private var minesAdded: Int = 0
    private var marked: Int = 0

    fun isOpen(y: Int, x: Int): Boolean {
        return state[y][x].show
    }
    private fun seedMines(y: Int, x: Int) {
        // Add mines to field
        while (minesAdded != mines) {
            val rx = Random.nextInt(0, width)
            val ry = Random.nextInt(0, height)

            if (rx == x && ry == y) continue
            val randomCell = state[ry][rx]

            if(randomCell.type == 0) {
                randomCell.type = -1 // Place mine here
                minesAdded++
            }
        }
        // Generate hints
        for((m, row) in state.withIndex()) {
            for((n, c) in row.withIndex()) {
                var count = 0
                if(c.type == -1) continue

                when {
                    // Upper left
                    m == 0 && n == 0 -> {
                        if(state[0][1].type == -1) count++
                        if(state[1][1].type == -1) count++
                        if(state[1][0].type == -1) count++
                    }
                    // Upper right
                    m == 0 && n == width - 1 -> {
                        if(state[0][n - 1].type == -1) count++
                        if(state[1][n - 1].type == -1) count++
                        if(state[1][n].type == -1) count++
                    }
                    // Lower right
                    m == height - 1 && n == width - 1 -> {
                        if(state[m - 1][n].type == -1) count++
                        if(state[m][n - 1].type == -1) count++
                        if(state[m - 1][n - 1].type == -1) count++
                    }
                    // Lower left
                    m == height - 1 && n == 0 -> {
                        if(state[m - 1][0].type == -1) count++
                        if(state[m][1].type == -1) count++
                        if(state[m - 1][1].type == -1) count++
                    }
                    // Top Row
                    m == 0 && n in 1 until width -> {
                        if(state[0][n + 1].type == -1) count++
                        if(state[1][n + 1].type == -1) count++
                        if(state[1][n].type == -1) count++
                        if(state[1][n - 1].type == -1) count++
                        if(state[0][n - 1].type == -1) count++
                    }
                    // Bottom Row
                    m == height - 1 && n in 1 until width -> {
                        if(state[m - 1][n].type == -1) count++
                        if(state[m - 1][n + 1].type == -1) count++
                        if(state[m][n + 1].type == -1) count++
                        if(state[m][n - 1].type == -1) count++
                        if(state[m - 1][n - 1].type == -1) count++
                    }
                    // First column
                    m in 1 until height && n == 0 -> {
                        if(state[m - 1][0].type == -1) count++
                        if(state[m - 1][1].type == -1) count++
                        if(state[m][1].type == -1) count++
                        if(state[m + 1][1].type == -1) count++
                        if(state[m + 1][0].type == -1) count++
                    }
                    // Last column
                    m in 1 until height && n == width - 1 -> {
                        if(state[m + 1][n].type == -1) count++
                        if(state[m + 1][n - 1].type == -1) count++
                        if(state[m][n - 1].type == -1) count++
                        if(state[m - 1][n - 1].type == -1) count++
                        if(state[m - 1][n].type == -1) count++
                    }
                    // All other cells
                    else -> {
                        if(state[m - 1][n].type == -1) count++
                        if(state[m - 1][n + 1].type == -1) count++
                        if(state[m][n + 1].type == -1) count++
                        if(state[m + 1][n + 1].type == -1) count++
                        if(state[m + 1][n].type == -1) count++
                        if(state[m + 1][n - 1].type == -1) count++
                        if(state[m][n - 1].type == -1) count++
                        if(state[m - 1][n - 1].type == -1) count++
                    }
                }
                // Assign mines count to cell
                state[m][n].type = count
            }
        }
    }

    fun openCell(iy: Int, ix: Int): Boolean {
        val y = iy - 1
        val x = ix - 1
        val cell = state[y][x]

        if (minesAdded == 0) {
            // First turn
            cell.type = 0
            seedMines(y, x)
        }

        // If free cell selected explore free neighbors
        if(cell.type == 0) {
            explore(y, x)
            println(toString())
        }

        return cell.check()

    }

    fun markCell(iy: Int, ix: Int): Boolean {
        val y = iy - 1
        val x = ix - 1
        val cell = state[y][x]

        if (minesAdded == 0) {
            // First turn
            cell.type = 0
            seedMines(y, x)
        }

        if(!cell.show) {
            cell.marked = !cell.marked
            if(cell.marked) {
                marked++
            } else {
                marked--
            }
        }
        return marked == minesAdded
    }

    private fun explore(m: Int, n: Int) {
        if(m > 0) {
            if(state[m - 1][n].reveal()) explore(m - 1, n)
            if(n < width - 1) {
                if(state[m - 1][n + 1].reveal()) explore(m - 1, n + 1)
            }
            if(n > 0) {
                if(state[m - 1][n - 1].reveal()) explore(m - 1, n - 1)
            }
        }
        if(m < height - 1) {
            if(state[m + 1][n].reveal()) explore(m + 1, n)
            if(n < width - 1) {
                if(state[m + 1][n + 1].reveal()) explore(m + 1, n + 1)
            }
            if(n > 0) {
                if(state[m + 1][n - 1].reveal()) explore(m + 1, n - 1)
            }
        }
        if(n < width - 1) {
            if(state[m][n + 1].reveal()) explore(m, n + 1)
        }
        if(n > 0) {
            if(state[m][n - 1].reveal()) explore(m, n - 1)
        }
    }

    fun isWin(): Boolean {
        var count: Int = 0

        // If all marks added
        if(marked == minesAdded) {
            for(row in state) {
                for(cell in row) {
                    if(cell.marked && cell.type == -1) count++
                }
            }
            return count == minesAdded
        }

        for(row in state) {
            for(cell in row) {
                if(cell.show) count++
            }
        }

        return count == totalCells - minesAdded

    }

    fun revealAll() {
        for(row in state) {
            for(cell in row) {
                cell.marked = false
                cell.show = true
            }
        }
    }

    override fun toString(): String {
        val header = (1..width).joinToString("") + "|\n" +
                "-|${"-".repeat(width)}"
        val footer = "-|${"-".repeat(width)}|\n"

        var field = " |$header|\n"

        for ((i, r) in state.withIndex()) {
            val num = i + 1
            var row = ""
            for(cell in r) {
                row += cell.toString()
            }
            field += "$num|$row|\n"
        }
        field += footer
        return field
    }

}

data class Cell(var type: Int = 0) {
    /*
    * TYPES
    * -1 - mine
    * 0 - free
    * 1..8 - mines is near
    * */

    var marked: Boolean = false
    var show: Boolean = false

    fun check(): Boolean {
        show = true
        return type == -1
    }

    fun reveal(): Boolean {
        if(show) return false
        if(type >= 0) {
            marked = false
            show = true
        }
        return type == 0
    }

    override fun toString(): String {
        if(marked) return "*"
        return when {
            type < 0 -> if(show) "X" else "."
            type == 0 -> if(show) "/" else "."
            else -> if(show) "$type" else "."
        }
    }

}
