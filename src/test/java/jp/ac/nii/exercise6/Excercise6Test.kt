package jp.ac.nii.exercise6

import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertThat

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.net.URISyntaxException
import java.nio.file.Paths

import org.junit.Test

import jp.ac.nii.mapreduceframework.util.Util

class Excercise6Test {

    @Test
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class, URISyntaxException::class)
    fun containsResults() {
        Excercise6Main.main(arrayOf())
        val text = Util.readAll(Paths.get("exercise6.csv").toFile())
        assertThat(text, containsString("生シュークリーム,あんドーナツ,0.0287"))
        assertThat(text, containsString("あんドーナツ,生シュークリーム,0.0294"))
        assertThat(text, containsString("ところてん,ミルクチョコレート,0.0367"))
    }

}
