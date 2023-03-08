import kotlin.math.max

data class Topic(val timeToLearn: Int, val possibleQuestions: Int)
data class DPArray(val numberOfLearnedQuestions: Int, val dpMatrix: Array<IntArray>)
data class Result(val numberOfLearnedQuestions: Int, val topicsOrder: List<Int>)

fun getResult(remainingTime: Int, topics: List<Topic>) : Result {
    val dpArray = getDPArray(remainingTime, topics)
    val topicsOrder = restoreAnswer(topics, dpArray.dpMatrix)

    return Result(dpArray.numberOfLearnedQuestions, topicsOrder)
}

fun getDPArray(remainingTime: Int, topics: List<Topic>) : DPArray {
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

    val result = try {
        dpArray.last().last()
    }
    catch (e: NoSuchElementException) {
        0
    }

    return DPArray(result, dpArray)
}

fun restoreAnswer(topics: List<Topic>, arrDP: Array<IntArray>) : List<Int> {
    val answer = mutableListOf<Int>()

    if (arrDP.isEmpty())
        return listOf()

    var sum = arrDP.last().size - 1

    if (sum < 0)
        return listOf()

    for  (i in arrDP.indices.reversed()) {
        if (arrDP[i][sum] == 0)
            break
        if (arrDP[i - 1][sum] != arrDP[i][sum]) {
            sum -= topics[i - 1].timeToLearn
            answer.add(i)
        }
    }

    return answer.reversed()
}
