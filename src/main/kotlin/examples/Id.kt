package examples

fun notId(a: Int): Int {
    if (a == 2) {
        return a
    } else {
        return 4
    }
}

fun <T> id2(a: T): T {
    while (true) {
        return a
    }
}

fun strangeId(i: Int): Int {
    if (i != 0) {
        when (i) {
            3 -> return i
            1 -> return i
            2 -> {
                println(i)
                return i
            }
        }
    }
    return i
}