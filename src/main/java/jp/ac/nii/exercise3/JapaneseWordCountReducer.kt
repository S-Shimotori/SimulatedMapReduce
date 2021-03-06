package jp.ac.nii.exercise3

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Reducer

/**
 * Created by S-Shimotori on 9/8/16.
 */

class JapaneseWordCountReducer : Reducer<String, Int, String, Int>() {
    override fun reduce(key: String?, values: MutableIterable<Int>, context: Context) {
        context.write(key, values.sum())
    }
}