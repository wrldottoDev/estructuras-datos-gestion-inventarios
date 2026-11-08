/**
 * Árbol binario de búsqueda que almacena el inventario de la tienda.
 * La llave de cada nodo es el nombre del Producto (sin distinguir mayúsculas):
 * los nombres menores van a la izquierda y los mayores a la derecha.
 * No se permiten nombres repetidos.
 *
 * Complejidad: sea h la altura del árbol. Insertar, buscar y eliminar son O(h).
 * Con productos insertados en un orden mezclado el árbol queda balanceado y
 * h es cercana a log n (caso esperado: O(log n)). Si se insertan en orden
 * alfabético el árbol se desbalancea hasta parecerse a una lista, h = n y el
 * peor caso es O(n).
 */
public class ArbolProductos {

    // Atributos.
    private NodoArbol raiz;

    // Constructor.
    public ArbolProductos() {
        raiz = null;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Inserta un producto en la posición que le corresponde según su nombre.
     * Complejidad: O(h).
     * @return true si se insertó; false si ya existía un producto con ese nombre.
     */
    public boolean insertar(Producto producto) {
        NodoArbol padre = null;
        NodoArbol actual = raiz;
        int comparacion = 0;

        // Se baja por el árbol hasta encontrar el hueco (null) donde va el nuevo nodo.
        while (actual != null) {
            comparacion = producto.getNombre().compareToIgnoreCase(actual.getProducto().getNombre());
            if (comparacion == 0) {
                return false; // la llave debe ser única
            }
            padre = actual;
            actual = (comparacion < 0) ? actual.getIzquierdo() : actual.getDerecho();
        }

        NodoArbol nuevo = new NodoArbol(producto);
        if (padre == null) {
            raiz = nuevo; // el árbol estaba vacío
        } else if (comparacion < 0) {
            padre.setIzquierdo(nuevo);
        } else {
            padre.setDerecho(nuevo);
        }
        return true;
    }

    /**
     * Busca un producto por nombre. Complejidad: O(h).
     * @return el producto, o null si no existe.
     */
    public Producto buscar(String nombre) {
        NodoArbol nodoTemp = raiz;
        while (nodoTemp != null) {
            int comparacion = nombre.compareToIgnoreCase(nodoTemp.getProducto().getNombre());
            if (comparacion == 0) {
                return nodoTemp.getProducto();
            }
            nodoTemp = (comparacion < 0) ? nodoTemp.getIzquierdo() : nodoTemp.getDerecho();
        }
        return null;
    }

    /**
     * Elimina el producto con el nombre indicado y lo retorna para que quien
     * llama decida qué hacer con él. Complejidad: O(h).
     * @return el producto eliminado, o null si no existía.
     */
    public Producto eliminar(String nombre) {
        Producto eliminado = buscar(nombre);
        if (eliminado != null) {
            raiz = eliminarNodo(raiz, nombre);
        }
        return eliminado;
    }

    // Elimina recursivamente y devuelve la nueva raíz del subárbol.
    private NodoArbol eliminarNodo(NodoArbol nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        int comparacion = nombre.compareToIgnoreCase(nodo.getProducto().getNombre());
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarNodo(nodo.getIzquierdo(), nombre));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarNodo(nodo.getDerecho(), nombre));
        } else {
            // Casos 1 y 2: sin hijos o con un solo hijo; el hijo (o null) toma su lugar.
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            // Caso 3: dos hijos. Toma el lugar su sucesor, que luego se quita de abajo.
            NodoArbol sucesor = obtenerSucesor(nodo);
            nodo.setProducto(sucesor.getProducto());
            nodo.setDerecho(eliminarNodo(nodo.getDerecho(), sucesor.getProducto().getNombre()));
        }
        return nodo;
    }

    // El sucesor es el nodo de llave menor dentro de la descendencia derecha:
    // un paso a la derecha y luego a la izquierda hasta que ya no se pueda.
    private NodoArbol obtenerSucesor(NodoArbol nodo) {
        NodoArbol sucesor = nodo.getDerecho();
        while (sucesor.getIzquierdo() != null) {
            sucesor = sucesor.getIzquierdo();
        }
        return sucesor;
    }

    /**
     * Muestra el inventario en recorrido en orden (alfabético por nombre).
     * Complejidad: O(n), con n la cantidad de productos.
     */
    public void mostrarInorden() {
        if (estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return;
        }
        enOrden(raiz);
    }

    // Recorre primero la descendencia izquierda, luego el nodo y por último la derecha.
    private void enOrden(NodoArbol nodo) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.getIzquierdo());
        System.out.println(nodo.getProducto());
        System.out.println("-----");
        enOrden(nodo.getDerecho());
    }
}
