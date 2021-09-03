import kotlin.test.*

internal class Test1 {

    @Test
    fun longestCommonSubsequenceLenthTest() {
        assertEquals(0, longestCommonSubsequenceLenth(arrayOf("A", "B", "C"), arrayOf("ABC", "D", "E")))
        assertEquals(2, longestCommonSubsequenceLenth(arrayOf("A", "b", "C"), arrayOf("A", "B", "C")))
        assertEquals(1, longestCommonSubsequenceLenth(arrayOf("A B", "C"), arrayOf("A", "B", "E", "C")))
        assertEquals(4, longestCommonSubsequenceLenth(arrayOf("A", "B", "C", "D", "E", "F"), arrayOf("B", "D", "C", "A", "E", "F")))
        assertEquals(3, longestCommonSubsequenceLenth(arrayOf("B", "A", "N", "A", "N", "A"), arrayOf("A", "T", "A", "N")))
        assertEquals(0, longestCommonSubsequenceLenth(arrayOf("B", "A", "N", "A", "N", "A"), arrayOf()))
    }
}
