public class NodoProducto {
    Producto producto;
    NodoProducto siguiente;

    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }
}
