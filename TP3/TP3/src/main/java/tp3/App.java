package tp3;

import java.util.List;

public class App {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String ROJO = "\u001B[31m";

    public static void main(String[] args) {
        Inventario.verificarCrearArchivo();
        boolean ejecucion = true;

        while (ejecucion) {
            System.out.println("\n" + CYAN + "========================================" + RESET);
            System.out.println("    " + CYAN + "SISTEMA DE GESTIÓN DE INVENTARIO" + RESET);
            System.out.println(CYAN + "========================================" + RESET);
            System.out.println("\t1. Agregar producto");
            System.out.println("\t2. Mostrar productos");
            System.out.println("\t3. Editar producto");
            System.out.println("\t4. Eliminar producto");
            System.out.println("\t5. Salir");
            System.out.println(CYAN + "========================================" + RESET);
            System.out.print("Seleccione una opción: ");

            String opcion = Validador.leerLinea();

            switch (opcion) {
                case "1" -> agregarProducto();
                case "2" -> mostrarProductos();
                case "3" -> editarProducto();
                case "4" -> eliminarProducto();
                case "5" -> {
                    System.out.println("\n\t" + VERDE + "Saliendo de la aplicación... ¡Adiós!" + RESET);
                    ejecucion = false;
                }
                default -> System.out.println("\n\t" + ROJO + "[Error] Opción no válida." + RESET);
            }
        }
    }

    private static void agregarProducto() {
        System.out.println("\n" + AMARILLO + "--- Registrar Nuevo Artículo ---" + RESET);
        String nombre = Validador.solicitarObligatorio("Nombre");

        float pCompra = solicitarFloatValido("Precio de Compra");
        float pVenta = solicitarFloatValido("Precio de Venta");
        int stock = solicitarIntValido("Stock");

        Productos nuevo = new Productos(nombre, pCompra, pVenta, stock);
        Inventario.agregar(nuevo);
        System.out.println("\n\t" + VERDE + "¡Producto agregado exitosamente!" + RESET);
    }

    private static void mostrarProductos() {
        List<Productos> productos = Inventario.leerTodos();
        System.out.println("\n" + AMARILLO + "---------------------------------------------------------" + RESET);
        System.out.printf(AMARILLO + "%-15s \t %-10s \t %-10s \t %-6s\n" + RESET, "NOMBRE", "P. COMPRA", "P. VENTA", "STOCK");
        System.out.println(AMARILLO + "---------------------------------------------------------" + RESET);

        if (productos.isEmpty()) {
            System.out.println("\t\tNo hay registros disponibles.");
        } else {
            for (Productos p : productos) {
                System.out.printf("%-15s \t $%-9.2f \t $%-9.2f \t %-6d\n", 
                        p.getNombre(), p.getPrecioCompra(), p.getPrecioVenta(), p.getStock());
            }
        }
        System.out.println(AMARILLO + "---------------------------------------------------------" + RESET);
    }

    private static void editarProducto() {
        System.out.println("\n" + AMARILLO + "--- Editar Artículo ---" + RESET);
        String nombreBuscado = Validador.solicitarObligatorio("Nombre del producto a editar");
        List<Productos> productos = Inventario.leerTodos();
        boolean encontrado = false;

        for (Productos p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombreBuscado)) {
                encontrado = true;
                System.out.println("Producto localizado. Ingrese los nuevos valores:");
                
                p.setPrecioCompra(solicitarFloatValido("Nuevo Precio de Compra"));
                p.setPrecioVenta(solicitarFloatValido("Nuevo Precio de Venta"));
                p.setStock(solicitarIntValido("Nuevo Stock"));
                break;
            }
        }

        if (encontrado) {
            Inventario.guardarTodos(productos);
            System.out.println("\n\t" + VERDE + "¡Producto modificado con éxito!" + RESET);
        } else {
            System.out.println("\n\t" + ROJO + "[Error] El artículo no existe." + RESET);
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n" + AMARILLO + "--- Eliminar Artículo ---" + RESET);
        String nombreBuscado = Validador.solicitarObligatorio("Nombre del producto a eliminar");
        List<Productos> productos = Inventario.leerTodos();
        
        boolean eliminado = productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombreBuscado));

        if (eliminado) {
            Inventario.guardarTodos(productos);
            System.out.println("\n\t" + VERDE + "¡Producto eliminado permanentemente!" + RESET);
        } else {
            System.out.println("\n\t" + ROJO + "[Error] No se encontró el producto." + RESET);
        }
    }

    private static float solicitarFloatValido(String campo) {
        while (true) {
            String entrada = Validador.solicitarObligatorio(campo);
            if (Validador.esDecimal(entrada) || Validador.esEntero(entrada)) {
                float valor = Validador.convertirAFloat(entrada);
                if (valor >= 0) return valor;
            }
            System.out.println("\n\t" + ROJO + "[Error] Tipo de dato incorrecto numérico." + RESET);
        }
    }

    private static int solicitarIntValido(String campo) {
        while (true) {
            String entrada = Validador.solicitarObligatorio(campo);
            if (Validador.esEntero(entrada)) {
                int valor = Validador.convertirAInt(entrada);
                if (valor >= 0) return valor;
            }
            System.out.println("\n\t" + ROJO + "[Error] Tipo de dato incorrecto (Debe ser entero)." + RESET);
        }
    }
}
