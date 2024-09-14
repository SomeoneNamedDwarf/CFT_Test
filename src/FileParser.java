import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileParser {
    private Statistics stats;

    public FileParser(String StatsMode) {
        // Создание сборщика статистики
        switch (StatsMode) {
            case null:
                stats = null;
                break;
            case "short":
                stats = new StatisticsShort();
                break;
            case "full":
                stats = new StatisticsFull();
                break;
            default:
                throw new IllegalStateException("Недопустимый режим сбора статистики: " + StatsMode);
        }
    }

    // Обработка одного файла с одновременной записью в выходные файлы
    public void parse(Path path, Output output) {
        // Проверка на возможность чтения файла
        if (!Files.isReadable(path)) {
            System.out.printf("Не удалось прочитать файл %s%n", path);
        } else {
            // Чтение и запись по строкам
            try (Stream<String> lines = Files.lines(path)) {
                lines.forEach(line -> {
                    String type = parseLine(line.trim());
                    try {
                        output.write(type, line);
                    } catch (IOException e) {
                        System.out.printf("Не удалось записать текст %s%n", line);
                    }
                });
            } catch (IOException e) {
                System.out.printf("Возникла ошибка при чтении файла %s%n", path);
            }
        }
    }

    // Обработка одной строки
    // Возвращает тип значения (integer, float, string), записанного в строке
    private String parseLine(String line) {
        String type;
        // Попытка считать строку как BigInteger
        try {
            BigInteger value = new BigInteger(line);
            stats.addEntry(value);
            type = "integer";
        } // Попытка считать строку как BigDecimal
        catch (NumberFormatException e) {
            try {
                BigDecimal value = new BigDecimal(line);
                stats.addEntry(value);
                type = "float";
            } // Если не BigInteger и не BigDecimal, то строка принимается за тип String
            catch (NumberFormatException ex) {
                stats.addEntry(line);
                type = "string";
            }
        }
        return type;
    }

    // Отображение собранной статистики в консоль
    public void showStatistics() {
        if (stats != null) stats.show();
    }
}
