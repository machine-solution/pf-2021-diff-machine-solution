import kotlin.test.*





internal class Test1 {

    @Test
    fun longestCommonSubsequenceLengthTest() {
        assertEquals(0, longestCommonSubsequenceLength(arrayOf("A", "B", "C"), arrayOf("ABC", "D", "E")))
        assertEquals(2, longestCommonSubsequenceLength(arrayOf("A", "b", "C"), arrayOf("A", "B", "C")))
        assertEquals(1, longestCommonSubsequenceLength(arrayOf("A B", "C"), arrayOf("A", "B", "E", "C")))
        assertEquals(2, longestCommonSubsequenceLength(arrayOf("A B", "C"), arrayOf("A", "A B", "E", "C")))
        assertEquals(4, longestCommonSubsequenceLength(arrayOf("A", "B", "C", "D", "E", "F"),
            arrayOf("B", "D", "C", "A", "E", "F")))
        assertEquals(3, longestCommonSubsequenceLength(arrayOf("B", "A", "N", "A", "N", "A"),
            arrayOf("A", "T", "A", "N")))
        assertEquals(0, longestCommonSubsequenceLength(arrayOf("B", "A", "N", "A", "N", "A"), arrayOf()))
    }

    // if answer is a common subsequence, return answer.size, else return 0
    fun longestCommonSubsequenceChecker(sequence1: Array<String>, sequence2: Array<String>, answer: Array<String>): Int {
        fun isSubsequence(subsequence: Array<String>, sequence: Array<String>): Boolean {
            var it1: Int = 0
            for (it2 in sequence.indices) {
                if (it1 == subsequence.size)
                    return true
                if (sequence[it2] == subsequence[it1]) {
                    it1++;
                }
            }
            return it1 == subsequence.size
        }

        return if (isSubsequence(answer, sequence1) && isSubsequence(answer, sequence2)) {
           answer.size
        } else {
            0
        }
    }

    @Test
    fun longestCommonSubsequenceTest() {
        assertEquals(0, longestCommonSubsequenceChecker(arrayOf("A", "B", "C"), arrayOf("ABC", "D", "E"),
            longestCommonSubsequence(arrayOf("A", "B", "C"), arrayOf("ABC", "D", "E"))))

    }
}
