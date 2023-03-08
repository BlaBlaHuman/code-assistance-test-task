
fun parseInput() : Int {
    var x: Int? = null

    while (x == null) {
        try {
            x = readln().toInt()
        } catch (e: NumberFormatException) {
            println("Seems like that's not a number, try again:")
        }
    }

    return x
}
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

    result.topicsOrder.forEach {
        print("$it ")
    }

}
