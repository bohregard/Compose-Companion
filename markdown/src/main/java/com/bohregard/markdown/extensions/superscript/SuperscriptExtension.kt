package com.bohregard.markdown.extensions.superscript

import com.bohregard.markdown.extensions.superscript.util.SuperscriptDelimiterProcessor
import org.commonmark.Extension
import org.commonmark.parser.Parser

class SuperscriptExtension: Parser.ParserExtension {

    companion object {
        fun create(): Extension {
            return SuperscriptExtension()
        }
    }

    override fun extend(parserBuilder: Parser.Builder) {
        parserBuilder.customDelimiterProcessor(SuperscriptDelimiterProcessor())
    }
}