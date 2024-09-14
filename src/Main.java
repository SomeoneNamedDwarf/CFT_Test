import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        // Парсинг параметров командной строки
        Options options = new Options(args);
        // Ввод входных файлов пользователем
        System.out.println("Построчно перечислите входные файлы. Для завершения введите пустую сроку.");
        Input input = new Input();
        // Парсер файлов с сборщиком статистики
        FileParser parser = new FileParser(options.getStats());
        // Класс для записи результатов в файлы
        Output output = new Output(options.getOutput(), options.getPrefix(), options.getAppend());
        // Парсинг перечисленных пользователем файлов с записью результатов в выходные файлы
        for (Path path : input.getFilePaths()) {
            System.out.printf("Чтение файла %s...%n", path);
            parser.parse(path, output);
        }
        try {
            output.close();
        } catch (IOException e) {
            System.out.println("Не удалось закрыть потоки вывода: " + e.getMessage());
        }
        // Отображение стастики (если включена)
        if (options.getStats() != null) parser.showStatistics();
    }
}