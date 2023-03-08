/**
 * parseInput() is used to read a non-negative number from standard input
 */
fun parseInput() : Int {
    var x: Int? = null

    while (x == null || x < 0) {
        try {
            x = readln().toInt()
            if (x < 0)
                println("Please, enter a non-negative number. Try again:")
        } catch (e: NumberFormatException) {
            println("Seems like that's not a number. Try again:")
        }

    }

    return x
}

/**
 * Provides a simple CLI
 */
fun runTheAlgorithm() {
    println("Welcome to the Great Problem Solver!")

    println("Please, type in your data:\n" +
            "Enter your value for N:")
    val remainingTime: Int = parseInput()

    println("Enter your value for M:")
    val numberOfTopics: Int = parseInput()

    val topics = mutableListOf<Topic>()

    (1..numberOfTopics).forEach{ i ->
        println("Enter T_i and K_i for the $i-th topic:")
        val time = parseInput()
        val questions = parseInput()
        topics.add(Topic(time, questions))
    }

    println("Enter your value for L:")
    val numberOfQuestions: Int = parseInput()

    val result = getResult(remainingTime, topics)

    println("With $remainingTime hours left the programmer can manage to learn ${result.numberOfLearnedQuestions}/$numberOfQuestions questions " +
            "by studying the following topics:")

    result.topicsSet.forEach {
        print("$it ")
    }

}
