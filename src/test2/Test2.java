package test2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test2 {
    public static void main(String[] args) throws FileNotFoundException {
        int count = 1;
        var input = new Scanner(new File("./src/test2/input2.txt"));
        int t = input.nextInt();
        input.nextLine();
        while (t > 0) {
            var str1 = input.nextLine();
            var result = extractData(str1);
            System.out.printf("Test %d: \n", count++);
            showResult(result);
            t--;
        }
        input.close();
    }

    private static void showResult(int[] result) {
        for (int i = 0; i < result.length; i++) {
            if (isReversed(result[i])) {
                System.out.print(result[i] + " ");
            }
        }
        System.out.println();
    }

    private static boolean isReversed(int i) {
        int n = i;
        int rev = 0;
        while (i > 0) {
            rev = rev * 10 + i % 10;
            i /= 10;
        }
        return n == rev;
    }

    private static int[] extractData(String str) {
        var numbers = str.split("\\s+");
        int[] result = new int[numbers.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(numbers[i]);
        }
        return result;
    }
}
