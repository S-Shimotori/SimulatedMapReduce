package jp.ac.nii.exercise5

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.Paths

import jp.ac.nii.mapreduceframework.FileInputFormat
import jp.ac.nii.mapreduceframework.FileOutputFormat
import jp.ac.nii.mapreduceframework.Job
import jp.ac.nii.mapreduceframework.util.Util

/**
 * このファイルは完成しています。
 */
object Excercise5Main {
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class, URISyntaxException::class)
    @JvmStatic fun main(args: Array<String>) {

        // TODO: 課題5： 科目名と点数のペアが記載されている score.csv を対象として、各科目の標準偏差を計算してください。
        // また、jp.ac.nii.exercise5.Exercise5Test のテストが通ることを確認して下さい。

        // この課題では、分散キャッシュの仕組みを使って、標準偏差を計算するジョブから、
        // 平均値の計算結果を格納しているファイル"exercise5_average.tsv"を読み込めるようにします。

        // 参考資料: Googleで「Hadoop 分散キャッシュ」などのキーワードで検索すると理解が深まります。（課題には不要だと思いますが。）

        val averageJob = createAverageJob()
        averageJob.waitForCompletion()

        // exercise5_average/outputディレクトリの中身をexercise5_average.tsvにマージします。
        Util.merge(Paths.get("exercise5_average", "output").toFile(), Paths.get("exercise5_average.tsv").toFile())

        val sdJob = createStandardDeviationJob()
        sdJob.waitForCompletion()

        // exercise5/outputディレクトリの中身をexercise5.tsvにマージします。
        Util.merge(Paths.get("exercise5", "output").toFile(), Paths.get("exercise5.tsv").toFile())
    }

    private fun createAverageJob(): Job<Long, String, String, Int, String, Double> {
        val averageJob = Job.getInstance<Long, String, String, Int, String, Double>()

        averageJob.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(averageJob, Paths.get("score.csv"))
        averageJob.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(averageJob, Paths.get("exercise5_average"))

        averageJob.setMapperClass(AverageCalculationMapper::class.java)
        averageJob.setReducerClass(AverageCalculationReducer::class.java)

        averageJob.setNumReduceTasks(10)
        return averageJob
    }

    @Throws(URISyntaxException::class)
    private fun createStandardDeviationJob(): Job<Long, String, String, Int, String, Double> {
        val sdJob = Job.getInstance<Long, String, String, Int, String, Double>()
        // 全Mapper / Reducer からファイルを参照できるように設定します。
        sdJob.addCacheFile(URI("exercise5_average.tsv"))

        sdJob.setInputFormatClass(FileInputFormat::class.java)
        FileInputFormat.addInputPath(sdJob, Paths.get("score.csv"))
        sdJob.setOutputFormatClass(FileOutputFormat::class.java)
        FileOutputFormat.setOutputPath(sdJob, Paths.get("exercise5"))

        sdJob.setMapperClass(StandardDeviationCalculationMapper::class.java)
        sdJob.setReducerClass(StandardDeviationCalculationReducer::class.java)

        sdJob.setNumReduceTasks(10)
        return sdJob
    }
}