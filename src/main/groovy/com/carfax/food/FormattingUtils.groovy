package com.carfax.food

import groovy.util.logging.Slf4j
import org.apache.commons.lang.StringEscapeUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

@Slf4j
class FormattingUtils {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:mm a")

    static Date createServeDate(String serveDate, String serveTime) {
        String datePart = serveDate.split('T')[0]

        StringBuilder builder = new StringBuilder(datePart)
        builder.append(" ${serveTime.replaceAll('  ', ' ')}")

        try {
            return dateFormat.parse(builder.toString())
        } catch (ParseException e) {
            log.info("Couldn't parse date:  ${builder.toString()}")
            return null
        }
    }

    static String stripFormatting(String string) {
        String unescapedString = StringEscapeUtils.unescapeHtml(string)

        Element body = Jsoup.parseBodyFragment(unescapedString).body()
        Node node = body
        while (!(node in TextNode) && node != null) {
            if (!node.childNodes().isEmpty()) {
                node = node.childNode(0)
            } else {
                node = null
            }
        }

        return node
    }
}
