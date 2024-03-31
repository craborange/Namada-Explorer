package namada.crab_orange.namada_explorer.extension

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

val String.formatDate: String
    get() {
        val locale = Locale.getDefault()
        for (
        format in listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'", locale),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", locale),
        )
        ) {
            try {
                val date = format.parse(this)
                if (date != null) {
                    return SimpleDateFormat(
                        "HH:mm, dd/MM/yyyy",
                        locale
                    ).format(date)
                }
            } catch (e: Exception) {
                continue
            }
        }
        return this
    }

val Long.formatLongToText: String
    get() {
        val suffix = listOf("", "K", "M", "B", "T", "P", "E")
        var value = this.toDouble()
        var index = 0
        while (value >= 1000) {
            value /= 1000
            index++
        }
        return String.format("%.0f%s", value, suffix[index])
    }

val String.middleEllipsis: String
    get() {
        val up = uppercase()
        return "${up.substring(0..<3)}...${up.substring((length - 3)..<length)}"
    }

val Double.roundOffDecimal: String
    get() {
        val pattern = StringBuilder("#,##0.")
        repeat(2) { pattern.append("#") }
        val df = DecimalFormat(pattern.toString())
        return df.format(this)
    }

val String.timeAgoString: String
    get() {
        val now = Date()
        val locale = Locale.getDefault()
        for (
        format in listOf(
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'", locale),
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", locale),
        )
        ) {
            try {
                val date = format.parse(this)
                if (date != null) {
                    val timeDifference = TimeUnit.MILLISECONDS.toSeconds(now.time - date.time)
                    return when {
                        timeDifference < 60 -> "1 minute ago"
                        timeDifference < 3600 -> "${timeDifference / 60} minutes ago"
                        timeDifference < 7200 -> "1 hour ago"
                        timeDifference < 86400 -> "${timeDifference / 3600} hours ago"
                        timeDifference < 172800 -> "1 day ago"
                        else -> formatDate
                    }
                }
            } catch (e: Exception) {
                continue
            }
        }
        return this
    }

val Long.formatWithCommas: String
    get() {
        return DecimalFormat("#,###").format(this)
    }