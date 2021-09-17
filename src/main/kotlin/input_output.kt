import java.io.File

// Возвращает содержимое файла filename, разделённое на строки
fun readFileLines(filename: String): Array<String> {
    fun readList(filename: String): List<String> {
        return File(filename).readLines()
    }
    val list: List<String> = readList(filename)
    val answer = Array(list.size){""}
    for (it in answer.indices)
        answer[it] = list[it]

    return answer
}

// Записывает блоки удалённых и добавленных строк с их заголовками в файл filename
fun writeFileLines(filename: String, lines: List<DiffLineBlock>) {
    fun writeDiffLineBlock(filename: String, block: DiffLineBlock) {
        val file = File(filename)
        // Вывод заголовка блока
        val ind = if (block.first != block.last) "${block.first}-${block.last}" else "${block.first}"
        if (block.add)
            file.appendText("\n@ a $ind @")
        else
            file.appendText("\n@ d $ind @")
        // Вывод содержимого блока
        for (str in block.strings)
            file.appendText("\n> $str")
    }
    val file = File(filename)
    // Проверка на существование файла
    // Если такого файла нет, то создаётся новый
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText("----------------------------SUCCESS------------------------------------------------------------------------------------")
    for (block in lines)
        writeDiffLineBlock(filename, block)
}