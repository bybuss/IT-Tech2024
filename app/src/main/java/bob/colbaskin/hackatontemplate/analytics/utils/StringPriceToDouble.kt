package bob.colbaskin.hackatontemplate.analytics.utils

fun String.toDoublePrice (): Double {
    val price = "\\d+[,.]\\d+".toRegex().find(this)?.value ?: "0"
    val replacedPrice = price.replace(",", ".")
    return replacedPrice.toDoubleOrNull() ?: 0.0
}