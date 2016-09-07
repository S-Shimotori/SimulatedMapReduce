package jp.ac.nii.exercise4

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Reducer

/**
 * TODO: このファイルは未完成です！
 */
class StandardDeviationCalculationReducer : Reducer<String, Int, String, Double>() {
    override fun reduce(key: String, values: Iterable<Int>, context: Context) {
        // TODO: 分散を計算しよう
        // ヒント1: context.getConfiguration().get() メソッドを使おう！
        // ヒント2: Excercise4Main.createConfiguration()メソッドをよく読もう！
        val average = context.configuration.get(key).toDouble()
        val variance = values.map { Math.pow(it - average, 2.toDouble()) }.sum() / values.toList().size
        context.write(key, Math.sqrt(variance))
    }
}
