package jp.ac.nii.exercise6

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.NullWritable
import jp.ac.nii.mapreduceframework.Reducer

/**
 * 以下の式の分母（denominator）を計算するジョブのReducerです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * TODO: このファイルは未完成です！
 */
class AllPairAggregationReducer : Reducer<String, Int, NullWritable, String>() {

    public override fun reduce(key: String, values: Iterable<Int>, context: Context) {
        val sum = 0
        // TODO: ワードカウントと同じ要領でvaluesの合計を計算して、keyの商品の出現回数を計算しよう

        // キーにNullWritableを使うことで、「キー[タブ]バリュー」という出力の代わりに、「バリュー」という出力を実現する
        context.write(nullWritable, key + "," + values.toList().sum())
    }

    companion object {

        private val nullWritable = NullWritable.get()
    }
}
