import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StreamPerformanceComparison {

    public static void main(String[] args) {
        // Создаем список из 1,000,000 случайных чисел
        List<Integer> numbers = generateRandomNumbers(1_000_000);

        System.out.println("Тестирование производительности Stream vs ParallelStream");
        System.out.println("Размер списка: " + numbers.size() + " элементов\n");

        // Тестирование последовательного потока
        System.out.println("=== ПОСЛЕДОВАТЕЛЬНЫЙ STREAM ===");
        long sequentialTime = measureSequentialStream(numbers);

        // Тестирование параллельного потока
        System.out.println("\n=== ПАРАЛЛЕЛЬНЫЙ STREAM ===");
        long parallelTime = measureParallelStream(numbers);

        // Сравнение результатов
        System.out.println("\n=== РЕЗУЛЬТАТЫ СРАВНЕНИЯ ===");
        System.out.println("Время последовательного потока: " + sequentialTime + " мс");
        System.out.println("Время параллельного потока: " + parallelTime + " мс");

        if (sequentialTime < parallelTime) {
            double speedup = (double) sequentialTime / parallelTime;
            System.out.println("Последовательный поток быстрее в " +
                    String.format("%.2f", speedup) + " раз");
        } else {
            double speedup = (double) parallelTime / sequentialTime;
            System.out.println("Параллельный поток быстрее в " +
                    String.format("%.2f", speedup) + " раз");
        }
    }

    // Генерация случайных чисел
    private static List<Integer> generateRandomNumbers(int size) {
        System.out.println("Генерация " + size + " случайных чисел...");
        long startTime = System.currentTimeMillis();

        List<Integer> numbers = new ArrayList<>(size);
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            numbers.add(random.nextInt(1000)); // числа от 0 до 999
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Генерация завершена за " + (endTime - startTime) + " мс");

        return numbers;
    }

    // Измерение производительности последовательного потока
    private static long measureSequentialStream(List<Integer> numbers) {
        long startTime = System.currentTimeMillis();

        // Операции: фильтрация -> преобразование -> агрегация
        long result = numbers.stream()
                .filter(n -> n % 2 == 0)        // только четные числа
                .map(n -> n * 2)                // умножение на 2
                .mapToLong(Integer::longValue)   // преобразование в long
                .sum();                         // суммирование

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Результат: " + result);
        System.out.println("Время выполнения: " + executionTime + " мс");

        return executionTime;
    }

    // Измерение производительности параллельного потока
    private static long measureParallelStream(List<Integer> numbers) {
        long startTime = System.currentTimeMillis();

        // Операции: фильтрация -> преобразование -> агрегация
        long result = numbers.parallelStream()
                .filter(n -> n % 2 == 0)        // только четные числа
                .map(n -> n * 2)                // умножение на 2
                .mapToLong(Integer::longValue)   // преобразование в long
                .sum();                         // суммирование

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Результат: " + result);
        System.out.println("Время выполнения: " + executionTime + " мс");

        return executionTime;
    }
}