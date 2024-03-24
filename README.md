
# Описание
Это простой анализатор кода, который при запуске проверяет наличие в проекте о наличии в нем функций напоминающий функцию id (то есть ничего не делающие методы)

Мотивация: не хочется, чтобы разработчики писали такие функции и вызывали их, так как это лишние инструкции, замедляющие код
и ничего не привносящие в его работу. 

## Пример работы

Пусть в проекте есть файл вида:

```kotlin
fun idMaybe(x: Int): Int {
    return x
}
```

Запускаем анализатор и получаем следующий вывод:

Вывод программы:
```text
Run analyzer for /path-to-project/.!
Exception in thread "main" java.lang.Error: 
/path-to-project/./src/main/kotlin/RunAnalyzer.kt:1:5: function "idMaybe" is similar to identical function
        at RunAnalyzerKt.main(RunAnalyzer.kt:47)
        at RunAnalyzerKt.main(RunAnalyzer.kt)

```

## Как запустить:
Из этого репозитория запускаем:
```bash
./gradlew distZip
```

Далее запускаем:
```bash
unzip build/distributions/kotlin-static-analyzer-1.0-SNAPSHOT.zip
```

Получаем папку _kotlin-static-analyzer-1.0-SNAPSHOT_. 
В ней лежат исходники для запуска по пути: _kotlin-static-analyzer-1.0-SNAPSHOT/bin/_

Пример:
```bash
./kotlin-static-analyzer-1.0-SNAPSHOT/bin/kotlin-static-analyzer
Run analyzer for /path-to-project/.!
Everything is okay!
```