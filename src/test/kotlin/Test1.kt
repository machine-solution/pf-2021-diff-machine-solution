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
}
