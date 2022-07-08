package com.bohregard.markdown.extensions.spoiler

import org.commonmark.node.Nodes
import org.commonmark.node.SourceSpans
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

class SpoilerDelimiterProcessor: DelimiterProcessor {

    override fun getOpeningCharacter(): Char {
        return '%'
    }

    override fun getClosingCharacter(): Char {
        return '%'
    }

    override fun getMinLength(): Int {
        return 2
    }

    override fun process(openingRun: DelimiterRun, closingRun: DelimiterRun): Int {
        return if (openingRun.length() == 2 && closingRun.length() == 2) {
            val opener = openingRun.opener

            val spoiler = Spoiler()

            val sourceSpans = SourceSpans.empty()
            sourceSpans.addAllFrom(openingRun.getOpeners(1))

            Nodes.between(opener, closingRun.closer).forEach {
                spoiler.appendChild(it)
                sourceSpans.addAll(it.sourceSpans)
            }

            sourceSpans.addAllFrom(closingRun.getClosers(1))

            spoiler.sourceSpans = sourceSpans.sourceSpans
            opener.insertAfter(spoiler)

            2
        } else {
            0
        }

    }
}