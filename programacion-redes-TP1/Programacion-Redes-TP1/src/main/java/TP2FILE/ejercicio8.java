package TP2FILE;

import java.io.*;

public class ejercicio8 {

    public static void main(String[] args) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("pagina.html"));

            PrintWriter pw =
                    new PrintWriter("paginaNueva.html");

            String linea;

            while ((linea = br.readLine()) != null) {

                linea = linea.replace(
                        "Lorem ipsum dolor sit amet.",
                        "");

                pw.println(linea);
            }

            br.close();
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}