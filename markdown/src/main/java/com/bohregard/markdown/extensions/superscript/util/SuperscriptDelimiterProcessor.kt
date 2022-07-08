package com.bohregard.markdown.extensions.superscript.util

import org.commonmark.node.Nodes
import org.commonmark.node.SourceSpans
import org.commonmark.parser.delimiter.DelimiterProcessor
import org.commonmark.parser.delimiter.DelimiterRun

internal class SuperscriptDelimiterProcessor : DelimiterProcessor {

    override fun getOpeningCharacter(): Char {
        return '^'
    }

    override fun getClosingCharacter(): Char {
        return ' '
    }

    override fun getMinLength(): Int {
        return 1
    }

    override fun process(openingRun: DelimiterRun, closingRun: DelimiterRun): Int {
        return if (openingRun.length() >= 1 && closingRun.length() >= 1) {
            val opener = openingRun.opener

            val superscript = Superscript()

            val sourceSpans = SourceSpans.empty()
            sourceSpans.addAllFrom(openingRun.getOpeners(1))

            Nodes.between(opener, closingRun.closer).forEach {
                superscript.appendChild(it)
                sourceSpans.addAll(it.sourceSpans)
            }

            sourceSpans.addAllFrom(closingRun.getClosers(1))

            superscript.sourceSpans = sourceSpans.sourceSpans
            opener.insertAfter(superscript)

            1
        } else {
            0
        }

    }
}