package test1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) throws FileNotFoundException {
        int count = 1;
        var input = new Scanner(new File("./src/test1/input.txt"));
        int t = input.nextInt();
        while (t > 0) {
            Node[] p = readData(input);
            Node[] q = readData(input);
            Node[] sum = add(p, q);
            System.out.printf("Test %d: \n", count);
            Node[] result = derivetive(sum);
            Node[] product = mul(p, q);
            showData(sum);
            showData(result);
            showData(product);
            t--;
        }
        input.close();
    }

    private static Node[] mul(Node[] p, Node[] q) {
        Node[] product = new Node[(p.length - 1) + (q.length - 1) + 1];
        for (int i =  0; i < p.length; i++) {
            for (int j = 0; j < q.length; j++) {
                int operand = p[i].getOperand() * q[j].getOperand();
                int level = p[i].getLevel() + q[j].getLevel();
                if(product[level] != null) {
                    product[level].setOperand(operand + product[level].getOperand());
                } else {
                    product[level] = new Node(operand, level);
                }
            }
        }
        return product;
    }

    /**
     * Phương thức tính đạo hàm của đa thức tổng
     *
     * @param sum đa thức tổng cần tính đạo hàm
     * @return đạo hàm bậc nhất của đa thức tổng
     */
    private static Node[] derivetive(Node[] sum) {
        Node[] result = new Node[sum.length - 1];
        for (int i = 1; i < sum.length; i++) {
            result[i - 1] = new Node(sum[i].getOperand() * sum[i].getLevel(),
                    sum[i].getLevel() - 1);
        }
        return result;
    }

    private static void showData(Node[] sum) {
        for (int i = sum.length - 1; i >= 0; i--) {
            System.out.print(sum[i].getOperand() + " ");
        }
        System.out.println();
    }

    private static Node[] readData(Scanner input) {
        int n = input.nextInt();
        Node[] p = new Node[n + 1];
        for (int i = n; i >= 0; i--) {
            int op = input.nextInt();
            Node node = new Node(op, i);
            p[i] = node;
        }
        return p;
    }

    private static Node[] add(Node[] p, Node[] q) {
        int level = maxLevel(p, q);
        Node[] sum = new Node[level + 1];
        int n1 = p.length - 1;
        int n2 = q.length - 1;
        while (n1 >= 0 && n2 >= 0) {
            if (p[n1].getLevel() == q[n2].getLevel()) {
                sum[level--] = new Node(p[n1].getOperand() + q[n2].getOperand(),
                        p[n1].getLevel());
                n1--;
                n2--;
            } else if (p[n1].getLevel() > q[n2].getLevel()) {
                sum[level--] = new Node(p[n1].getOperand(), p[n1].getLevel());
                n1--;
            } else {
                sum[level--] = new Node(q[n2].getOperand(), q[n2].getLevel());
                n2--;
            }
        }
        while (n1 >= 0) {
            sum[level--] = new Node(p[n1].getOperand(), p[n1].getLevel());
            n1--;
        }
        while (n2 >= 0) {
            sum[level--] = new Node(q[n2].getOperand(), q[n2].getLevel());
            n2--;
        }
        return sum;
    }

    private static int maxLevel(Node[] p, Node[] q) {
        return Math.max(p.length - 1, q.length - 1);
    }
}

class Node {
    private int level;
    private int operand;

    public Node() {
        level = 0;
        operand = 0;
    }

    public Node(int operand, int level) {
        this.operand = operand;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOperand() {
        return operand;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return level == node.level && operand == node.operand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, operand);
    }
}
