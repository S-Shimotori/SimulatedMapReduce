package jp.ac.nii.exercise6

import jp.ac.nii.mapreduceframework.Context
import jp.ac.nii.mapreduceframework.Mapper

/**
 * 以下の式の関連度を計算するジョブのMapperです。
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * このファイルは完成しています。
 */
class RelativityCaclulationMapper : Mapper<Long, String, String, String>() {

    private var writer: Writer? = null

    public override fun setup(context: Context) {
        val filePath = context.path

        if (filePath.contains(FileNameConstants.DENOMINATION)) {
            writer = DenominationWriter()
        } else if (filePath.contains(FileNameConstants.NUMERATOR)) {
            writer = NumeratorWriter()
        } else {
            throw RuntimeException("Invalid Input File : " + filePath)
        }
    }

    public override fun map(key: Long?, value: String, context: Context) {
        writer!!.write(key, value, context)
    }

    private interface Writer {
        fun write(key: Long?, valueI: String, context: Context)
    }

    private inner class DenominationWriter : Writer {
        override fun write(key: Long?, value: String, context: Context) {
            val goodsNameAndNum = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            // 分母と分子を区別するためにキーの末尾に"#d"を追加
            context.write(goodsNameAndNum[0] + "#d", goodsNameAndNum[1])
        }
    }

    private inner class NumeratorWriter : Writer {
        override fun write(key: Long?, value: String, context: Context) {
            // SpecPairAggregationMapper によって反転するペア（「商品X,商品Y」と「商品Y,商品X」）をひとまとめにして購入回数を計算したが、
            // 関連度を計算するときは「商品X」からも「商品Y」からも関連度を計算したいので、両方のペアで扱えるように分子データを複製する
            val goodsPairAndNum = value.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            context.write(goodsPairAndNum[0], goodsPairAndNum[1] + "," + goodsPairAndNum[2])
            context.write(goodsPairAndNum[1], goodsPairAndNum[0] + "," + goodsPairAndNum[2])
        }
    }
}
