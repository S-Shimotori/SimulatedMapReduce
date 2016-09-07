package jp.ac.nii.exercise3

import java.io.IOException
import java.io.StringReader
import java.util.ArrayList

import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.core.LowerCaseFilter
import org.apache.lucene.analysis.core.StopFilter
import org.apache.lucene.analysis.ja.JapaneseAnalyzer
import org.apache.lucene.analysis.ja.JapaneseBaseFormFilter
import org.apache.lucene.analysis.ja.JapaneseKatakanaStemFilter
import org.apache.lucene.analysis.ja.JapanesePartOfSpeechStopFilter
import org.apache.lucene.analysis.ja.JapaneseTokenizer
import org.apache.lucene.analysis.miscellaneous.LengthFilter
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute

/**
 * 日本語の文章から単語を抽出するためのクラスです。
 * このファイルは完成しています。
 */
object Tokenizer {
    fun tokenize(japaneseText: String): List<String> {
        val tokenizer = JapaneseTokenizer(null, true,
                JapaneseTokenizer.Mode.NORMAL)
        var stream: TokenStream = tokenizer

        // 参考サイト
        // http://www.mwsoft.jp/programming/hadoop/mapreduce_with_lucene_filter.html

        // 小文字に統一
        stream = LowerCaseFilter(stream)
        // 「こと」「これ」「できる」などの頻出単語を除外
        stream = StopFilter(stream, JapaneseAnalyzer.getDefaultStopSet())
        // 16文字以上の単語は除外(あまり長い文字列はいらないよね)
        stream = LengthFilter(stream, 1, 16)
        // 動詞の活用を揃える(疲れた => 疲れる)
        stream = JapaneseBaseFormFilter(stream)
        // 助詞、助動詞、接続詞などを除外する
        stream = JapanesePartOfSpeechStopFilter(stream,
                JapaneseAnalyzer.getDefaultStopTags())
        // カタカナ長音の表記揺れを吸収
        stream = JapaneseKatakanaStemFilter(stream)

        val result = ArrayList<String>()

        try {
            tokenizer.setReader(StringReader(japaneseText))
            stream.reset()
            while (stream.incrementToken()) {
                val term = stream.getAttribute(CharTermAttribute::class.java)
                result.add(term.toString())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                tokenizer.close()
            } catch (e: IOException) {
            }

        }

        return result
    }

}
