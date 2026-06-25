package tp4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    private static final String ARCHIVO = "agenda.dat";

    public static List<Contacto> cargar() {

        List<Contacto> lista = new ArrayList<>();

        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br =
                new BufferedReader(
                        new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                if (datos.length >= 4) {

                    String nota =
                            CryptoAES.desencriptar(datos[3]);

                    lista.add(new Contacto(
                            datos[0],
                            datos[1],
                            datos[2],
                            nota));
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error cargando archivo: "
                            + e.getMessage());
        }

        return lista;
    }

    public static void guardar(
            List<Contacto> lista) {

        File temporal = new File("agenda.tmp");

        try (PrintWriter pw =
                new PrintWriter(temporal)) {

            for (Contacto c : lista) {

                String notaCifrada =
                        CryptoAES.encriptar(
                                c.getNotaPrivada());

                pw.println(
                        c.getNombre() + ";" +
                        c.getTelefono() + ";" +
                        c.getEmail() + ";" +
                        notaCifrada);
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al guardar: "
                            + e.getMessage());
            return;
        }

        try {

            Files.move(
                    temporal.toPath(),
                    Paths.get(ARCHIVO),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {

            System.out.println(
                    "Error reemplazando archivo: "
                            + e.getMessage());
        }
    }
}