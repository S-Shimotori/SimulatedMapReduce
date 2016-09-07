package jp.ac.nii.exercise2

import jp.ac.nii.mapreduceframework.FileInputFormat
import jp.ac.nii.mapreduceframework.FileOutputFormat
import jp.ac.nii.mapreduceframework.Job
import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths

import jp.ac.nii.mapreduceframework.util.Util

/**
 * TODO: このファイルは未完成です！
 */
object Excercise2Main {
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    @JvmStatic fun main(args: Array<String>) {

        // TODO: 課題2： alice.txt を対象として、単語の長さの頻度を算出してください。
        // また、jp.ac.nii.exercise2.Exercise2Test のテストが通ることを確認して下さい。

        // 例： I am a cat. => 1文字が2回、2文字が1回、3文字が1回
        // 参考資料： 「01_Hadoopの概要.pdf」のp.30-35あたり

        val job = Job.getInstance<Long, String, Int, Int, Int, Int>()

        job.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(job, Paths.get("alice.txt"))
        job.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(job, Paths.get("exercise2"))
        job.setMapperClass(WordLengthCountMapper::class.java)
        job.setReducerClass(WordLengthCountReducer::class.java)
        job.setNumReduceTasks(10)

        job.waitForCompletion()

        // exercise2/outputディレクトリの中身をexercise2.tsvにマージします。
        Util.merge(Paths.get("exercise2", "output").toFile(), Paths.get("exercise2.tsv").toFile())
    }
}