import kotlin.math.max

data class Topic(val timeToLearn: Int, val possibleQuestions: Int)

/**
 * This pair is returned by the algorithm to the program
 * @param numberOfLearnedQuestions - the maximum possible number of learned questions
 * @param topicsSet - topics, which should be studied in order to achieve numberOfLearnedQuestions
 */
data class Result(val numberOfLearnedQuestions: Int, val topicsSet: List<Int>)


/**
 * Combines two functions getDPArray and restoreAnswer together.
 * Supposed to receive initial data and return the final answer.
 * @param remainingTime - time remained to study
 * @param topics - the set of all topics presented
 */
fun getResult(remainingTime: Int, topics: List<Topic>) : Result {
    val dpArray = getDPArray(remainingTime, topics)
    val topicsSet = restoreAnswer(topics, dpArray)

    return Result(dpArray.last().last(), topicsSet)
}


/**
 * Generates the array of Dynamic Programming.
 * @param remainingTime - time remained to study
 * @param topics - the set of all topics presented
 */
fun getDPArray(remainingTime: Int, topics: List<Topic>) : Array<IntArray> {
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

/**
 * Restores the answer for a given set of topics  and DP array
 * @param topics - all topics presented
 * @param arrDP - the array of Dynamic Programming
 * @return Set of all topics to be learnt in order to achieve the maximum possible number of learned questions
 */
fun restoreAnswer(topics: List<Topic>, arrDP: Array<IntArray>) : List<Int> {
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
