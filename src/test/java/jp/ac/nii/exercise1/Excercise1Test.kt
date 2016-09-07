package jp.ac.nii.exercise1

import org.hamcrest.CoreMatchers.containsString
import org.junit.Assert.assertThat

import java.io.FileNotFoundException
import java.io.UnsupportedEncodingException
import java.nio.file.Paths

import org.junit.Test

import jp.ac.nii.mapreduceframework.util.Util

class Excercise1Test {

    @Test
    @Throws(FileNotFoundException::class, InstantiationException::class, IllegalAccessException::class, UnsupportedEncodingException::class)
    fun containsWords() {
        Excercise1Main.main(arrayOf())
        val text = Util.readAll(Paths.get("exercise1.tsv").toFile())
        assertThat(text, containsString("the	793"))
        assertThat(text, containsString("alice	103"))
        assertThat(text, containsString("apply	1"))
    }

}
