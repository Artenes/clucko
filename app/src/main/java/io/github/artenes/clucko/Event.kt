package io.github.artenes.clucko

class Event<T>(private val value: T) {

    private var isConsumed = false

    fun get(): T? =
        if (isConsumed) {
            null
        } else {
            isConsumed = true
            value
        }

}