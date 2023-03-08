import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals

data class TestData(val remainingTime: Int, val topics: List<Topic>, val correctResult: Result)

private val dataSet1 = TestData(
    26,
    listOf(
        Topic(12, 24),
        Topic(7, 13),
        Topic(11, 23),
        Topic(8, 15),
        Topic(9, 16),
    ),
    Result(51,
    listOf(2, 3, 4)))


private val dataSet2 = TestData(170,
    listOf(
        Topic(41, 442),
        Topic(50, 525),
        Topic(49, 511),
        Topic(59, 593),
        Topic(55, 546),
        Topic(57, 564),
        Topic(60, 617),
    ),
    Result(1735,
    listOf(2, 4, 7)))

private val dataSet3 = TestData(50,
    listOf(
        Topic(31, 70),
        Topic(10, 20),
        Topic(20, 39),
        Topic(19, 37),
        Topic(4, 7),
        Topic(3, 5),
        Topic(6, 10),
    ),
    Result(107,
    listOf(1, 4)))

private val dataSet4 = TestData(750,
    listOf(
        Topic(70, 135),
        Topic(73, 139),
        Topic(77, 149),
        Topic(80, 150),
        Topic(82, 156),
        Topic(87, 163),
        Topic(90, 173),
        Topic(94, 184),
        Topic(98, 192),
        Topic(106, 201),
        Topic(110, 210),
        Topic(113, 214),
        Topic(115, 221),
        Topic(118, 229),
        Topic(120, 240),
    ),
    Result(1458,
    listOf(1, 3, 5, 7, 8, 9, 14, 15)))

private val dataSetNoTime = TestData(0,
    listOf(
        Topic(70, 135),
        Topic(73, 139),
        Topic(77, 149),
        Topic(80, 150),
        Topic(82, 156),
        Topic(87, 163),
        Topic(90, 173),
        Topic(94, 184),
        Topic(98, 192),
        Topic(106, 201),
        Topic(110, 210),
        Topic(113, 214),
        Topic(115, 221),
        Topic(118, 229),
        Topic(120, 240),
    ),
    Result(0,
    listOf()))

private val dataSetNoTopics = TestData(357,
    listOf(),
    Result(0,
    listOf()))

private val dataSetNoTopicsNoTime = TestData(0,
    listOf(),
    Result(0,
    listOf()))

class GetDPArrayTest {
    @ParameterizedTest
    @MethodSource("provideInputForGetDPArray")
    fun testGetTopicsOrder(data: TestData){
        assertEquals(data.correctResult.numberOfLearnedQuestions, getDPArray(data.remainingTime, data.topics).numberOfLearnedQuestions)
    }

    companion object {
        @JvmStatic
        private fun provideInputForGetDPArray(): Stream<Arguments?> {
            return Stream.of(
                Arguments.of(dataSet1),
                Arguments.of(dataSet2),
                Arguments.of(dataSet3),
                Arguments.of(dataSet4),
                Arguments.of(dataSetNoTopics),
                Arguments.of(dataSetNoTime),
                Arguments.of(dataSetNoTopicsNoTime),
            )
        }
    }
}

class RestoreAnswerTest {
    @ParameterizedTest
    @MethodSource("provideInputForRestoreAnswer")
    fun testGetRestoreAnswer(data: TestData) {
        assertEquals(data.correctResult.topicsOrder, restoreAnswer(data.topics, getDPArray(data.remainingTime, data.topics).dpMatrix))

    }
    companion object {
        @JvmStatic
        private fun provideInputForRestoreAnswer(): Stream<Arguments?> {
            return Stream.of(
                Arguments.of(dataSet1),
                Arguments.of(dataSet2),
                Arguments.of(dataSet3),
                Arguments.of(dataSet4),
                Arguments.of(dataSetNoTime),
                Arguments.of(dataSetNoTopics),
                Arguments.of(dataSetNoTopicsNoTime),
            )
        }
    }
}
