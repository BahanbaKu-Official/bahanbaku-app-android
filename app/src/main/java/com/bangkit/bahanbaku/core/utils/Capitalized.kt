package com.bangkit.bahanbaku.core.utils

import java.util.*

fun capitalize(str: String): String {
    return str.trim().split("\\s+".toRegex()).joinToString(" ") { it.capitalize(Locale.ROOT) }
}