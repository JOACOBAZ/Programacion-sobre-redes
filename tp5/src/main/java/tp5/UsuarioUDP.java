package tp5;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UsuarioUDP {

    public static void main(String[] args) {

        final int PUERTO = 9876;
        final String HOST = "localhost";

        try {
            DatagramSocket socket = new DatagramSocket();

            InetAddress direccion = InetAddress.getByName(HOST);

            Scanner teclado = new Scanner(System.in);

            boolean continuar = true;

            while (continuar) {

                System.out.print("Ingrese un mensaje: ");
                String mensaje = teclado.nextLine();

                byte[] datos = mensaje.getBytes();

                DatagramPacket paquete = new DatagramPacket(
                        datos,
                        datos.length,
                        direccion,
                        PUERTO
                );

                socket.send(paquete);

                if (mensaje.equalsIgnoreCase("FIN")) {
                    continuar = false;
                }
            }

            socket.close();
            teclado.close();

            System.out.println("Cliente finalizado.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
