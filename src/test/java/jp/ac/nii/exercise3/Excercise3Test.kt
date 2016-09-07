package jp.ac.nii.exercise3

import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertThat

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths

import org.junit.Test

import jp.ac.nii.mapreduceframework.util.Util

class Excercise3Test {

    @Test
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    fun containsLengths() {
        Excercise3Main.main(arrayOf())
        val text = Util.readAll(Paths.get("exercise3.tsv").toFile())
        assertThat(text, containsString("あいさつ	11"))
        assertThat(text, containsString("義理	5"))
        assertThat(text, containsString("大いに	26"))
    }

}
