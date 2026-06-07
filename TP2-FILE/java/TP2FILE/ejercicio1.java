package TP2FILE;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ejercicio1 {

    public static void main(String[] args) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            PrintWriter pw =
                    new PrintWriter("ultimoDato.txt");

            String dato = "";

            while (true) {

                System.out.println("Ingrese un dato (FIN para terminar):");
                dato = br.readLine();

                if (dato.equalsIgnoreCase("FIN")) {
                    break;
                }
            }

            pw.print(dato);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


