import kotlin.math.max

data class Topic(val timeToLearn: Int, val possibleQuestions: Int)

/**
 * Stores Dynamic Programming matrix and the maximum number of learned questions,
 * which is supposed to be equal to dpMatrix.last().last() (if this value exists, 0 otherwise)
 */
data class DPArray(val numberOfLearnedQuestions: Int, val dpMatrix: Array<IntArray>)

/**
 * This pair is returned by the algorithm to the program
 * @param numberOfLearnedQuestions - the maximum possible number of learned questions
 * @param topicsSet - topics, which should be studied in order to achieve numberOfLearnedQuestions
 */
data class Result(val numberOfLearnedQuestions: Int, val topicsSet: List<Int>)


fun getResult(remainingTime: Int, topics: List<Topic>) : Result {
    val dpArray = getDPArray(remainingTime, topics)
    val topicsSet = restoreAnswer(topics, dpArray.dpMatrix)

    return Result(dpArray.numberOfLearnedQuestions, topicsSet)
}


/**
 * Generates the array of Dynamic Programming.
 * @param remainingTime - time remained to study
 * @param topics - the set of all topics presented
 */
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

/**
 * Restores the answer for a given set of topics  and DP array
 * @param topics - all topics presented
 * @param arrDP - the array of Dynamic Programming
 * @return Set of all topics to be learnt in order to achieve the maximum possible number of learned questions
 */
fun restoreAnswer(topics: List<Topic>, arrDP: Array<IntArray>) : List<Int> {
    if (arrDP.isEmpty())
        return listOf()

    val answer = mutableListOf<Int>()
    var sum = arrDP.first().lastIndex

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
