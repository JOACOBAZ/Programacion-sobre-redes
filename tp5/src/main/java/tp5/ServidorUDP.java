package tp5;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServidorUDP {

    public static void main(String[] args) {

        final int PUERTO = 9876;

        try {
            DatagramSocket socket = new DatagramSocket(PUERTO);

            System.out.println("Servidor UDP iniciado.");
            System.out.println("Esperando mensajes en el puerto " + PUERTO + "...");

            boolean continuar = true;

            while (continuar) {

                byte[] datos = new byte[1024];

                DatagramPacket paquete = new DatagramPacket(
                        datos,
                        datos.length
                );

                socket.receive(paquete);

                String mensaje = new String(
                        paquete.getData(),
                        0,
                        paquete.getLength()
                );

                String ip = paquete.getAddress().getHostAddress();
                int puerto = paquete.getPort();

                System.out.println("Mensaje recibido: " + mensaje);
                System.out.println("IP de origen: " + ip);
                System.out.println("Puerto de origen: " + puerto);
                System.out.println("--------------------------------");

                if (mensaje.equalsIgnoreCase("FIN")) {
                    continuar = false;
                }
            }

            socket.close();

            System.out.println("Servidor finalizado.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}