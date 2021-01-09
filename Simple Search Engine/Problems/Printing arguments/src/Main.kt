fun main(args: Array<String>) {
    if (args.size != 3) {
        println("Invalid number of program arguments")
    } else {
        for ((i, s) in args.withIndex()) {
            val arr = s.split(" ")
            println("Argument ${i + 1}: ${arr.first()}. It has ${arr.first().length} characters")
        }
    }
}
