package tp3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private static final String ARCHIVO = "Inventario.dat";

    public static void verificarCrearArchivo() {
        File file = new File(ARCHIVO);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar el archivo: " + e.getMessage());
        }
    }

    public static List<Productos> leerTodos() {
        List<Productos> lista = new ArrayList<Productos>();
        verificarCrearArchivo();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] campos = linea.split(";");
                if (campos.length == 4) {
                    String nombre = campos[0];
                    float pCompra = Float.parseFloat(campos[1]);
                    float pVenta = Float.parseFloat(campos[2]);
                    int stock = Integer.parseInt(campos[3]);
                    lista.add(new Productos(nombre, pCompra, pVenta, stock));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
        return lista;
    }

    public static void guardarTodos(List<Productos> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Productos p : lista) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo.");
        }
    }

    public static void agregar(Productos p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(p.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir el registro.");
        }
    }
}
