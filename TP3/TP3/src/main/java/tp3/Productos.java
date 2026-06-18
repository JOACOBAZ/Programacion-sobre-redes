package tp3;

public class Productos {
    private String nombre;
    private float precioCompra;
    private float precioVenta;
    private int stock;

    public Productos(String nombre, float precioCompra, float precioVenta, int stock) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stock = stock;
    }


	public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public float getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(float precioCompra) { this.precioCompra = precioCompra; }
    public float getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(float precioVenta) { this.precioVenta = precioVenta; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return nombre + ";" + precioCompra + ";" + precioVenta + ";" + stock;
    }
}

