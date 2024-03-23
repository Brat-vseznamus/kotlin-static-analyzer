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