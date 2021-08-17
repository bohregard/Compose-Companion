package com.bohregard.shared.compose

import android.print.PrintDocumentAdapter
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


class WebContext(
    val javaScriptEnabled: Boolean = false,
    val domStorageEnabled: Boolean = false
) {

    fun createPrintDocumentAdapter(documentName: String): PrintDocumentAdapter {
        validateWebView()
        return webView!!.createPrintDocumentAdapter(documentName)
    }

    fun goForward() {
        validateWebView()
        webView!!.goForward()
    }

    fun goBack() {
        validateWebView()
        webView!!.goBack()
    }

    fun canGoBack(): Boolean {
        validateWebView()
        return webView!!.canGoBack()
    }

    private fun validateWebView() {
        if (webView == null) {
            throw IllegalStateException("The WebView is not initialized yet.")
        }
    }

    internal var webView: WebView? = null
}

private fun WebView.setRef(ref: (WebView) -> Unit) {
    ref(this)
}

private fun WebView.setUrl(url: String) {
    if (originalUrl != url) {
        loadUrl(url)
    }
}

/**
 * Given a url, build a WebView
 *
 * @param url
 * @param webViewClient
 * @param webContext
 * @param webChromeClient
 */
@Composable
fun WebComponent(
    url: String,
    webViewClient: WebViewClient = WebViewClient(),
    webContext: WebContext,
    webChromeClient: WebChromeClient
) {
    AndroidView(factory = ::WebView, modifier = Modifier.fillMaxSize()) {
        it.setRef { view -> webContext.webView = view }
        it.setUrl(url)
        it.webViewClient = webViewClient
        it.webChromeClient = webChromeClient
        it.settings.apply {
            javaScriptEnabled = webContext.javaScriptEnabled
            domStorageEnabled = webContext.domStorageEnabled
        }
    }
}

/**
 * Given some HTML content, render the content in a WebView
 *
 * @param text
 * @param modifier
 * @param webViewClient
 * @param webContext
 */
@Composable
fun WebViewFromString(
    text: String,
    modifier: Modifier = Modifier,
    webViewClient: WebViewClient = WebViewClient(),
    webContext: WebContext = WebContext()
) {
    AndroidView(factory = ::WebView, modifier = modifier) {
        it.setRef { view -> webContext.webView = view }
        it.loadData(text, null, null)
        it.webViewClient = webViewClient
        it.settings.apply {
            javaScriptEnabled = webContext.javaScriptEnabled
            domStorageEnabled = webContext.domStorageEnabled
        }
    }
}
