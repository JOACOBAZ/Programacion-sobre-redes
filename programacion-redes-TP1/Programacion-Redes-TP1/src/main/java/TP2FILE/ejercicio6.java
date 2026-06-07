package TP2FILE;

import java.io.*;

public class ejercicio6 {

    public static boolean esPrimo(int n) {

        if (n < 2)
            return false;

        for (int i = 2; i < n; i++) {

            if (n % i == 0)
                return false;
        }

        return true;
    }

    public static void main(String[] args) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("C:/TP/numeros.txt"));

            PrintWriter pw =
                    new PrintWriter("D:/TP/primos.dat");

            String linea;

            while ((linea = br.readLine()) != null) {

                int num = Integer.parseInt(linea);

                if (esPrimo(num)) {
                    pw.println(num);
                }
            }

            br.close();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
