package jp.ac.nii.exercise6

import java.nio.file.Paths

import jp.ac.nii.mapreduceframework.FileInputFormat
import jp.ac.nii.mapreduceframework.FileOutputFormat
import jp.ac.nii.mapreduceframework.Job
import jp.ac.nii.mapreduceframework.NullWritable

/**
 * 以下の式の分子（numerator）を計算するジョブのJobです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * このファイルは完成しています。
 */
object SpecPairAggregationJob {

    fun create(): Job<Long, String, String, Int, NullWritable, String> {
        val job = Job.getInstance<Long, String, String, Int, NullWritable, String>()

        job.setMapperClass(SpecPairAggregationMapper::class.java)
        job.setReducerClass(SpecPairAggregationReducer::class.java)

        job.setInputFormatClass(FileInputFormat::class.java)
        job.setOutputFormatClass(FileOutputFormat::class.java)
        FileInputFormat.addInputPath(job, Paths.get(FileNameConstants.GOODS_PAIR))
        FileOutputFormat.setOutputPath(job, Paths.get(FileNameConstants.NUMERATOR))

        job.setNumReduceTasks(10)

        return job
    }
}
