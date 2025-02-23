# Утилита фильтрации содержимого файлов.
Работа выполнялась в IDE IntelliJ IDEA 2024.2.1 (Community Edition). Сборка проекта осуществлялась встроенной системой сборки.<br/>
Версия Java 22.0.2.<br/>
Сторонние библиотеки не применялись.<br/><br/>
## Описание программы
При запуске программы пользователь должен перечислить имена файлов, из которых будет производиться чтение, разделяя их переводом на новую строку (нажатием Enter). В случае, если файл не удалось найти или он недоступен для чтения, будет выведено сообщение об ошибке и файл будет проигнорирован, после чего пользователь может продолжать перечисление.<br/>
Чтение файлов начинается только после того, как пользователь завершит перечисление файлов. Чтение производится в порядке перечисления файлов, построчно. Программа анализирует, является ли очередная строка файла целочисленным значением, числом с плавающей точкой или строкой, после чего сразу записывается в файл `integers.txt`, `floats.txt` или `strings.txt` соответственно. Строки записываются в выходные файлы в точно том же виде, в каком они были записаны в входных файлах.<br/>
Собранная статистика, если она включена, выводится только по завершении работы программы.<br/>
## Запуск программы
Для запуска программы достаточно скачать исполняемый файл `CFT_test.jar` и запустить его с помощью команды `java -jar CFT_test.jar`<br/>
### Возможные опции: <br/>
`-o <путь>` позволяет указать путь к директории, в которой будут создаваться выходные файлы (по умолчанию файлы создаются в текущей директории)<br/>
`-p <текст>` позволяет задать префикс перед именами выходных файлов, он должна содержать только символы латиницы, кириллицы, цифр, -, _, . и пробелы<br/>
`-s` включает сбор краткой статистики (количество считанных значений каждого типа)<br/>
`-f` включает сбор полной статистики (количество считанных значений каждого типа, для чисел - максимальное, минимальное, среднее значения и сумму, для строк - минимальный и максимальный размер строки)<br/>
`-a` режим дозаписи выходных файлов (по умолчанию существующие файлы перезаписываются)<br/>
Например, `java -jar CFT_test.jar -f -o output/ -p result_ -f` запишет файлы `result_integers.txt`, `result_floats.txt` и `result_strings.txt` в директорию `output/`, после чего выведет в консоль полную собранную статистику по каждому типу данных.
