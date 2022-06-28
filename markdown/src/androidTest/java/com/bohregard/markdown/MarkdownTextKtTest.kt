package com.bohregard.markdown

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import org.junit.Rule
import org.junit.Test


class MarkdownTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testParagraph() {
        composeTestRule.setContent {
            MarkdownText(markdown = "**Emphasis**")
        }
        composeTestRule.onRoot().printToLog("TAG")

        composeTestRule.onNodeWithText("Emphasis")
            .assertIsDisplayed()
    }
}