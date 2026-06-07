package TP2FILE;

import java.io.*;

public class ejercicio9 {

    public static void main(String[] args) {

        try {

            BufferedReader teclado =
                    new BufferedReader(
                            new InputStreamReader(System.in));

            while (true) {

                System.out.println("1- Agregar");
                System.out.println("2- Mostrar");
                System.out.println("3- Salir");

                int op =
                        Integer.parseInt(
                                teclado.readLine());

                switch (op) {

                    case 1:

                        PrintWriter pw =
                                new PrintWriter(
                                        new FileOutputStream(
                                                "clima.dat",
                                                true));

                        System.out.println("Fecha:");
                        String fecha =
                                teclado.readLine();

                        System.out.println("Temperatura:");
                        String temp =
                                teclado.readLine();

                        pw.println(fecha + ";" + temp);

                        pw.close();

                        break;

                    case 2:

                        BufferedReader br =
                                new BufferedReader(
                                        new FileReader(
                                                "clima.dat"));

                        String linea;

                        while ((linea = br.readLine()) != null) {
                            System.out.println(linea);
                        }

                        br.close();

                        break;

                    case 3:
                        return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}