package tp4;

import java.util.Scanner;

public class Menú {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Service agenda =
                new Service();

        int opcion;

        do {

            System.out.println(
                    ANSI.AZUL +
                    "\n===== AGENDA SEGURA ====="
                    + ANSI.RESET);

            System.out.println("1- Agregar");
            System.out.println("2- Mostrar");
            System.out.println("3- Editar");
            System.out.println("4- Eliminar");
            System.out.println("0- Salir");

            opcion =
                    Integer.parseInt(
                            sc.nextLine());

            switch (opcion) {

                case 1:

                    System.out.print(
                            "Nombre: ");
                    String nombre =
                            sc.nextLine();

                    System.out.print(
                            "Telefono: ");
                    String tel =
                            sc.nextLine();

                    System.out.print(
                            "Email: ");
                    String email =
                            sc.nextLine();

                    System.out.print(
                            "Nota Privada: ");
                    String nota =
                            sc.nextLine();

                    agenda.agregar(
                            new Contacto(
                                    nombre,
                                    tel,
                                    email,
                                    nota));

                    System.out.println(
                            ANSI.VERDE +
                            "Contacto agregado"
                            + ANSI.RESET);

                    break;

                case 2:

                    System.out.println(
                             ANSI.CYAN +
                            "\nLISTA CONTACTOS"
                            + ANSI.RESET);

                    int i = 0;

                    for (Contacto c :
                            agenda.listar()) {

                        System.out.printf(
                                "%d | %s | %s | %s | %s%n",
                                i++,
                                c.getNombre(),
                                c.getTelefono(),
                                c.getEmail(),
                                c.getNotaPrivada());
                    }

                    break;

                case 3:

                    System.out.print(
                            "Indice: ");

                    int editar =
                            Integer.parseInt(
                                    sc.nextLine());

                    System.out.print(
                            "Nombre: ");
                    nombre =
                            sc.nextLine();

                    System.out.print(
                            "Telefono: ");
                    tel =
                            sc.nextLine();

                    System.out.print(
                            "Email: ");
                    email =
                            sc.nextLine();

                    System.out.print(
                            "Nota: ");
                    nota =
                            sc.nextLine();

                    agenda.editar(
                            editar,
                            new Contacto(
                                    nombre,
                                    tel,
                                    email,
                                    nota));

                    System.out.println(
                            ANSI.AMARILLO +
                            "Editado correctamente"
                            + ANSI.RESET);

                    break;

                case 4:

                    System.out.print(
                            "Indice: ");

                    int borrar =
                            Integer.parseInt(
                                    sc.nextLine());

                    agenda.eliminar(borrar);

                    System.out.println(
                            ANSI.ROJO +
                            "Contacto eliminado"
                            + ANSI.RESET);

                    break;
            }

        } while (opcion != 0);

        sc.close();
    }
}