package com.bohregard.example.ui.markdown.util

import android.content.Context
import com.bohregard.example.R
import java.io.BufferedReader
import java.io.InputStreamReader

object MarkdownUtils {
    fun readFile(context: Context): String {
        val inputStream = context.resources.openRawResource(R.raw.markdown)

        val sb = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).lines().forEach {
            sb.append(it).append("\n")
        }
        return sb.toString()
    }
}