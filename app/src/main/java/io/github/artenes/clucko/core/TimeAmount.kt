package io.github.artenes.clucko.core

class TimeAmount(val minutes: Long) {

    val seconds: Long
    get() = minutes * 60

}