package jp.ac.nii.exercise1

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths

import jp.ac.nii.mapreduceframework.FileInputFormat
import jp.ac.nii.mapreduceframework.FileOutputFormat
import jp.ac.nii.mapreduceframework.Job
import jp.ac.nii.mapreduceframework.util.Util

/**
 * このファイルは完成しています。
 */
object Excercise1Main {
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    @JvmStatic fun main(args: Array<String>) {

        // TODO: 課題1: このファイルを実行してexercise1/outputディレクトリと、exercise1.tsvファイルが生成されることを確認してください。
        // また、jp.ac.nii.exercise1.Exercise1Test のテストを実行して、テストが通ることを確認して下さい。

        val job = Job.getInstance<Long, String, String, Int, String, Int>()

        job.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(job, Paths.get("alice.txt"))
        job.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(job, Paths.get("exercise1"))
        job.setMapperClass(WordCountMapper::class.java)
        job.setReducerClass(WordCountReducer::class.java)
        job.setNumReduceTasks(10)

        job.waitForCompletion()

        // exercise1/outputディレクトリの中身をexercise1.tsvにマージします。
        Util.merge(Paths.get("exercise1", "output").toFile(), Paths.get("exercise1.tsv").toFile())
    }
}