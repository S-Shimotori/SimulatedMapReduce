package jp.ac.nii.exercise3

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
object Excercise3Main {
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    @JvmStatic fun main(args: Array<String>) {

        // TODO: 課題3： bocchan.txt を対象として、日本語版のワードカウントを作ってください。
        // また、jp.ac.nii.exercise3.Exercise3Test のテストが通ることを確認して下さい。

        // 以下は日本語の文章から単語を抽出する形態素解析のサンプルコードです。
        for (word in Tokenizer.tokenize("寿司を食べたい。")) {
            println(word)
        }

        val job = Job.getInstance<Long, String, String, Int, String, Int>()

        job.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(job, Paths.get("bocchan.txt"))
        job.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(job, Paths.get("exercise3"))
        job.setMapperClass(JapaneseWordCountMapper::class.java)
        job.setReducerClass(JapaneseWordCountReducer::class.java)
        job.setNumReduceTasks(10)

        job.waitForCompletion()

        // exercise3/outputディレクトリの中身をexercise3.tsvにマージします。
        Util.merge(Paths.get("exercise3", "output").toFile(), Paths.get("exercise3.tsv").toFile())
    }
}