# Amazed - Jaza maze generator and path finder

[Академии Бэкенда 2024][course-url].

Проект состоит из реализации консольной программы для генерации лабиринтов и поиска пути в них. Программа должна быть способна генерировать лабиринты различной сложности и размеров, а также предоставлять один или несколько методов поиска пути от заданной точки А (начала) к точке Б (конца). Интерфейс приложения должен быть простым и понятным, с возможностью отрисовки в консоли лабиринта и найденного пути.

Алгоритмы генерации лабиринтов - алгоритм Прима и Краскала.
Алгоритмы поиска пути - поиск в ширину (BFS) и А* (A-star).

## Примеры работы

![telegram-cloud-photo-size-2-5224257713750534851-y](https://github.com/user-attachments/assets/6673045d-f71d-4abb-882a-5ecfa5f8d609)

## Структура проекта

Это типовой Java-проект, который собирается с помощью инструмента автоматической
сборки проектов [Apache Maven](https://maven.apache.org/).

Проект состоит из следующих директорий и файлов:

- [pom.xml](./pom.xml) – дескриптор сборки, используемый maven, или Project
  Object Model. В нем описаны зависимости проекта и шаги по его сборке
- [src/](./src) – директория, которая содержит исходный код приложения и его
  тесты:
  - [src/main/](./src/main) – здесь находится код приложения
  - [src/test/](./src/test) – здесь находятся тесты приложения
- [mvnw](./mvnw) и [mvnw.cmd](./mvnw.cmd) – скрипты maven wrapper для Unix и
  Windows, которые позволяют запускать команды maven без локальной установки
- [checkstyle.xml](checkstyle.xml),
  [checkstyle-suppression.xml](checkstyle-suppression.xml), [pmd.xml](pmd.xml) и
  [spotbugs-excludes.xml](spotbugs-excludes.xml) – в проекте используются
  [линтеры](https://en.wikipedia.org/wiki/Lint_%28software%29) для контроля
  качества кода. Указанные файлы содержат правила для используемых линтеров
- [.mvn/](./.mvn) – служебная директория maven, содержащая конфигурационные
  параметры сборщика
- [lombok.config](lombok.config) – конфигурационный файл
  [Lombok](https://projectlombok.org/), библиотеки помогающей избежать рутинного
  написания шаблонного кода
- [.editorconfig](.editorconfig) – файл с описанием настроек форматирования кода
- [.github/workflows/build.yml](.github/workflows/build.yml) – файл с описанием
  шагов сборки проекта в среде Github
- [.gitattributes](.gitattributes), [.gitignore](.gitignore) – служебные файлы
  для git, с описанием того, как обрабатывать различные файлы, и какие из них
  игнорировать




[course-url]: https://edu.tinkoff.ru/all-activities/courses/870efa9d-7067-4713-97ae-7db256b73eab
