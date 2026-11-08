/**
 * Cliente de la tienda. Tiene una prioridad de atención (1 = básico,
 * 2 = afiliado, 3 = premium) y un carrito, que es su propia ListaProductos.
 */
public class Cliente {

    // Atributos.
    private final String nombre;
    private final int prioridad;
    private final ListaProductos carrito;

    // Constructor.
    /**
     * @throws IllegalArgumentException si el nombre está vacío o la prioridad
     *         no está entre 1 y 3.
     */
    public Cliente(String nombre, int prioridad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio.");
        }
        if (prioridad < 1 || prioridad > 3) {
            throw new IllegalArgumentException("La prioridad debe ser 1, 2 o 3.");
        }
        this.nombre = nombre.trim();
        this.prioridad = prioridad;
        this.carrito = new ListaProductos();
    }

    // Getters.
    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    /** Nombre del tipo de cliente según su prioridad. */
    public String getTipo() {
        switch (prioridad) {
            case 3:
                return "Premium";
            case 2:
                return "Afiliado";
            default:
                return "Basico";
        }
    }

    /**
     * Agrega al carrito una copia del producto del inventario con la cantidad
     * pedida (el inventario conserva su propio objeto y su stock).
     * Si el producto ya estaba en el carrito, solo suma la cantidad.
     * Complejidad: O(m), con m la cantidad de productos del carrito.
     * @throws IllegalArgumentException si la cantidad no es mayor que cero.
     */
    public void agregarAlCarrito(Producto deInventario, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        Producto enCarrito = carrito.buscar(deInventario.getNombre());
        if (enCarrito != null) {
            enCarrito.setCantidad(enCarrito.getCantidad() + cantidad);
            return;
        }
        carrito.insertarAlFinal(new Producto(deInventario.getNombre(), deInventario.getPrecio(),
                deInventario.getCategoria(), deInventario.getFechaVencimiento(), cantidad));
    }

    @Override
    public String toString() {
        return nombre + " (" + getTipo() + ", prioridad " + prioridad + ")";
    }
}
