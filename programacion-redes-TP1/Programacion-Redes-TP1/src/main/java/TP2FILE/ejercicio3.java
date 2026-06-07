package TP2FILE;

import java.io.*;

public class ejercicio3 {

    public static void main(String[] args) {

        try {

            PrintWriter pw =
                    new PrintWriter("C:/TP/numeros.txt");

            for (int i = 0; i <= 1000; i += 2) {
                pw.println(i);
            }

            pw.close();

            System.out.println("Archivo creado");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}