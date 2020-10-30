package com.bohregard.shared.compose

import android.print.PrintDocumentAdapter
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.emitView


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

@Composable
fun WebComponent(
    url: String,
    webViewClient: WebViewClient = WebViewClient(),
    webContext: WebContext
) {
    emitView(::WebView) {
        it.settings.apply {
            javaScriptEnabled = webContext.javaScriptEnabled
            domStorageEnabled = webContext.domStorageEnabled
        }
        it.setRef { view -> webContext.webView = view }
        it.setUrl(url)
        it.webViewClient = webViewClient
    }
}
