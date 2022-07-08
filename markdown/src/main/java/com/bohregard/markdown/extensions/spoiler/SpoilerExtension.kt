package com.bohregard.markdown.extensions.spoiler

import org.commonmark.Extension
import org.commonmark.parser.Parser

class SpoilerExtension: Parser.ParserExtension {

    companion object {
        fun create(): Extension {
            return SpoilerExtension()
        }
    }

    override fun extend(parserBuilder: Parser.Builder) {
        parserBuilder.customDelimiterProcessor(SpoilerDelimiterProcessor())
    }
}