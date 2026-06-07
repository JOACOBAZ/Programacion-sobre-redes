package TP2FILE;

import java.io.*;

public class ejercicio5 {

    public static void main(String[] args) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader("C:/TP/numeros.txt"));

            PrintWriter pw =
                    new PrintWriter("C:/TP/temp.txt");

            String linea;

            while ((linea = br.readLine()) != null) {

                int num = Integer.parseInt(linea);

                if (num % 3 != 0) {
                    pw.println(num);
                }
            }

            br.close();
            pw.close();

            File original =
                    new File("C:/TP/numeros.txt");

            File temporal =
                    new File("C:/TP/temp.txt");

            original.delete();
            temporal.renameTo(original);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
