
Это простой анализатор кода, которому по имени файлу на языке Kotlin (путь указывается в переменной _source_) указывает
на наличие в нем функций напоминающий функцию id (то есть буквально ничего не делающие)

Мотивация: не хочется, чтобы разработчики писали такие функции и вызывали их, так как это лишние инструкции, замедляющие код
и ничего не привносящие в его работу. 

**Пример работы**:

Файл, который хотим проверить:

```kotlin
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
```

В этом коде 2 функции по факту ничего не делающие (кроме как выводят значение переменной на входе)
Хотелось бы их убрать.

Запускаем _src/main/kotlin/Main.kt_ с указанием пути до этого файла (в примере _src/main/kotlin/examples/Id.kt_)

Вывод программы:
```text
function "id2" on position 10:9 is similar to id
function "strangeId" on position 16:5 is similar to id
```

