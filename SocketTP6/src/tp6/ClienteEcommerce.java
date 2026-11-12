package tp6;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class ClienteEcommerce {
    
    private static final String IP_SERVIDOR = "127.0.0.1";
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> historialChat = new ArrayList<>();

        ConsolaUtil.imprimirSeparador();
        ConsolaUtil.log("INICIO", ConsolaUtil.CIAN, "CLIENTE", "Conectando al servidor en " + IP_SERVIDOR + ":" + PUERTO + "...");

        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             DataInputStream entrada = new DataInputStream(socket.getInputStream());
             DataOutputStream salida = new DataOutputStream(socket.getOutputStream())) {

            ConsolaUtil.log("CONEXION", ConsolaUtil.VERDE, "CLIENTE", "Conexion TCP establecida con exito.");
            mostrarComandosDisponibles();

            boolean ejecutando = true;

            while (ejecutando) {
                System.out.print(ConsolaUtil.NEGRITA + "\nIngrese comando > " + ConsolaUtil.RESET);
                String comandoIngresado = scanner.nextLine().trim();

                if (comandoIngresado.isEmpty()) continue;

                String cargaUtilRed = "";
                boolean enviarAlServidor = true;

                switch (comandoIngresado.toLowerCase()) {
                    case "autenticar":
                        System.out.print("  Usuario: ");
                        String user = scanner.nextLine().trim();
                        System.out.print("  Clave:   ");
                        String pass = scanner.nextLine().trim();
                        cargaUtilRed = "autenticar|" + user + "|" + pass;
                        historialChat.add("CLIENTE -> Solicitud de autenticacion (Usuario: " + user + ")");
                        break;

                    case "ayuda":
                        cargaUtilRed = "ayuda";
                        historialChat.add("CLIENTE -> Solicito menu de ayuda del chatbot");
                        break;

                    case "ver stock de un producto":
                        System.out.print("  Nombre del producto (ej: notebook, mouse): ");
                        String prod = scanner.nextLine().trim();
                        cargaUtilRed = "ver stock|" + prod;
                        historialChat.add("CLIENTE -> Consulto stock de: " + prod);
                        break;

                    case "subir comprobante":
                        System.out.print("  Ruta del archivo local (ej: comprobante.txt o factura.pdf): ");
                        String rutaStr = scanner.nextLine().trim();
                        Path ruta = Paths.get(rutaStr);

                        if (Files.exists(ruta) && !Files.isDirectory(ruta)) {
                            byte[] bytesArchivo = Files.readAllBytes(ruta);
                            String base64Archivo = Base64.getEncoder().encodeToString(bytesArchivo);
                            String nombreArchivo = ruta.getFileName().toString();
                            cargaUtilRed = "subir comprobante|" + nombreArchivo + "|" + base64Archivo;
                            historialChat.add("CLIENTE -> Envio comprobante: " + nombreArchivo);
                            ConsolaUtil.log("ARCHIVO", ConsolaUtil.AMARILLO, "CLIENTE", "Preparando archivo (" + bytesArchivo.length + " bytes)...");
                        } else {
                            ConsolaUtil.log("ERROR FILE", ConsolaUtil.ROJO, "CLIENTE", "El archivo no existe en la ruta indicada.");
                            enviarAlServidor = false;
                        }
                        break;

                    case "ver historial de chat":
                        enviarAlServidor = false;
                        ConsolaUtil.imprimirSeparador();
                        ConsolaUtil.log("HISTORIAL", ConsolaUtil.CIAN, "SESION", "Mensajes registrados en esta sesion:");
                        if (historialChat.isEmpty()) {
                            System.out.println("  (No hay mensajes registrados aun)");
                        } else {
                            for (int i = 0; i < historialChat.size(); i++) {
                                System.out.println("  " + (i + 1) + ". " + historialChat.get(i));
                            }
                        }
                        ConsolaUtil.imprimirSeparador();
                        break;

                    case "salir":
                        cargaUtilRed = "salir";
                        historialChat.add("CLIENTE -> Cerro la sesion.");
                        ejecutando = false;
                        break;

                    default:
                        
                        if (comandoIngresado.toLowerCase().startsWith("bot ")) {
                            String num = comandoIngresado.substring(4).trim();
                            cargaUtilRed = "chatbot|" + num;
                            historialChat.add("CLIENTE -> Pregunta Chatbot opcion " + num);
                        } else {
                            ConsolaUtil.log("AVISO", ConsolaUtil.AMARILLO, "CLIENTE", "Comando no reconocido. Use los comandos exactos.");
                            mostrarComandosDisponibles();
                            enviarAlServidor = false;
                        }
                        break;
                }

               
                if (enviarAlServidor) {
                    String cifrado = CifradoUtil.encriptar(cargaUtilRed);
                    salida.writeUTF(cifrado);
                    salida.flush();
                    ConsolaUtil.log("RED (OUT)", ConsolaUtil.AZUL, "CLIENTE", "Comando cifrado enviado al servidor.");

                    String respuestaCifrada = entrada.readUTF();
                    String respuestaPlana = CifradoUtil.desencriptar(respuestaCifrada);

                    ConsolaUtil.log("RESPUESTA", ConsolaUtil.VERDE, "SERVIDOR", respuestaPlana);
                    historialChat.add("SERVIDOR -> " + respuestaPlana.replace("\n", " "));
                }
            }

            ConsolaUtil.log("CIERRE", ConsolaUtil.ROJO, "CLIENTE", "Recursos liberados y socket cerrado.");

        } catch (Exception e) {
            ConsolaUtil.log("ERROR", ConsolaUtil.ROJO, "CLIENTE", "Fallo en la comunicacion: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void mostrarComandosDisponibles() {
        ConsolaUtil.imprimirSeparador();
        System.out.println(ConsolaUtil.NEGRITA + "CATALOGO DE COMANDOS DISPONIBLES:" + ConsolaUtil.RESET);
        System.out.println("  * autenticar               -> Validar acceso de usuario (admin / 1234)");
        System.out.println("  * ayuda                    -> Desplegar preguntas guiadas del chatbot");
        System.out.println("  * ver stock de un producto -> Consultar unidades en el arreglo del servidor");
        System.out.println("  * subir comprobante        -> Transferir un archivo hacia el servidor");
        System.out.println("  * ver historial de chat    -> Repasar los mensajes de la sesion actual");
        System.out.println("  * salir                    -> Cerrar sockets y finalizar");
        ConsolaUtil.imprimirSeparador();
    }
}

