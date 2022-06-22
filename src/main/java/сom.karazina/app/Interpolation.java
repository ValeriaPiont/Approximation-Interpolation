package сom.karazina.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Interpolation {

    private static int N = 0;
    private static final String FILE_PATH = "src/main/resources/input.txt";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StringBuilder num = new StringBuilder();
        int c = 0, d = 0, check = 0;
        int n = 4;
        System.out.print("Enter the number of elements: ");
        n = in.nextInt();
        N = n;
        double[] x = new double[n];
        String xString = "";
        String yString = "";
        double[][] y = new double[n * 2][n * 2];

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = br.readLine();
            while (line != null) {
                if (check == 0) {
                    xString = line;
                }
                if (check == 1) {
                    yString = line;
                }
                line = br.readLine();
                check = 1;
            }
        } catch (FileNotFoundException f) {
            System.out.println(FILE_PATH + " does not exist");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String remove = xString + ' ';
        {
            int i = 0;
            if (i < remove.length()) {
                do {
                    if (c >= n) {
                        break;
                    }
                    if (remove.charAt(i) != ' ') {
                        num.append(remove.charAt(i));
                    }
                    if (remove.charAt(i) == ' ') {
                        if ("".equals(num.toString())) {
                            i++;
                            continue;
                        }
                        x[c] = Double.parseDouble(num.toString());
                        num = new StringBuilder();
                        c++;
                    }
                    i++;
                } while (i < remove.length());
            }
        }

        remove = yString + ' ';
        int i = 0;
        if (i < remove.length()) {
            do {
                if (d >= n * 2) {
                    break;
                }
                if (remove.charAt(i) != ' ') {
                    num.append(remove.charAt(i));
                }
                if (remove.charAt(i) == ' ') {
                    if ("".equals(num.toString())) {
                        i++;
                        continue;
                    }
                    y[d][0] = Double.parseDouble(num.toString());
                    num = new StringBuilder();
                    d++;
                }
                i++;
            } while (i < remove.length());
        }
        difference(x, y);
        printTable(y, n, x);
        System.out.println();
        System.out.println("Lagrange interpolation polynomial");
        printPoly(poly(y, x), n);
        System.out.println();
        System.out.println();

    }

    public static void difference(double[] a, double[][] b) {
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < a.length - i; j++) {
                b[j][i] = (b[j][i - 1] - b[j + 1][i - 1]) / (a[j] - a[i + j]);
            }
        }
    }

    static void printTable(double[][] a, int n, double[] b) {
        System.out.print("x");
        System.out.print("\t");
        for (int i = 0; i < n; i++) {
            System.out.print("f" + i);
            System.out.print("\t");
        }

        System.out.println();
        System.out.println("______________________________________________");

        for (int i = 0; i < n; i++) {
            System.out.print(b[i]);
            System.out.print("\t");
            for (int j = 0; j < n - i; j++) {
                System.out.printf("%.2f", a[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println("______________________________________________");
    }

    static double[] multi(double[] a, double[] b) {
        int total = a.length + b.length - 1;
        double[] p = new double[total];

        for (int i = 0; i < total; i++)
            p[i] = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++)
                p[i + j] += a[i] * b[j];
        }

        return p;
    }

    static double[] add(double[] a, double[] b) {
        int total = a.length + b.length - 1;
        double[] p = new double[total];
        for (int i = 0; i < total; i++)
            p[i] = 0;

        for (int i = 0; i < N; i++) {
            p[i] = a[i] + b[i];
        }

        return p;
    }

    static double[] poly(double[][] a, double[] b) {
        double[] p = new double[b.length];
        double[] q = new double[b.length];
        q[0] = b[0] * -1;
        q[1] = 1;
        double[] y;
        double[] xTotal = new double[b.length];

        xTotal[0] = a[0][0];
        p[0] = a[0][1];
        y = multi(q, p);

        xTotal = add(xTotal, y);

        for (int i = 1; i < b.length - 1; i++) {
            double t = b[i] * -1;
            double[] nextX = {t, 1};
            q = multi(q, nextX);
            p[0] = a[0][i + 1];
            y = multi(q, p);
            xTotal = add(xTotal, y);
        }

        return xTotal;
    }

    static void printPoly(double[] poly, int n) {
        for (int i = 0; i < n; i++) {
            System.out.printf("%.2f", poly[i]);
            if (i != 0)
                System.out.print("x^" + i);
            if (i != n - 1)
                System.out.print(" + ");
        }
    }

}
