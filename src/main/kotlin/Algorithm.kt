import kotlin.math.max

data class Topic(val timeToLearn: Int, val possibleQuestions: Int)
data class Result(val numberOfLearnedQuestions: Int, val topicsOrder: List<Int>)

fun getResult(remainingTime: Int, topics: List<Topic>) : Result {
    val dpArray = getTopicsOrder(remainingTime, topics)
    val answer = restoreAnswer(topics, dpArray)

    return Result(dpArray.last().last(), answer)
}

fun getTopicsOrder(remainingTime: Int, topics: List<Topic>) : Array<IntArray> {
    val dpArray = Array(topics.size + 1) { IntArray(remainingTime + 1) }

    for (k in 1 until dpArray.size) {
        for (s in 1 until dpArray[k].size) {
            if (s >= topics[k - 1].timeToLearn)
                dpArray[k][s] = max(
                    dpArray[k - 1][s],
                    dpArray[k - 1][s - topics[k - 1].timeToLearn] + topics[k - 1].possibleQuestions
                )
            else
                dpArray[k][s] = dpArray[k - 1][s]
        }
    }

    return dpArray
}

fun restoreAnswer(topics: List<Topic>, arrDP: Array<IntArray>) : List<Int> {
    val answer = mutableListOf<Int>()
    var sum = arrDP.last().last()

    for  (i in arrDP.indices.reversed()) {
        if (arrDP[i][sum] == 0)
            break
        if (arrDP[i - 1][sum] != arrDP[i][sum]) {
            sum -= topics[i].timeToLearn
            answer.add(i)
        }
    }

    return answer
}
