package com.bohregard.example.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bohregard.markdown.MarkdownText
import com.bohregard.markdown.model.MarkdownConfiguration

@Composable
fun MarkdownUi() {
    val markdown = """
        # Header 1
        ## Header 2
        ### Header 3
        #### Header 4
        ##### Header 5
        ###### Header 6
        
        # Thematic Break
        ---

        # Bullet Lists
        * Item 1
        * Item 2
        * Lorem **_ipsum_** [dolor][1] sit amet, *consectetur adipiscing elit.* **Nulla vitae tempus nibh.** Pellentesque congue massa purus, a pretium urna dapibus quis.

        # Ordered Lists

        1. Item 1
        2. Item 2
        3. Item 3

        # Ordered Lists

        4. Item 4
        5. Item 5
        6. Item 6
        
        # Paragraph

        Lorem **_ipsum_** [dolor][1] sit amet, *consectetur adipiscing elit.* **Nulla vitae tempus nibh.** Pellentesque congue massa purus, a pretium urna dapibus quis.
        
        [1]: <https://en.wikipedia.org/wiki/Hobbit#Lifestyle> "Hobbit lifestyles"
        
        # Links
        
        Links: [Duck Duck Go](https://duckduckgo.com).
        
        Tooltip Link: [Duck Duck Go](https://duckduckgo.com "The best search engine for privacy")
        
        <https://www.markdownguide.org>
        
        <fake@example.com>
        
        # Images
        
        ![Place Kitten](http://placekitten.com/200/300)
        
        # Code

        Inline code in a paragraph `inline`.

        ```
        import x.y.z
        
        private fun VariablesAreNasty() {
            Doing the thing
        }
        ```

        # Block Quotes
        
        > Sentence One **BOLD One**
        >> Sentence Two _**Bold Italic?**_
        >
        >
        > Maecenas maximus euismod nisl vehicula rhoncus.
        > **Vivamus accumsan** neque ac ex laoreet lobortis.
        >
        > Test
        >> Maecenas maximus euismod nisl vehicula rhoncus.
        >> **Vivamus accumsan** neque ac ex laoreet lobortis.
        >
        > Nunc in posuere eros, ut fermentum turpis.
    """.trimIndent()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        MarkdownText(
            markdown = markdown,
            configuration = MarkdownConfiguration(
                onClickEvent = {}
            ),
            textStyle = MaterialTheme.typography.bodyMedium
                .toSpanStyle()
        )
        Spacer(modifier = Modifier.size(100.dp))
    }
}