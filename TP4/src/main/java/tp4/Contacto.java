package tp4;

public class Contacto {

    private String nombre;
    private String telefono;
    private String email;
    private String notaPrivada;

    public Contacto(String nombre,
                    String telefono,
                    String email,
                    String notaPrivada) {

        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.notaPrivada = notaPrivada;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getNotaPrivada() {
        return notaPrivada;
    }
}