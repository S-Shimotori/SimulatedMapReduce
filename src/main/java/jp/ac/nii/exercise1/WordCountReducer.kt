package jp.ac.nii.exercise1

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Reducer

/**
 * このファイルは完成しています。
 */
class WordCountReducer : Reducer<String, Int, String, Int>() {
    override fun reduce(key: String, values: Iterable<Int>, context: Context) {
        var sum = 0
        for (value in values) {
            sum += value
        }
        context.write(key, sum)
    }
}
