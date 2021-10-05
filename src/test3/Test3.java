package test3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Test3 {
    public static void main(String[] args) throws FileNotFoundException {
        var input = new Scanner(new File("./src/test3/input3.txt"));
        int test = input.nextInt();
        input.nextLine();
        while (test > 0) {
            var str = input.nextLine();
            str = addSpace(str);
            var postfix = infixToPostfix(str);
            var result = calculateResult(postfix);
            System.out.printf("%1.2f\n", result);
            test--;
        }
        input.close();
    }

    /**
     * Phương thức kiểm tra thứ tự ưu tiên của toán tử.
     *
     * @param operator toán tử cần lấy thứ tự ưu tiên.
     * @return thứ tự ưu tiên của toán tử đang xét trong đề bài.
     */
    private static int precedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> 0;
        };
    }

    /**
     * Phương thức thêm dấu cách vào trước và sau các dấu ngoặc và phép toán.
     * Mục đích nhằm dễ dàng cho việc phân tách biểu thức thành từng phần tử đơn.
     *
     * @param input biểu thức trung tố đầu vào
     * @return input sau khi đã thêm dấu cách vào trước và sau dấu ngoặc, phép toán.
     */
    private static String addSpace(String input) {
        input = input.replaceAll("\\^", " ^ ");
        input = input.replaceAll("\\(", " ( ");
        input = input.replaceAll("\\)", " ) ");
        input = input.replaceAll("\\+", " + ");
        input = input.replaceAll("-", " - ");
        input = input.replaceAll("\\*", " * ");
        input = input.replaceAll("/", " / ");
        return input;
    }

    /**
     * Phương thức chuyển đổi từ trung tố sang hậu tố.
     *
     * @param str chuỗi chứa biểu thức trung tố.
     * @return biểu thức hậu tố tương ứng.
     */
    private static String infixToPostfix(String str) {
        var infixElements = str.split("\\s+");
        var result = new StringBuilder();
        Stack<String> stack = new Stack<>();
        for (var e : infixElements) {
            if (precedence(e) > 0) { // e là toán tử
                while (!stack.isEmpty() && precedence(e) <= precedence(stack.peek())) {
                    result.append(stack.peek()).append(" ");
                    stack.pop();
                }
                stack.push(e);
            } else if (e.equals(")")) { // là dấu ngoặc đóng
                var operator = stack.peek();
                stack.pop();
                while (!operator.equals("(")) {
                    result.append(operator).append(" ");
                    operator = stack.peek();
                    stack.pop();
                }

            } else if (e.equals("(")) { // là dấu ngoặc mở
                stack.push(e);
            } else { // nếu là toán hạng, push vào stack
                result.append(e).append(" ");
            }
        }
        // pop các phần tử còn lại của stack
        while (!stack.isEmpty()) {
            var operator = stack.peek();
            stack.pop();
            if (!operator.equals("(")) {
                result.append(operator).append(" ");
            }
        }
        return result.toString();
    }

    private static boolean isOperator(String input) {
        return input.equals("+") || input.equals("-") || input.equals("*") ||
                input.equals("/") || input.equals("^");
    }

    private static double calculateResult(String str) {
        double result;
        Stack<String> stack = new Stack<>();
        String[] words = str.split("\\s+"); // tách biểu thức tại vị trí có 1 hoặc nhiều dấu cách
        for (var e : words) {
            if (isOperator(e)) {
                double b = Double.parseDouble(stack.peek());
                stack.pop();
                double a = Double.parseDouble(stack.peek());
                stack.pop();
                result = makeResult(a, b, e);
                stack.push(result + "");
            } else {
                stack.push(e);
            }
        }
        return Double.parseDouble(stack.peek());
    }

    /**
     * Phương thức tính toán kết quả a operator b.
     *
     * @param a toán hạng đầu
     * @param b toán hạng sau
     * @param e toán tử
     * @return giá trị đạt được
     */
    private static double makeResult(double a, double b, String e) {
        return switch (e) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "^" -> Math.pow(a, b);
            default -> 0;
        };
    }
}
