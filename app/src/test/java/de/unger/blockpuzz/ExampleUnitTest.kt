package de.unger.blockpuzz

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun assertd() {
        assertEquals(
            "a_Z.|@$%d", "a_Z.|@$%d"
                .replace(Regex("[^[A-Z][a-z]]"), "_")
        )
    }

    @Test
    fun asserte() {
        val STRING_LENGTH = 8
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randoms =
            List(5_000_000) { List(STRING_LENGTH) { charPool.random() }.joinToString("") }
        println(randoms.toSet().size)
    }

    @Test
    fun tresor() {
        // 4 Mal "Buchstaben" von 0-9
        val range1 = '0'..'9'
        val range2 = '0'..'9'
        val range3 = '0'..'9'
        val range4 = '0'..'9'

        //Zähler
        var counter = 0

        //Veränderbare Liste der "einzigartigen" Zahlen
        val resultList = MutableList(0, { "" })

        //alle 4 Zahlen der Reihe nach durchgehen
        range1.map { n1 ->
            range2.map { n2 ->
                range3.map { n3 ->
                    range4.map { n4 ->

                        //Alle 24 Kombinations-Möglichkeiten in eine Liste packen
                        val combinations = listOf(
                            n1.toString() + n2.toString() + n3.toString() + n4.toString(),
                            n1.toString() + n2.toString() + n4.toString() + n3.toString(),
                            n1.toString() + n3.toString() + n2.toString() + n4.toString(),
                            n1.toString() + n3.toString() + n4.toString() + n2.toString(),
                            n1.toString() + n4.toString() + n3.toString() + n2.toString(),
                            n1.toString() + n4.toString() + n2.toString() + n3.toString(),
                            n2.toString() + n1.toString() + n3.toString() + n4.toString(),
                            n2.toString() + n1.toString() + n4.toString() + n3.toString(),
                            n2.toString() + n3.toString() + n1.toString() + n4.toString(),
                            n2.toString() + n3.toString() + n4.toString() + n1.toString(),
                            n2.toString() + n4.toString() + n3.toString() + n1.toString(),
                            n2.toString() + n4.toString() + n1.toString() + n3.toString(),
                            n3.toString() + n2.toString() + n1.toString() + n4.toString(),
                            n3.toString() + n2.toString() + n4.toString() + n1.toString(),
                            n3.toString() + n1.toString() + n2.toString() + n4.toString(),
                            n3.toString() + n1.toString() + n4.toString() + n2.toString(),
                            n3.toString() + n4.toString() + n1.toString() + n2.toString(),
                            n3.toString() + n4.toString() + n2.toString() + n1.toString(),
                            n4.toString() + n2.toString() + n3.toString() + n1.toString(),
                            n4.toString() + n2.toString() + n1.toString() + n3.toString(),
                            n4.toString() + n3.toString() + n2.toString() + n1.toString(),
                            n4.toString() + n3.toString() + n1.toString() + n2.toString(),
                            n4.toString() + n1.toString() + n3.toString() + n2.toString(),
                            n4.toString() + n1.toString() + n2.toString() + n3.toString()
                        )

                        //Wenn keine Kombination in der bestehenden Liste ist
                        if (combinations.none {
                                it in resultList
                            }) {

                            //hinzufügen zur Liste{
                            resultList.add(combinations[0])

                            //Zähler hochzählen
                            counter++
                        }
                    }
                }
            }
        }

        //ausgeben des Zählers
        println(counter)

        //ausgeben der Liste
        resultList.forEach {
            println(it)
        }
    }
}