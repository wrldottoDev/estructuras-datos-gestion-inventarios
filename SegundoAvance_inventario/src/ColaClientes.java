/**
 * Cola de prioridad de clientes, implementada con nodos enlazados.
 * Los clientes entran siempre por el final. Al atender, sale el cliente con
 * la prioridad más alta; si hay empate, el que esté más cerca del frente
 * (el que llegó primero).
 * En las complejidades, n es la cantidad de clientes en la cola.
 */
public class ColaClientes {

    // Atributos.
    private NodoCliente frente;
    private NodoCliente fin;

    // Constructor.
    public ColaClientes() {
        frente = null;
        fin = null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    /** Inserta un cliente al final de la cola. Complejidad: O(1). */
    public void insertar(Cliente cliente) {
        NodoCliente nuevo = new NodoCliente(cliente);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
    }

    /**
     * Consulta quién sería atendido, sin sacarlo de la cola. Complejidad: O(n).
     * @return el cliente prioritario, o null si la cola está vacía.
     */
    public Cliente verSiguiente() {
        NodoCliente prioritario = buscarPrioritario();
        return (prioritario == null) ? null : prioritario.getCliente();
    }

    /**
     * Retira y retorna al cliente que corresponde atender. Complejidad: O(n),
     * porque hay que recorrer la cola para encontrar la prioridad más alta.
     * @return el cliente atendido, o null si la cola está vacía.
     */
    public Cliente atender() {
        NodoCliente prioritario = buscarPrioritario();
        if (prioritario == null) {
            return null;
        }
        if (prioritario == frente) {
            frente = frente.getSiguiente();
            if (frente == null) {
                fin = null; // la cola quedó vacía
            }
        } else {
            NodoCliente anterior = frente;
            while (anterior.getSiguiente() != prioritario) {
                anterior = anterior.getSiguiente();
            }
            anterior.setSiguiente(prioritario.getSiguiente()); // se salta al atendido
            if (prioritario == fin) {
                fin = anterior;
            }
        }
        return prioritario.getCliente();
    }

    // Recorre desde el frente; solo reemplaza al candidato si la prioridad es
    // estrictamente mayor, así en un empate gana el más cercano al frente.
    private NodoCliente buscarPrioritario() {
        if (estaVacia()) {
            return null;
        }
        NodoCliente mejor = frente;
        NodoCliente nodoTemp = frente.getSiguiente();
        while (nodoTemp != null) {
            if (nodoTemp.getCliente().getPrioridad() > mejor.getCliente().getPrioridad()) {
                mejor = nodoTemp;
            }
            nodoTemp = nodoTemp.getSiguiente();
        }
        return mejor;
    }

    /**
     * Muestra los clientes en orden de llegada y marca a quién se atenderá
     * primero. Complejidad: O(n).
     */
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("No hay clientes en la cola.");
            return;
        }
        NodoCliente siguiente = buscarPrioritario();
        int posicion = 1;
        NodoCliente nodoTemp = frente;
        while (nodoTemp != null) {
            String marca = (nodoTemp == siguiente) ? "   <-- siguiente en ser atendido" : "";
            System.out.println(posicion + ". " + nodoTemp.getCliente() + marca);
            posicion++;
            nodoTemp = nodoTemp.getSiguiente();
        }
    }
}
