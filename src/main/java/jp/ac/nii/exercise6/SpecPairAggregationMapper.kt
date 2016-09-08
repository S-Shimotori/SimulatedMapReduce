package jp.ac.nii.exercise6

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * 以下の式の分子（numerator）を計算するジョブのMapperです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 */
class SpecPairAggregationMapper : Mapper<Long, String, String, Int>() {
    public override fun map(key: Long?, value: String, context: Context) {
        // TODO: 商品ペアの名前を昇順でソートした後、キーを「商品X,商品Y」という文字列、バリューを1にしてペアの頻度を計算するMapperを作ろう

        // 商品ペアの名前を昇順でソートすることで、例えば、「あんドーナツ,生シュークリーム」と「生シュークリーム,あんドーナツ」など、
        // 実質は同じだが順序が異なるペアを「あんドーナツ,生シュークリーム」という一つのペアに集約することができる
        context.write(value.split(",").sorted().joinToString(separator = ","), 1)
    }
}
