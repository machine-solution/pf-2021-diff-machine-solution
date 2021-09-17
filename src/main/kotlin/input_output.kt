import java.io.File

fun readFileLines(filename: String): Array<String> {
    fun readList(filename: String): List<String> {
        return File(filename).readLines()
    }
    val list: List<String> =readList(filename)
    val answer = Array(list.size){""}
    for (it in answer.indices)
        answer[it] = list[it]

    return answer
}

fun writeFileLines(filename: String, lines: List<DiffLineBlock>) {
    fun writeDiffLineBlock(filename: String, block: DiffLineBlock) {
        val file = File(filename)
        val ind = if (block.first != block.last) "${block.first}-${block.last}" else "${block.first}"
        if (block.add)
            file.appendText("\n@ a $ind @")
        else
            file.appendText("\n@ d $ind @")
        for (str in block.strings)
            file.appendText("\n> $str")
    }
    val file = File(filename)
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText("----------------------------SUCCESS------------------------------------------------------------------------------------")
    for (block in lines)
        writeDiffLineBlock(filename, block)
}