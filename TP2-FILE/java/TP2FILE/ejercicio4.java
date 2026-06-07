package TP2FILE;

import java.io.*;

public class ejercicio4 {

    public static void main(String[] args) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("C:/TP/numeros.txt"));

            String linea;

            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}