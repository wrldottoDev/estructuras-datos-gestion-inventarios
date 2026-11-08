/**
 * Tienda: administra el inventario (ArbolProductos) y la cola de clientes
 * pendientes de atención (ColaClientes).
 */
public class Tienda {

    // Atributos.
    private final ArbolProductos inventario;
    private final ColaClientes cola;

    // Constructor.
    public Tienda() {
        inventario = new ArbolProductos();
        cola = new ColaClientes();
    }

    // Getters.
    public ArbolProductos getInventario() {
        return inventario;
    }

    public ColaClientes getCola() {
        return cola;
    }

    // Operaciones sobre el inventario.
    /** Agrega un producto al inventario. false si el nombre ya existe. */
    public boolean agregarProducto(Producto producto) {
        return inventario.insertar(producto);
    }

    public Producto buscarProducto(String nombre) {
        return inventario.buscar(nombre);
    }

    /** Elimina un producto del inventario y lo retorna (null si no existía). */
    public Producto eliminarProducto(String nombre) {
        return inventario.eliminar(nombre);
    }

    // Operaciones sobre los clientes.
    /**
     * Mueve unidades del inventario al carrito del cliente: las agrega al
     * carrito y las descuenta del stock.
     * @return true si había stock suficiente y se agregó; false en otro caso.
     */
    public boolean agregarAlCarrito(Cliente cliente, String nombreProducto, int cantidad) {
        Producto producto = inventario.buscar(nombreProducto);
        if (producto == null || cantidad <= 0 || cantidad > producto.getCantidad()) {
            return false;
        }
        cliente.agregarAlCarrito(producto, cantidad);
        producto.setCantidad(producto.getCantidad() - cantidad);
        return true;
    }

    /** Inserta un cliente (con su carrito ya lleno) en la cola de atención. */
    public void registrarCliente(Cliente cliente) {
        cola.insertar(cliente);
    }

    /**
     * Saca de la cola al cliente que corresponde atender según la prioridad.
     * La factura la imprime quien llama (ver Main).
     * @return el cliente atendido, o null si no hay clientes en la cola.
     */
    public Cliente atenderSiguiente() {
        return cola.atender();
    }
}
