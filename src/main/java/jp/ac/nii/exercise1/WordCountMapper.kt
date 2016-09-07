package jp.ac.nii.exercise1

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * このファイルは完成しています。
 */
class WordCountMapper : Mapper<Long, String, String, Int>() {

    override fun map(key: Long?, line: String, context: Context) {
        val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (word in words) {
            if (isWord(word)) {
                context.write(word.toLowerCase(), 1)
            }
        }
    }

    private fun isWord(word: String): Boolean {
        for (i in 0..word.length - 1) {
            if (!Character.isAlphabetic(word[i].toInt())) {
                return false
            }
        }
        return word.length > 0
    }

}
