package jp.ac.nii.exercise3

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * Created by S-Shimotori on 9/8/16.
 */

class JapaneseWordCountMapper : Mapper<Long, String, String, Int>() {
    override fun map(key: Long?, value: String, context: Context) {
        for (word in Tokenizer.tokenize(value)) {
            context.write(word, 1)
        }
    }
}