import java.util.ArrayList;
import java.util.Locale;

/**
 * Producto de la tienda. Se usa tanto en el inventario (ArbolProductos) como
 * en el carrito de cada cliente (ListaProductos).
 * Valida sus datos: el nombre y la categoría no pueden estar vacíos, el precio
 * debe ser mayor que cero y la cantidad no puede ser negativa.
 */
public final class Producto {

    // Atributos.
    private String nombre;
    private double precio;
    private String categoria;
    private String fechaVencimiento; // vacío si el producto no vence
    private int cantidad;
    private final ArrayList<String> listaImagenes;

    // Constructor.
    /**
     * @throws IllegalArgumentException si algún dato no es válido.
     */
    public Producto(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad) {
        setNombre(nombre);
        setPrecio(precio);
        setCategoria(categoria);
        setFechaVencimiento(fechaVencimiento);
        setCantidad(cantidad);
        this.listaImagenes = new ArrayList<String>();
    }

    // Getters.
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    // Setters (con validación).
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacio.");
        }
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        // La forma !(precio > 0) también rechaza NaN.
        if (!(precio > 0) || Double.isInfinite(precio)) {
            throw new IllegalArgumentException("El precio debe ser un numero mayor que cero.");
        }
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoria no puede estar vacia.");
        }
        this.categoria = categoria.trim();
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = (fechaVencimiento == null) ? "" : fechaVencimiento.trim();
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.cantidad = cantidad;
    }

    // Otros métodos.
    /** Agrega la ruta de una imagen asociada al producto. Complejidad: O(1). */
    public void agregarImagen(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta de la imagen no puede estar vacia.");
        }
        listaImagenes.add(ruta.trim());
    }

    /** Costo de las unidades del producto (precio por cantidad). Complejidad: O(1). */
    public double calcularCostoTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        String vence = fechaVencimiento.isEmpty() ? "No aplica" : fechaVencimiento;
        return "Producto: " + nombre
                + "\nPrecio: " + String.format(Locale.US, "%.2f", precio)
                + "\nCategoria: " + categoria
                + "\nVencimiento: " + vence
                + "\nCantidad: " + cantidad
                + "\nImagenes: " + listaImagenes;
    }
}
