package de.unger.domain.entities

enum class KindOfExercise(val sign: String) {
    PLUS("+"), MINUS("-"), DIVIDE("/"), MULTIPLY("*");

    override fun toString(): String {
        return sign
    }
}