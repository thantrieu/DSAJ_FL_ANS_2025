package test4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test4 {
    public static void main(String[] args) throws FileNotFoundException {
        var input = new Scanner(new File("./src/test4/input4.txt"));
        int t = input.nextInt();
        int countTest = 1;
        int n;
        while (t > 0) {
            n = input.nextInt();
            int[] values = new int[n];
            int[] weight = new int[n];
            readData(input, values, n);
            readData(input, weight, n);
            int m = input.nextInt(); // trọng lượng tối đa
            System.out.printf("Test %d: ", countTest++);
            System.out.println(knapSack(m, weight, values, n));
            t--;
        }
        input.close();
    }

    private static void readData(Scanner input, int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }
    }

    // trả về giá trị lớn nhất có thể bỏ vào túi giới hạn khối lượng W
    // chiến lược bottom-up
    static int knapSack(int W, int[] weight, int[] value, int n) {
        int[][] result = new int[n + 1][W + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= W; j++) {
                if (i == 0 || j == 0) {
                    result[i][j] = 0;
                } else if (weight[i - 1] <= j) {
                    result[i][j] = Math.max(value[i - 1] + result[i - 1][j - weight[i - 1]], result[i - 1][j]);
                } else {
                    result[i][j] = result[i - 1][j];
                }
            }
        }
        return result[n][W]; // trả về kết quả
    }
}
