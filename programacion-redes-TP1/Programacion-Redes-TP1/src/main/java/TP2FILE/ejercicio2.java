package TP2FILE;

import java.io.*;

public class ejercicio2 {

    public static void main(String[] args) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            PrintWriter pw =
                    new PrintWriter("numericos.txt");

            String dato;

            while (true) {

                System.out.println("Ingrese un dato (FIN para terminar):");
                dato = br.readLine();

                if (dato.equalsIgnoreCase("FIN")) {
                    break;
                }

                try {
                    Double.parseDouble(dato);
                    pw.println(dato);
                } catch (Exception e) {

                }
            }

            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
