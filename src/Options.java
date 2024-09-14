import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Pattern;

public class Options {
    private Path output; // Директория для размещения выходных файлов
    private String prefix; // Префикс перед названиями выходных файлов
    private String stats; // Режим, в котором собирается статистика (null, "short", "full")
    private Boolean append; // Флаг добавления в существующие выходные файлы
    private static final String REGEX_PREFIX = "^[а-яА-Яa-zA-Z0-9._ -]{1,243}$"; // RegEx допустимых символов префикса

    public Options(String[] args) {
        // Объявление значений по умолчанию
        output = Paths.get("./");
        prefix = "";
        stats = null;
        append = false;

        // Парсинг аргументов
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o": // output
                case "-p": // prefix
                    // Считывание дополнительного параметра опций
                    if ((i + 1 < args.length) && (args[i + 1].charAt(0) != '-')) {
                        switch (args[i]) {
                            case "-o": // output
                                try {
                                    output = Paths.get(args[i + 1]);
                                } catch (InvalidPathException e) {
                                    throw new IllegalArgumentException(String.format("Не удалось преобразовать %s в путь", args[i+1]));
                                }
                                break;
                            case "-p": // prefix
                                prefix = args[i + 1];
                                if (!Pattern.matches(REGEX_PREFIX, prefix))
                                    throw new IllegalArgumentException("Префикс должен содержать только символы " +
                                            "кириллицы, латиницы, цифр, -, _, ., - и пробелы и иметь длину не более " +
                                            "243 символов");
                                break;
                        }
                        i++;
                    } else
                        throw new IllegalArgumentException(String.format("Необходимо задать значение параметра %s", args[i]));
                    break;
                case "-a": // append
                    append = true;
                    break;
                case "-s": // short stats
                    // Если заданы опции -s и -f, предпочтение отдаётся -f
                    if (!Objects.equals(stats, "full")) stats = "short";
                    break;
                case "-f": // full stats
                    stats = "full";
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Недопустимый параметр %s", args[i]));
            }
        }
    }

    public Path getOutput() {
        return output;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getStats() {
        return stats;
    }

    public Boolean getAppend() {
        return append;
    }
}
