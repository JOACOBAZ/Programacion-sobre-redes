package TP2FILE;

import java.io.*;

public class ejercicio7 {

    public static void main(String[] args) {

        try {

            BufferedReader teclado =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            PrintWriter pw =
                    new PrintWriter("C:/TP/caracteres.dat");

            for (int i = 1; i <= 10; i++) {

                System.out.println("Ingrese palabra:");

                String palabra = teclado.readLine();

                pw.println(palabra);
            }

            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
