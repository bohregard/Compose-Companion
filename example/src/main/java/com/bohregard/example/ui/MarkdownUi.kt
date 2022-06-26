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
import com.bohregard.shared.compose.markdown.MarkdownText

@Composable
fun MarkdownUi() {
    val markdown = """
        ### Thematic Break
        
        ---
        
        # Header 1
        ## Header 2
        ### Header 3
        #### Header 4
        ##### Header 5
        ###### Header 6
        ### Bullet Lists
        * Item 1
        * Item 2
        
        ### Ordered Lists
              
        1. Item 1
        2. Item 2
        3. Item 3
        
        ### Ordered Lists
        
        4. Item 4
        5. Item 5
        6. Item 6
        
        ### Paragraph

        Lorem **_ipsum_** dolor sit amet, *consectetur adipiscing elit.* **Nulla vitae tempus nibh.** Pellentesque congue massa purus, a pretium urna dapibus quis.
        
        ### Block Quotes
        
        > Maecenas maximus euismod nisl vehicula rhoncus.
        > **Vivamus accumsan** neque ac ex laoreet lobortis.
        >
        >> Maecenas maximus euismod nisl vehicula rhoncus.
        >> **Vivamus accumsan** neque ac ex laoreet lobortis.
        
        Nunc in posuere eros, ut fermentum turpis. Sed sit amet odio rhoncus dolor interdum blandit et sed leo. Quisque ac magna quis libero dictum hendrerit mollis in quam. Suspendisse et turpis odio. Aenean non ultricies urna. Donec euismod, arcu sit amet pretium auctor, dui lectus venenatis est, vel accumsan sem arcu quis nibh.
    """.trimIndent()
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
            .padding(12.dp)
    ) {
        MarkdownText(
            markdown = markdown,
            textStyle = MaterialTheme.typography.bodyMedium
                .toSpanStyle()
        )
        Spacer(modifier = Modifier.size(100.dp))
    }
}