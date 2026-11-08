/**
 * Nodo de la cola de clientes (ColaClientes).
 * Guarda un Cliente y la referencia al siguiente nodo de la cola.
 */
public class NodoCliente {

    // Atributos.
    private Cliente cliente;
    private NodoCliente siguiente;

    // Constructor.
    public NodoCliente(Cliente cliente) {
        this.cliente = cliente;
        this.siguiente = null;
    }

    // Getters.
    public Cliente getCliente() {
        return cliente;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    // Setters.
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }
}
