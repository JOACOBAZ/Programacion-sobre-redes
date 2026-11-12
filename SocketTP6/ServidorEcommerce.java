package tp6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ServidorEcommerce {
    private static final int PUERTO = 5000;

    
    private static final String[] PRODUCTOS = {"notebook", "mouse", "teclado", "monitor", "auriculares"};
    private static final int[] CANTIDADES = {15, 42, 28, 7, 19};

    
    private static final String USER_VALIDO = "admin";
    private static final String PASS_VALIDO = "1234";

    public static void main(String[] args) {
        ConsolaUtil.imprimirSeparador();
        ConsolaUtil.log("INICIO", ConsolaUtil.VERDE, "SERVIDOR", "Iniciando servidor TCP secuencial en puerto " + PUERTO + "...");
        ConsolaUtil.imprimirSeparador();

     
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                ConsolaUtil.log("ESPERA", ConsolaUtil.AMARILLO, "SERVIDOR", "Esperando conexion de un cliente...");
                
                try (Socket clienteSocket = serverSocket.accept();
                     DataInputStream entrada = new DataInputStream(clienteSocket.getInputStream());
                     DataOutputStream salida = new DataOutputStream(clienteSocket.getOutputStream())) {

                    String ipCliente = clienteSocket.getInetAddress().getHostAddress();
                    ConsolaUtil.log("CONEXION", ConsolaUtil.VERDE, ipCliente, "Cliente conectado exitosamente.");

                    boolean autenticado = false;
                    boolean sesionActiva = true;

                    while (sesionActiva) {
                       
                        String mensajeCifrado = entrada.readUTF();
                        ConsolaUtil.log("RED (IN)", ConsolaUtil.AZUL, ipCliente, "Cifrado recibido: " + acortar(mensajeCifrado));

                        
                        String mensajePlano = CifradoUtil.desencriptar(mensajeCifrado);
                        ConsolaUtil.log("COMANDO", ConsolaUtil.CIAN, ipCliente, "Descifrado: " + mensajePlano);

                        String respuesta = "";
                        String[] partes = mensajePlano.split("\\|");
                        String accion = partes[0].toLowerCase().trim();

                        
                        if (accion.equals("salir")) {
                            respuesta = "Desconexion confirmada. ¡Gracias por operar con nosotros!";
                            sesionActiva = false;
                            ConsolaUtil.log("DESCONEXION", ConsolaUtil.ROJO, ipCliente, "El cliente cerro la sesion.");

                        } else if (accion.equals("autenticar")) {
                            if (partes.length == 3 && partes[1].equals(USER_VALIDO) && partes[2].equals(PASS_VALIDO)) {
                                autenticado = true;
                                respuesta = "OK|Autenticacion exitosa. Bienvenido al Chatbot de E-Commerce.";
                                ConsolaUtil.log("AUTH", ConsolaUtil.VERDE, ipCliente, "Usuario autenticado correctamente.");
                            } else {
                                respuesta = "ERROR|Credenciales incorrectas. Intente nuevamente.";
                                ConsolaUtil.log("AUTH ERROR", ConsolaUtil.ROJO, ipCliente, "Intento de login fallido.");
                            }

                        } else if (!autenticado) {
                            respuesta = "DENEGADO|Debe ejecutar el comando 'autenticar' antes de operar.";
                            ConsolaUtil.log("SEGURIDAD", ConsolaUtil.ROJO, ipCliente, "Acceso denegado: usuario no autenticado.");

                        } else {
                           
                            switch (accion) {
                                case "ayuda":
                                    respuesta = generarMenuChatbot();
                                    break;

                                case "chatbot":
                                    String opcion = partes.length > 1 ? partes[1] : "";
                                    respuesta = responderChatbot(opcion);
                                    break;

                                case "ver stock":
                                    String prodBuscado = partes.length > 1 ? partes[1].toLowerCase().trim() : "";
                                    respuesta = consultarStock(prodBuscado);
                                    break;

                                case "subir comprobante":
                                    if (partes.length == 3) {
                                        String nombreArchivo = "recibido_" + partes[1];
                                        byte[] archivoBytes = Base64.getDecoder().decode(partes[2]);
                                        Files.write(Paths.get(nombreArchivo), archivoBytes);
                                        respuesta = "Comprobante '" + nombreArchivo + "' recibido y guardado (" + archivoBytes.length + " bytes).";
                                        ConsolaUtil.log("ARCHIVO", ConsolaUtil.VERDE, ipCliente, "Archivo guardado: " + nombreArchivo);
                                    } else {
                                        respuesta = "Error al recibir el formato del comprobante.";
                                    }
                                    break;

                                default:
                                    respuesta = "Comando no reconocido. Escriba 'ayuda' para ver opciones.";
                                    break;
                            }
                        }

                        
                        String respuestaCifrada = CifradoUtil.encriptar(respuesta);
                        salida.writeUTF(respuestaCifrada);
                        salida.flush();
                        ConsolaUtil.log("RED (OUT)", ConsolaUtil.VERDE, "SERVIDOR", "Respuesta cifrada enviada.");
                    }

                } catch (Exception e) {
                    ConsolaUtil.log("ERROR RED", ConsolaUtil.ROJO, "CLIENTE", "Conexion interrumpida: " + e.getMessage());
                }
                ConsolaUtil.imprimirSeparador();
            }
        } catch (IOException e) {
            ConsolaUtil.log("FATAL", ConsolaUtil.ROJO, "SERVIDOR", "No se pudo abrir el puerto: " + e.getMessage());
        }
    }

    private static String generarMenuChatbot() {
        return "\n=== CHATBOT E-COMMERCE (PREGUNTAS GUIADAS) ===\n" +
               "  [1] ¿Cuales son los medios de pago disponibles?\n" +
               "  [2] ¿Cuanto demora el envio a domicilio?\n" +
               "  [3] ¿Como realizo un cambio o devolucion?\n" +
               "  [4] ¿Tienen garantia los productos?\n" +
               "  -> Para consultar al bot escriba: bot <numero> (Ej: bot 1)";
    }

    private static String responderChatbot(String opcion) {
        switch (opcion) {
            case "1":
                return "BOT: Aceptamos tarjetas de credito (hasta 6 cuotas sin interes), debito, transferencia bancaria y Mercado Pago.";
            case "2":
                return "BOT: Los envios demoran entre 24 y 48 horas habiles en AMBA, y de 3 a 5 dias habiles al resto del pais.";
            case "3":
                return "BOT: Tenes 30 dias corridos desde la recepcion para gestionar cambios sin costo presentando tu comprobante.";
            case "4":
                return "BOT: Todos nuestros productos tecnologicos cuentan con 12 meses de garantia oficial de fabrica.";
            default:
                return "BOT: Opcion invalida. Escriba 'ayuda' para ver los numeros de preguntas disponibles.";
        }
    }

    private static String consultarStock(String producto) {
        for (int i = 0; i < PRODUCTOS.length; i++) {
            if (PRODUCTOS[i].equals(producto)) {
                return "STOCK: El producto '" + PRODUCTOS[i].toUpperCase() + "' tiene " + CANTIDADES[i] + " unidades disponibles.";
            }
        }
        return "STOCK: Producto '" + producto + "' no encontrado. Disponibles: notebook, mouse, teclado, monitor, auriculares.";
    }

    private static String acortar(String texto) {
        return texto.length() > 25 ? texto.substring(0, 25) + "..." : texto;
    }
}

