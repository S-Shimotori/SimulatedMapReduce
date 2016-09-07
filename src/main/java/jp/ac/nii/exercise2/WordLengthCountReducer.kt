package jp.ac.nii.exercise2

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Reducer

/**
 * Created by S-Shimotori on 9/7/16.
 */

class WordLengthCountReducer: Reducer<Int, Int, Int, Int>() {
    override fun reduce(key: Int, values: MutableIterable<Int>, context: Context) {
        var sum = 0
        for (value in values) {
            sum += value
        }
        context.write(key, sum)
    }
}