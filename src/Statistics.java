import java.math.BigInteger;

// Абстрактный класс
abstract class Statistics {
    abstract public void addEntry(BigInteger entry);

    abstract public void addEntry(double entry);

    abstract public void addEntry(String entry);

    abstract public void show();
}

// Краткая статистика
class StatisticsShort extends Statistics {
    protected int counterInt = 0;
    protected int counterFloat = 0;
    protected int counterString = 0;

    @Override
    public void addEntry(BigInteger entry) {
        counterInt++;
    }

    @Override
    public void addEntry(double entry) {
        counterFloat++;
    }

    @Override
    public void addEntry(String entry) {
        counterString++;
    }

    @Override
    public void show() {
        System.out.printf("Записано целых чисел: %d%n", counterInt);
        System.out.printf("Записано чисел с плавающей точкой: %d%n", counterFloat);
        System.out.printf("Записано строк: %d%n", counterString);
    }
}

// Полная статистика
class StatisticsFull extends StatisticsShort {
    private BigInteger minInt = null;
    private BigInteger maxInt = null;
    private BigInteger sumInt = new BigInteger("0");
    private double meanInt = 0.;
    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double sumFloat = 0.;
    private double meanFloat = 0.;
    private int maxString = Integer.MIN_VALUE;
    private int minString = Integer.MAX_VALUE;

    @Override
    public void addEntry(BigInteger entry) {
        counterInt++;
        // Если первая запись
        if (minInt == null || minInt.compareTo(entry) > 0) minInt = entry;
        if (maxInt == null || maxInt.compareTo(entry) < 0) maxInt = entry;
        sumInt = sumInt.add(entry);
        meanInt = sumInt.doubleValue() / (double) counterInt;
    }

    @Override
    public void addEntry(double entry) {
        counterFloat++;
        if (minFloat > entry) minFloat = entry;
        if (maxFloat < entry) maxFloat = entry;
        sumFloat += entry;
        meanFloat = sumFloat / counterFloat;
    }

    @Override
    public void addEntry(String entry) {
        counterString++;
        int len = entry.length();
        if (minString > len) minString = len;
        if (maxString < len) maxString = len;
    }

    @Override
    public void show() {
        System.out.println("--- Целые числа ---");
        System.out.printf("Записано: %d%n", counterInt);
        if (counterInt > 0) {
            System.out.printf("Минимум: %d%n", minInt);
            System.out.printf("Максимум: %d%n", maxInt);
            System.out.printf("Сумма: %d%n", sumInt);
            System.out.printf("Среднее: %f%n", meanInt);
        }
        System.out.println("--- Числа с плавающей точкой ---");
        System.out.printf("Записано: %d%n", counterFloat);
        if (counterFloat > 0) {
            System.out.printf("Минимум: %f%n", minFloat);
            System.out.printf("Максимум: %f%n", maxFloat);
            System.out.printf("Сумма: %f%n", sumFloat);
            System.out.printf("Среднее: %f%n", meanFloat);
        }
        System.out.println("--- Строки ---");
        System.out.printf("Записано: %d%n", counterString);
        if (counterString > 0) {
            System.out.printf("Минимальная длина: %d%n", minString);
            System.out.printf("Максимальная длина: %d%n", maxString);
        }
    }
}