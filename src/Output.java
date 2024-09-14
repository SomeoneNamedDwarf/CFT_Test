import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Output {
    private Path dir;
    private String prefix;
    private boolean append;
    private static final String nameInt = "integers.txt";
    private static final String nameFloat = "floats.txt";
    private static final String nameString = "strings.txt";
    private BufferedWriter writerInt;
    private BufferedWriter writerFloat;
    private BufferedWriter writerString;

    public Output(Path dir, String prefix, boolean append) {
        this.dir = dir;
        this.prefix = prefix;
        this.append = append;
    }

    public void write(String type, String text) throws IOException {
        BufferedWriter writer = switch (type) {
            // Выбор потока, в который будет записан текст
            case "integer" -> {
                // Создание потока, если его ещё не существует
                if (writerInt == null) {
                    writerInt = createWriter(dir.resolve(Paths.get(this.prefix + nameInt)), append);
                }
                yield writerInt;
            }
            case "float" -> {
                if (writerFloat == null) {
                    writerFloat = createWriter(dir.resolve(Paths.get(this.prefix + nameFloat)), append);
                }
                yield writerFloat;
            }
            case "string" -> {
                if (writerString == null) {
                    writerString = createWriter(dir.resolve(Paths.get(this.prefix + nameString)), append);
                }
                yield writerString;
            }
            default -> throw new IllegalStateException("Указан недопустимый тип данных при записи в файл: " + type);
        };
        writer.write(text);
        writer.newLine();
        writer.flush();
    }

    // Создание потока для записи в файл
    private static BufferedWriter createWriter(Path path, boolean append) {
        try {
            // Создаём директории в пути к файлу, если их не существует
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            return new BufferedWriter(new FileWriter(path.toString(), append));
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать файловый поток для вывода: " + e.getMessage());
        }
    }

    public void close() throws IOException {
        if (writerInt != null) writerInt.close();
        if (writerFloat != null) writerFloat.close();
        if (writerString != null) writerString.close();
        writerInt = writerFloat = writerString = null;
    }
}
