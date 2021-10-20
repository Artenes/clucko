package io.github.artenes.clucko

class HourTime(
    val hour: Int,
    val minute: Int
) {

    fun toMinutes() = minute + hour * 60

}