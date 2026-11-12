package tp6;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsolaUtil {
 
    public static final String RESET = "\u001B[0m";
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CIAN = "\u001B[36m";
    public static final String AZUL = "\u001B[34m";
    public static final String NEGRITA = "\u001B[1m";

    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void log(String tipo, String color, String origen, String detalle) {
        String hora = LocalTime.now().format(FORMATO_HORA);
  
        System.out.printf("%s[%s]%s | %s%-12s%s | %-12s | %s%n",
                CIAN, hora, RESET,
                color + NEGRITA, tipo, RESET,
                origen,
                detalle);
    }

    public static void imprimirSeparador() {
        System.out.println(AZUL + "--------------------------------------------------------------------------------" + RESET);
    }
}
