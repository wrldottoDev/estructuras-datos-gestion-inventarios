import java.util.ArrayList;

public class Producto {
    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento;
    private int cantidad;
    private ArrayList<String> listaImagenes;

    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<String>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    public void agregarImagen(String ruta) {
        listaImagenes.add(ruta);
    }

    public double calcularCostoTotal() {
        return precio * cantidad;
    }

    public String toString() {
        String vence = fechaVencimiento.isEmpty() ? "No aplica" : fechaVencimiento;
        return "Producto: " + nombre + "\nPrecio: " + precio + "\nCategoria: " + categoria
                + "\nVencimiento: " + vence + "\nCantidad: " + cantidad
                + "\nImagenes: " + listaImagenes;
    }
}
