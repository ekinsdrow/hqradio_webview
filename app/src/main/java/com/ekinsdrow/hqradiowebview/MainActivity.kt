package com.ekinsdrow.hqradiowebview

import android.annotation.SuppressLint
import android.media.session.MediaSession
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.ekinsdrow.hqradiowebview.ui.theme.HQRadioWebViewTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            HQRadioWebViewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WebView()
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView() {
    val mUrl = "http://hqradio.ru/"

    AndroidView(factory = {
        WebView(it).apply {
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.databaseEnabled = true
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            webViewClient = WebViewClient()



            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                    Log.d(
                        "MyApplication", consoleMessage.message() + " -- From line "
                                + consoleMessage.lineNumber() + " of "
                                + consoleMessage.sourceId()
                    )
                    return super.onConsoleMessage(consoleMessage)
                }
            }

            loadUrl(mUrl)
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}
