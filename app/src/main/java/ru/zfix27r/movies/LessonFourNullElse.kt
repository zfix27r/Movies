package ru.zfix27r.movies

fun main() {
    test(null)
    test("String")
}

fun test(value: String?) {
    val r1 = value ?: "r1"
    println("r1 = $r1")
    val r2 = value.let { it ?: "r2" }
    println("r2 = $r2")
    val r3 = if (value != null) value else "r3"
    println("r3 = $r3")
    val r4 = try { value!! } catch (e: Exception) { "r4" }
    println("r4 = $r4")
    val r5 = value.orEmpty()
    println("r5 = $r5")
    val r6 = value.let { it } ?: "r6"
    println("r6 = $r6")
}