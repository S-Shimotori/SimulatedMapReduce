package jp.ac.nii.exercise5

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Scanner

import com.google.common.collect.Maps

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Reducer

/**
 * TODO: このファイルは未完成です！
 */
class StandardDeviationCalculationReducer : Reducer<String, Int, String, Double>() {
    private val subject2Average = Maps.newHashMap<String, Double>()

    override fun setup(context: Context) {
        // 注意：以下は分散キャッシュ（DistributedCache）から平均値の計算結果の読み込みを行なっているが、本家Hadoopとは処理が大きく異なります！
        try {
            val intput = FileInputStream("exercise5_average.tsv")
            val scanner = Scanner(intput, "UTF-8")
            while (scanner.hasNextLine()) {
                // TODO: subject2Averageに科目名と平均値のキーバリューを保存しよう
                val (subject, score) = scanner.nextLine().split("\t")
                subject2Average.put(subject, score.toDouble())
            }
            scanner.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun reduce(key: String, values: Iterable<Int>, context: Context) {
        // TODO: 分散を計算しよう
        // ヒント: subject2Average フィールドを使おう！
        val average = subject2Average[key]!!
        val variance = values.map { Math.pow(it - average, 2.toDouble()) }.sum() / values.toList().size
        context.write(key, Math.sqrt(variance))
    }
}
