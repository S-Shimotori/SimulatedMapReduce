package jp.ac.nii.exercise6

import java.nio.file.Paths
import java.util.Comparator

import jp.ac.nii.mapreduceframework.FileInputFormat
import jp.ac.nii.mapreduceframework.FileOutputFormat
import jp.ac.nii.mapreduceframework.HashPartitioner
import jp.ac.nii.mapreduceframework.Job
import jp.ac.nii.mapreduceframework.NullWritable

/**
 * 以下の式の関連度を計算するジョブのJobです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * TODO: このファイルは未完成です！
 */
object RelativityCalculationJob {

    /**
     * このメソッドは完成しています。
     */
    fun create(): Job<Long, String, String, String, NullWritable, String> {
        val job = Job.getInstance<Long, String, String, String, NullWritable, String>()

        job.setMapperClass(RelativityCaclulationMapper::class.java)
        job.setReducerClass(RelativityCalculationReducer::class.java)

        job.setInputFormatClass(FileInputFormat::class.java)
        job.setOutputFormatClass(FileOutputFormat::class.java)
        FileInputFormat.addInputPath(job, Paths.get(FileNameConstants.DENOMINATION))
        FileInputFormat.addInputPath(job, Paths.get(FileNameConstants.NUMERATOR))
        FileOutputFormat.setOutputPath(job, Paths.get(FileNameConstants.RELATED_GOODS))

        // TODO: ずっと下にある RelativityCalculationSortComparator, RelativityCalculationPartitioner, 
        // RelativityCalculationGroupComparator クラスを修正しよう！

        // ヒント: RelativityCaclulationMapperクラスをよく読もう

        // キーの並び順をどうするか（Reduceタスクの割り当て前のキーのソート処理の制御）
        job.setSortComparatorClass(RelativityCalculationSortComparator::class.java)
        // どのReduceタスクでキー（と対応するバリュー）を処理するか（Reduceタスクの割り当て制御）
        job.setPartitionerClass(RelativityCalculationPartitioner::class.java)
        // どのキーとどのキーを同一とみなして、Reducerのバリューリストに集約するか（Reduceの処理単位の制御）
        job.setGroupingComparatorClass(RelativityCalculationGroupComparator::class.java)

        job.setNumReduceTasks(10)

        return job
    }

    fun removeSharpD(key: String): String {
        val keyStr = key.toString()
        if (keyStr.endsWith("#d")) {
            return keyStr.substring(0, keyStr.length - 2)
        }
        return key
    }

    /**
     * 以下のように分母データと分子データが入り乱れているので、
     * , ところてん,1200(注：分子データ)>, #d, 5400(注：分母データ)>, , 生シュークリーム,2000(注：分子データ)>
     * 「あんドーナツ」と「あんドーナツ#d」が同じキーと見なして、同じReduceタスクで処理されるようにハッシュ計算処理を制御する。
     */
    class RelativityCalculationPartitioner : HashPartitioner<String, String>() {
        override fun getPartition(key: String, value: String, numReduceTasks: Int): Int {
            // TODO: removeSharp()とsuper.getPartition()メソッドを活用しよう
            //return 0    // 注意: return 0; は誤りです
            return super.getPartition(removeSharpD(key), value, numReduceTasks)
        }
    }

    /**
     * 以下のように分母データと分子データが入り乱れているので、
     * , ところてん,1200(注：分子データ)>, #d, 5400(注：分母データ)>, , 生シュークリーム,2000(注：分子データ)>
     * 「あんドーナツ」と「あんドーナツ#d」を同じキーと見なして、バリューリストにまとめられるように比較処理を制御する。
     */
    class RelativityCalculationGroupComparator : Comparator<String> {
        override fun compare(a: String, b: String): Int {
            // TODO: removeSharp()とString.compareTo()メソッドを活用しよう
            //return 0    // 注意: return 0; は誤りです
            return removeSharpD(a).compareTo(removeSharpD(b))
        }
    }

    /**
     * 以下のように分母データと分子データが入り乱れているので、
     * , ところてん,1200(注：分子データ)>, #d, 5400(注：分母データ)>, , 生シュークリーム,2000(注：分子データ)>
     * キーに対するソート時の比較処理を制御することで、以下のように分母データが先頭に来るようにする。
     * #d, 5400(注：分母データ)>, , ところてん,1200(注：分子データ)>, , 生シュークリーム,2000(注：分子データ)>
     */
    class RelativityCalculationSortComparator : Comparator<String> {
        override fun compare(a: String, b: String): Int {
            // TODO: String.compareTo()メソッドを活用しよう
            //return 0    // 注意: return 0; は誤りです
            val regex = Regex("#d$")
            return if (a.contains(regex)) {
                -1
            } else if (b.contains(regex)) {
                1
            } else {
                a.compareTo(b)
            }
        }
    }
}
