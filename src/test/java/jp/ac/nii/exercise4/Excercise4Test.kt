package jp.ac.nii.exercise4

import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertThat

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths

import org.junit.Test

import jp.ac.nii.mapreduceframework.util.Util

class Excercise4Test {

    @Test
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    fun containsResults() {
        Excercise4Main.main(arrayOf())
        val text = Util.readAll(Paths.get("exercise4.tsv").toFile())
        assertThat(text, containsString("体育	14.85"))
        assertThat(text, containsString("生活	15.08"))
        assertThat(text, containsString("社会	14.71"))
    }

}
