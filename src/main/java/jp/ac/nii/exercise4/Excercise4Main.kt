package jp.ac.nii.exercise4

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths
import java.util.Scanner

import jp.ac.nii.mapreduceframework.Configuration
import jp.ac.nii.mapreduceframework.FileInputFormat
import jp.ac.nii.mapreduceframework.FileOutputFormat
import jp.ac.nii.mapreduceframework.Job
import jp.ac.nii.mapreduceframework.util.Util

/**
 * このファイルは完成しています。
 */
object Excercise4Main {
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    @JvmStatic fun main(args: Array<String>) {

        // TODO: 課題4： 科目名と点数のペアが記載されている score.csv を対象として、各科目の標準偏差を計算してください。
        // また、jp.ac.nii.exercise4.Exercise4Test のテストが通ることを確認して下さい。

        // この課題では、設定情報を受け渡す仕組み（Configurationオブジェクト）を使って、
        // 標準偏差を計算するジョブに、平均値の計算ジョブが出力する計算結果を渡します。

        // 参考資料: Googleで「Hadoop 標準偏差」などのキーワードで検索すると良いでしょう。
        // 注意: このファイルは完成しています。

        val averageJob = createAverageJob()
        averageJob.waitForCompletion()

        // exercise4_average/outputディレクトリの中身をexercise4_average.tsvにマージします。
        Util.merge(Paths.get("exercise4_average", "output").toFile(), Paths.get("exercise4_average.tsv").toFile())

        val sdJob = createStandardDeviationJob()
        sdJob.waitForCompletion()

        // exercise4/outputディレクトリの中身をexercise4.tsvにマージします。
        Util.merge(Paths.get("exercise4", "output").toFile(), Paths.get("exercise4.tsv").toFile())
    }

    private fun createAverageJob(): Job<Long, String, String, Int, String, Double> {
        val averageJob = Job.getInstance<Long, String, String, Int, String, Double>()

        averageJob.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(averageJob, Paths.get("score.csv"))
        averageJob.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(averageJob, Paths.get("exercise4_average"))

        averageJob.setMapperClass(AverageCalculationMapper::class.java)
        averageJob.setReducerClass(AverageCalculationReducer::class.java)

        averageJob.setNumReduceTasks(10)
        return averageJob
    }

    @Throws(FileNotFoundException::class)
    private fun createStandardDeviationJob(): Job<Long, String, String, Int, String, Double> {
        // 平均値の計算結果を格納しているConfigurationオブジェクトを取得
        val conf = createConfiguration()
        // 標準偏差を計算するジョブにConfigurationオブジェクトを渡す
        val sdJob = Job.getInstance<Long, String, String, Int, String, Double>(conf)

        sdJob.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(sdJob, Paths.get("score.csv"))
        sdJob.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(sdJob, Paths.get("exercise4"))

        sdJob.setMapperClass(StandardDeviationCalculationMapper::class.java)
        sdJob.setReducerClass(StandardDeviationCalculationReducer::class.java)

        sdJob.setNumReduceTasks(10)
        return sdJob
    }

    /**
     * 平均値の計算結果を読み取って、計算結果を設定したConfigurationオブジェクトを返します。

     * @return 計算結果を設定したConfigurationオブジェクト
     * *
     * @throws FileNotFoundException
     */
    @Throws(FileNotFoundException::class)
    private fun createConfiguration(): Configuration {
        val conf = Configuration()
        // 平均値の計算結果を読み取る
        val intput = FileInputStream("exercise4_average.tsv")
        val scanner = Scanner(intput, "UTF-8")
        while (scanner.hasNextLine()) {
            val keyValue = scanner.nextLine().split("\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            // 平均値データの設定
            conf.set(keyValue[0], keyValue[1])
        }
        scanner.close()
        return conf
    }
}