package com.codechacha.xmlresourceparser

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.res.XmlResourceParser
import android.util.Log


class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var parser = resources.getXml(R.xml.sites)
        try {
            Log.d(TAG, "Parsing with v1")
            parseXmlDataV1(parser)
        } catch (e: Exception) {
            Log.d(TAG, "Failed to parse a xml file")
        }

        parser = resources.getXml(R.xml.sites)
        try {
            Log.d(TAG, "Parsing with v2")
            parseXmlDataV2(parser)
        } catch (e: Exception) {
            Log.d(TAG, "Failed to parse a xml file")
        }

    }

    private fun parseXmlDataV1(parser: XmlResourceParser) {
        var eventType = -1
        val namespace = "http://schemas.android.com/apk/res-auto"
        val defaultValue = 0

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                val element = parser.name

                if (element == "site") {
                    val titleResId = parser.getAttributeResourceValue(namespace, "title", defaultValue)
                    var title: String

                    if (titleResId == defaultValue) {
                        title = parser.getAttributeValue(namespace, "title")
                    } else {
                        title = resources.getString(titleResId)
                    }
                    val url  = parser.getAttributeValue(namespace, "url")
                    Log.d(TAG, "title : $title, url: $url")
                }
            }
            eventType = parser.next()
        }
    }

    private fun parseXmlDataV2(parser: XmlResourceParser) {
        var eventType = -1
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                val element = parser.name

                if (element == "site") {
                    val ta: TypedArray = resources.obtainAttributes(parser, R.styleable.codechacha)
                    var title = ta.getString(R.styleable.codechacha_title)
                    val url  = ta.getString(R.styleable.codechacha_url)
                    Log.d(TAG, "title : $title, url: $url")
                }
            }
            eventType = parser.next()
        }
    }

}
