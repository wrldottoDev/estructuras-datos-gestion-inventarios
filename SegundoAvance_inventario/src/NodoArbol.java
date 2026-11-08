/**
 * Nodo del árbol binario de búsqueda del inventario (ArbolProductos).
 * Guarda un Producto (su nombre es la llave) y las referencias a sus dos hijos.
 */
public class NodoArbol {

    // Atributos.
    private Producto producto;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    // Constructor.
    public NodoArbol(Producto producto) {
        this.producto = producto;
        this.izquierdo = null;
        this.derecho = null;
    }

    // Getters.
    public Producto getProducto() {
        return producto;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    // Setters.
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }
}
