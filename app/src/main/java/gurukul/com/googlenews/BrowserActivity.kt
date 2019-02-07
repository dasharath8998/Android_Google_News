package gurukul.com.googlenews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_browser.*

class BrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        var url = intent.getStringExtra("url")

        wView.loadUrl(url)
        wView.webViewClient = WebViewClient()
        wView.settings.builtInZoomControls = true
        wView.settings.javaScriptEnabled = true
    }
}
