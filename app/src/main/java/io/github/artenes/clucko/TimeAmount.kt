package io.github.artenes.clucko

class TimeAmount(val minutes: Long) {

    val seconds: Long
    get() = minutes * 60

}