package test5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test5 {
    public static void main(String[] args) throws FileNotFoundException {
        var input = new Scanner(new File("./src/test5/input5.txt"));
        int t = input.nextInt();
        int testCount = 1;
        while (t-- > 0) {
            int n = input.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = input.nextInt();
            }
            System.out.printf("Test %d: \n", testCount++);
            findLIS(arr);
        }
    }

    // Iterative function to find the longest increasing subsequence
    // of a given array
    public static void findLIS(int[] arr) {
        // trường hợp cơ sở
        if (arr == null || arr.length == 0) {
            return;
        }
        // LIS[i] lưu trữ tập các phần tử tăng dần
        // arr[0…i] kết thúc với arr[i]
        List<List<Integer>> LIS = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            LIS.add(new ArrayList<>());
        }
        // LIS[0] chứa danh sách các phần tử tăng dần kết thúc bởi arr[0]
        LIS.get(0).add(arr[0]);
        // bắt đầu từ vị trí phần tử thứ hai trong mảng
        for (int i = 1; i < arr.length; i++) {
            // thực hiện cho tất cả các phần tử trước vị trí i
            for (int j = 0; j < i; j++) {
                // tìm chuỗi tăng liên tục kết thúc bởi phần tử arr[j]
                // trong đó arr[j] phải nhỏ hơn phần tử arr[i]
                if (arr[j] < arr[i] && LIS.get(j).size() > LIS.get(i).size()) {
                    LIS.set(i, new ArrayList<>(LIS.get(j)));
                }
            }
            // bao gồm cả phần tử arr[i] vào danh sách thứ i của LIS
            LIS.get(i).add(arr[i]);
        }

        // resultIndex sẽ lưu trữ danh sách chứa kết quả dài nhất trong LIS
        int resultIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (LIS.get(resultIndex).size() < LIS.get(i).size()) {
                resultIndex = i;
            }
        }

        // hiển thị kết quả
        System.out.println(LIS.get(resultIndex).size());
        showResult(LIS.get(resultIndex));
    }

    private static void showResult(List<Integer> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            System.out.printf("%d ", resultList.get(i));
        }
        System.out.println();
    }
}
