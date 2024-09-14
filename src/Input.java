import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Класс, отвечающий за ввод данных из командной строки
public class Input {
    private List<Path> filePaths = new ArrayList<>(); // Массив путей к входным файлам

    public Input() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Прекращение ввода, если была введена пустая строка
            if (line.isEmpty()) {
                break;
            }
            // Преобразование введённой строки в путь к файла
            try {
                Path path = Paths.get(line);
                // Некорректные пути игнорируются
                if (isValid(path)) filePaths.add(path);
            } catch (InvalidPathException e) {
                System.out.println("Не удалось преобразовать строку в путь к файлу");
            }
        }
    }

    // Проверка указанного пользователем пути к файлу на корректность
    private static boolean isValid(Path path) {
        boolean valid = false;
        if (!Files.exists(path)) {
            System.out.printf("Файл %s не найден%n", path);
        } else if (!Files.isRegularFile(path)) {
            System.out.printf("Файл %s не является обычным%n", path);
        } else if (!Files.isReadable(path)) {
            System.out.printf("Невозможно прочитать файл %s%n", path);
        } else {
            valid = true;
        }
        return valid;
    }

    public List<Path> getFilePaths() {
        return filePaths;
    }
}
