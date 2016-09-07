package jp.ac.nii.exercise2

import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertThat

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths

import org.junit.Test

import jp.ac.nii.mapreduceframework.util.Util

class Excercise2Test {

    @Test
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    fun containsLengths() {
        Excercise2Main.main(arrayOf())
        val text = Util.readAll(Paths.get("exercise2.tsv").toFile())
        assertThat(text, containsString("16	1"))
        assertThat(text, containsString("9	269"))
        assertThat(text, containsString("3	2742"))
    }

}
