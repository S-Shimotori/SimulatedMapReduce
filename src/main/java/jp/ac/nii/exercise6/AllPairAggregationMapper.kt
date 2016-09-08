package jp.ac.nii.exercise6

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * 以下の式の分母（denominator）を計算するジョブのMapperです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * TODO: このファイルは未完成です！
 */
class AllPairAggregationMapper : Mapper<Long, String, String, Int>() {
    public override fun map(key: Long?, value: String, context: Context) {
        // TODO: 同時に購入された商品ペアデータから、各商品の購入回数を計算するためのMapperを作ろう
        // 入力はペアデータなので、1レコードから2種類の商品を1回ずつ購入したという情報が得られる
        value.split(",").forEach { context.write(it, 1) }
    }
}
