package jp.ac.nii.exercise6

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.NullWritable
import jp.ac.nii.mapreduceframework.Reducer

/**
 * 以下の式の関連度を計算するジョブのReducerです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * TODO: このファイルは未完成です！
 */
class RelativityCalculationReducer : Reducer<String, String, NullWritable, String>() {

    public override fun reduce(key: String, values: Iterable<String>, context: Context) {
        // ヒント1: 正しく他のファイルが書けていれば、valuesの先頭は分母データで、2個目以降は分子データになる
        // ヒント2: 正しく他のファイルが書けていれば、keyは「あんドーナツ#d」というように、末尾に#dが付いている
        val goodsName = key.replace(Regex("#d(?=$)"), "") /* TODO: 商品Xの名前を設定 */
        val iterator = values.iterator()

        // 一番最初のvalueが分母になるようにソート済み（RelativityCalculationJob）
        val denominator = Integer.parseInt(iterator.next())

        while (iterator.hasNext()) {
            val (yKey, yValue) = iterator.next().split(",")
            val pairGoodsName = yKey /* TODO: 関連度を計算する商品Yの名前を設定 */
            val relativity = yValue.toDouble() / denominator /* TODO: 関連度を計算 */

            // 関連度が低すぎる（0.025以下）ペアは関連していないとみなしてフィルタリング
            if (relativity > 0.025) {
                // キーにNullWritableを使うことで、「キー[タブ]バリュー」という出力の代わりに、「バリュー」という出力を実現する
                context.write(nullWritable, "$goodsName,$pairGoodsName,$relativity")
            }
        }
    }

    companion object {

        private val nullWritable = NullWritable.get()
    }
}
