package jp.ac.nii.exercise2

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * Created by S-Shimotori on 9/7/16.
 */

class WordLengthCountMapper: Mapper<Long, String, Int, Int>() {
    override fun map(key: Long?, line: String, context: Context) {
        val words = line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (word in words) {
            if (isWord(word)) {
                context.write(word.length, 1)
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