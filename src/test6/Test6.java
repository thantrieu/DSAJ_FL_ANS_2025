package test6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test6 {
    // thuật toán sắp xếp quicksort
    public static <T extends Comparable<T>> void quickSort(T[] arr, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            var p = partition(arr, leftIndex, rightIndex);
            quickSort(arr, leftIndex, p - 1);
            quickSort(arr, p + 1, rightIndex);
        }
    }
    // phân mảnh và sắp xếp
    public static <T extends Comparable<T>> int partition(T[] arr, int left, int right) {
        T pivot = arr[right];
        var i = left;
        for (int j = left; j <= right; j++) {
            if (arr[j].compareTo(pivot) < 0) {
                T tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
            }
        }
        T tmp = arr[i];
        arr[i] = arr[right];
        arr[right] = tmp;
        return i;
    }

    public static void main(String[] args) throws FileNotFoundException {
        var input = new Scanner(new File("./src/test6/input6.txt"));
        var t = input.nextInt();
        input.nextLine();
        int testNumber = 1;
        while(t-- > 0) {
            String x = input.nextLine().trim(); // đọc và loại bỏ các dấu cách thừa
            String str = input.nextLine(); // đọc cả dòng chuỗi đầu vào
            String[] words = str.split("\\s+");
            quickSort(words, 0, words.length - 1);
            System.out.printf("Test %d: \n", testNumber++);
            showResult(words);
            int occurent = countX(words, x);
            System.out.println(occurent);
        }
    }

    private static void showResult(String[] words) {
        for (var word : words             ) {
            System.out.print(word + ' ');
        }
        System.out.println();
    }

    private static int countX(String[] arr, String x) {
        int counter = 0;
        // tìm vị trí trái cùng xuất hiện x
        int startPos = leftMostX(arr, 0, arr.length - 1, x);
        if (startPos == -1) { // không tìm thấy x
            return counter;
        }
        // tìm vị trí phải củng xuất hiện x
        int endPos = rightMostX(arr, 0, arr.length - 1, x);
        return endPos - startPos + 1;
    }

    private static int leftMostX(String[] arr, int left, int right, String x) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            if ((mid == 0 || arr[mid - 1].compareTo(x) < 0) && arr[mid].compareTo(x) == 0) {
                return mid;
            }
            if (arr[mid].compareTo(x) < 0) { // tìm phía bên phải
                return leftMostX(arr, mid + 1, right, x);
            } else { // tìm phía trái
                return leftMostX(arr, left, mid - 1, x);
            }
        }
        return -1;
    }

    private static int rightMostX(String[] arr, int left, int right, String x) {
        if (left <= right) {
            int mid = left + (right - left) / 2;
            if ((mid == arr.length - 1 || arr[mid + 1].compareTo(x) > 0) && arr[mid].compareTo(x) == 0) {
                return mid;
            }
            if (arr[mid].compareTo(x) <= 0) { // tìm phía bên phải
                return rightMostX(arr, mid + 1, right, x);
            } else { // tìm phía trái
                return rightMostX(arr, left, mid - 1, x);
            }
        }
        return -1;
    }
}
