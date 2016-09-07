package jp.ac.nii.exercise4

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * TODO: このファイルは未完成です！
 */
class AverageCalculationMapper : Mapper<Long, String, String, Int>() {
    override fun map(key: Long?, line: String, context: Context) {
        // TODO: 科目と点数のキーバリューに変換しよう
        val (subject, score) = line.split(",")
        context.write(subject, score.toInt())
    }
}
