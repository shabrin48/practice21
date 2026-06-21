import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Practice21 {

    public static void mergeSort(int[] array, boolean ascending) {
        if (array.length < 2) return;
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        mergeSort(left, ascending);
        mergeSort(right, ascending);

        merge(array, left, right, ascending);
    }

    private static void merge(int[] array, int[] left, int[] right, boolean ascending) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (ascending) {
                if (left[i] <= right[j]) array[k++] = left[i++];
                else array[k++] = right[j++];
            } else {
                if (left[i] >= right[j]) array[k++] = left[i++];
                else array[k++] = right[j++];
            }
        }
        while (i < left.length) array[k++] = left[i++];
        while (j < right.length) array[k++] = right[j++];
    }

    public static void countingSort(int[] array, int min, int max, boolean ascending) {
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        for (int num : array) {
            count[num - min]++;
        }

        if (ascending) {
            for (int i = 1; i < range; i++) {
                count[i] += count[i - 1];
            }
            for (int i = array.length - 1; i >= 0; i--) {
                output[count[array[i] - min] - 1] = array[i];
                count[array[i] - min]--;
            }
        } else {
            for (int i = range - 2; i >= 0; i--) {
                count[i] += count[i + 1];
            }
            for (int i = array.length - 1; i >= 0; i--) {
                output[count[array[i] - min] - 1] = array[i];
                count[array[i] - min]--;
            }
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }

    private static void printResults(String algorithmName, int[] array, LocalTime start, LocalTime end) {
        Duration duration = Duration.between(start, end);
        long millis = duration.toMillis();
        long nanos = duration.toNanos();

        System.out.println("\n>>> Результат для: " + algorithmName);
        if (array.length <= 100) {
            System.out.println("Відсортований масив: " + Arrays.toString(array));
        } else {
            System.out.println("Відсортований масив: " + Arrays.toString(Arrays.copyOf(array, 100)) + " ... [перші 100 ел.]");
        }
        System.out.println("Час виконання: " + millis + " мс (" + nanos + " нс)");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введіть розмір масиву: ");
        int size = Integer.parseInt(scanner.nextLine());

        System.out.print("Введіть мінімальну межу діапазону: ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Введіть максимальну межу діапазону: ");
        int max = Integer.parseInt(scanner.nextLine());

        if (min > max) {
            System.out.println("Помилка: мінімальна межа не може бути більшою за максимальну!");
            return;
        }

        System.out.println("Оберіть спосіб сортування:");
        System.out.println("1. За зростанням");
        System.out.println("2. За спаданням");
        System.out.print("Ваш вибір (1 або 2): ");
        int choice = Integer.parseInt(scanner.nextLine());
        boolean ascending = (choice != 2);

        int[] originalArray = new int[size];
        for (int i = 0; i < size; i++) {
            originalArray[i] = random.nextInt((max - min) + 1) + min;
        }

        System.out.println("\nПочатковий масив ДО сортування:");
        if (size <= 100) {
            System.out.println(Arrays.toString(originalArray));
        } else {
            System.out.println(Arrays.toString(Arrays.copyOf(originalArray, 100)) + " ... [показано перші 100 елементів]");
        }
        System.out.println("----------------------------------------------------------------");

        int[] mergeCopy = Arrays.copyOf(originalArray, originalArray.length);
        int[] countingCopy = Arrays.copyOf(originalArray, originalArray.length);

        LocalTime startMerge = LocalTime.now();
        mergeSort(mergeCopy, ascending);
        LocalTime endMerge = LocalTime.now();
        printResults("Сортування злиттям (Merge Sort)", mergeCopy, startMerge, endMerge);

        LocalTime startCounting = LocalTime.now();
        countingSort(countingCopy, min, max, ascending);
        LocalTime endCounting = LocalTime.now();
        printResults("Сортування підрахунком (Counting Sort)", countingCopy, startCounting, endCounting);

        System.out.println("----------------------------------------------------------------");
        scanner.close();
    }
}