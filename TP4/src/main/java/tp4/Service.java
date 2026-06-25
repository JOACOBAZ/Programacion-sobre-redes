package tp4;

import java.util.List;

public class Service {

    private List<Contacto> contactos;

    public Service() {

        contactos =
                Persistencia.cargar();
    }

    public void agregar(
            Contacto contacto) {

        contactos.add(contacto);

        Persistencia.guardar(contactos);
    }

    public void eliminar(int indice) {

        contactos.remove(indice);

        Persistencia.guardar(contactos);
    }

    public void editar(
            int indice,
            Contacto nuevo) {

        contactos.set(indice, nuevo);

        Persistencia.guardar(contactos);
    }

    public List<Contacto> listar() {

        return contactos;
    }
}
