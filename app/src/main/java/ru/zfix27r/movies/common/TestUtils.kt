package ru.zfix27r.movies.common

import android.util.JsonReader
import android.util.JsonToken
import java.io.InputStreamReader

class TestUtils {

    private fun printDataAsString(inputStreamReader: InputStreamReader) {
        var result = ""
        var data = inputStreamReader.read()
        while (data != -1) {
            val current = data.toChar()
            result += current
            data = inputStreamReader.read()
        }
        println(result)
    }

    private fun printDataAsJsonSteps(inputStreamReader: InputStreamReader) {
        val jsonReader = JsonReader(inputStreamReader)
        while (jsonReader.hasNext()) {
            when (jsonReader.peek()) {
                JsonToken.BEGIN_OBJECT -> {
                    println("BEGIN_OBJECT")
                    jsonReader.beginObject()
                }
                JsonToken.BEGIN_ARRAY -> {
                    println("BEGIN_ARRAY")
                    jsonReader.beginArray()
                }
                JsonToken.END_OBJECT -> {
                    println("END_OBJECT")
                    jsonReader.endObject()
                }
                JsonToken.END_ARRAY -> {
                    println("END_ARRAY")
                    jsonReader.endArray()
                }
                JsonToken.NULL -> {
                    println("NULL")
                    jsonReader.nextNull()
                }
                JsonToken.NAME -> {
                    println("Name: " + jsonReader.nextName())
                }
                JsonToken.STRING -> {
                    println("String: " + jsonReader.nextString())
                }
                JsonToken.NUMBER -> {
                    println("Int: " + jsonReader.nextInt())
                }
                else -> {
                    jsonReader.skipValue()
                }
            }
        }

        if (jsonReader.peek() == JsonToken.END_OBJECT) println("sdfsdfsdfsdfsdfsdfs")
        if (jsonReader.peek() == JsonToken.END_ARRAY) println("sdfsdfsdfsdfs97897070dfsdfs")
        if (jsonReader.peek() == JsonToken.END_DOCUMENT) println("sdfsdfsdfsdfsdfsdf34234sdfs")

        jsonReader.close()
    }
}