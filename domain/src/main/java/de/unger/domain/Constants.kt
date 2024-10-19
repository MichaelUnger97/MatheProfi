package de.unger.domain

enum class Constants(private val any: Any) {
    SETTINGS("Settings"),
    TIMEOUT_SECONDS("Timeout Seconds"),
    DEFAULT_TIMEOUT_SECONDS(10L);

    fun get() = this.any
}