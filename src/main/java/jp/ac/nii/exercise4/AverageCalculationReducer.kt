package jp.ac.nii.exercise4

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Reducer

/**
 * TODO: このファイルは未完成です！
 */
class AverageCalculationReducer : Reducer<String, Int, String, Double>() {
    override fun reduce(key: String, values: Iterable<Int>, context: Context) {
        // TODO: 平均値を計算しよう
        context.write(key, values.sum().toDouble() / values.toList().size)
    }
}
