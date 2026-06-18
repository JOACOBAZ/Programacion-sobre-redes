package tp3;

import java.util.Scanner;

public class Validador {
    private static final Scanner scanner = new Scanner(System.in);

    public static String leerLinea() {
        return scanner.nextLine().trim();
    }

    public static boolean esEntero(String texto) {
        return texto.matches("^-?\\d+$");
    }

    public static boolean esDecimal(String texto) {
        return texto.matches("^-?\\d+(\\.\\d+)?$") && !esEntero(texto);
    }

    public static int convertirAInt(String texto) {
        if (esEntero(texto)) {
            return Integer.parseInt(texto);
        }
        return -1;
    }

    public static float convertirAFloat(String texto) {
        if (esEntero(texto) || esDecimal(texto)) {
            return Float.parseFloat(texto);
        }
        return -1.0f;
    }

    public static String solicitarObligatorio(String campo) {
        while (true) {
            System.out.print("  Ingrese " + campo + ": ");
            String entrada = leerLinea();
            if (!entrada.isEmpty()) return entrada;
            System.out.println("  \u001B[31m[Error] El campo " + campo + " es obligatorio.\u001B[0m");
        }
    }
}
