package com.subash.salarycalc

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.webkit.WebView
import android.webkit.WebViewClient
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher

class MainActivity : Activity() {
    private lateinit var web: WebView
    private var isBackCallbackRegistered = false
    private val backCallback = OnBackInvokedCallback {
        if (web.canGoBack()) {
            web.goBack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                updateBackCallback()
            }
        }

        // Android 15+ draws edge to edge, so keep the page clear of system bars.
        web.setOnApplyWindowInsetsListener { v, insets ->
            if (Build.VERSION.SDK_INT >= 30) {
                val b = insets.getInsets(WindowInsets.Type.systemBars() or WindowInsets.Type.displayCutout())
                v.setPadding(b.left, b.top, b.right, b.bottom)
            }
            insets
        }
        setContentView(web)

        if (savedInstanceState == null) {
            web.loadUrl("file:///android_asset/index.html")
        } else {
            web.restoreState(savedInstanceState)
        }
        updateBackCallback()
    }

    private fun updateBackCallback() {
        if (Build.VERSION.SDK_INT >= 33) {
            val shouldRegister = web.canGoBack()
            if (shouldRegister && !isBackCallbackRegistered) {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                    backCallback
                )
                isBackCallbackRegistered = true
            } else if (!shouldRegister && isBackCallbackRegistered) {
                onBackInvokedDispatcher.unregisterOnBackInvokedCallback(backCallback)
                isBackCallbackRegistered = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        web.saveState(outState)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (web.canGoBack()) {
            web.goBack()
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }
}
