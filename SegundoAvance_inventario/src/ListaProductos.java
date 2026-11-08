import java.util.Locale;

/**
 * Lista enlazada simple de productos. Cada Cliente la usa como su carrito.
 * Su único atributo es la referencia al primer nodo. Los nombres de producto se
 * comparan sin distinguir mayúsculas.
 * En las complejidades, n es la cantidad de productos que hay en la lista.
 */
public class ListaProductos {

    // Atributos.
    private NodoProducto inicio;

    // Constructor.
    public ListaProductos() {
        inicio = null;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    /** Inserta un producto al inicio de la lista. Complejidad: O(1). */
    public void insertarAlInicio(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        nuevo.setSiguiente(inicio);
        inicio = nuevo;
    }

    /** Inserta un producto al final de la lista. Complejidad: O(n). */
    public void insertarAlFinal(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        if (estaVacia()) {
            inicio = nuevo;
            return;
        }
        NodoProducto nodoTemp = inicio;
        while (nodoTemp.getSiguiente() != null) {
            nodoTemp = nodoTemp.getSiguiente();
        }
        nodoTemp.setSiguiente(nuevo);
    }

    /**
     * Busca un producto por nombre.
     * Complejidad: O(n) en el peor caso (no está o es el último).
     * @return el producto, o null si no existe.
     */
    public Producto buscar(String nombre) {
        NodoProducto nodoTemp = inicio;
        while (nodoTemp != null) {
            if (nodoTemp.getProducto().getNombre().equalsIgnoreCase(nombre)) {
                return nodoTemp.getProducto();
            }
            nodoTemp = nodoTemp.getSiguiente();
        }
        return null;
    }

    /**
     * Modifica los datos de un producto de la lista. Los datos nuevos se validan
     * antes de tocar el producto, así que si alguno es inválido no se cambia nada.
     * Complejidad: O(n).
     * @return true si se modificó; false si el producto no existe.
     * @throws IllegalArgumentException si algún dato nuevo no es válido.
     */
    public boolean modificar(String nombre, String nuevoNombre, double precio, String categoria,
            String fechaVencimiento, int cantidad) {
        Producto producto = buscar(nombre);
        if (producto == null) {
            return false;
        }
        new Producto(nuevoNombre, precio, categoria, fechaVencimiento, cantidad); // solo valida
        producto.setNombre(nuevoNombre);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setFechaVencimiento(fechaVencimiento);
        producto.setCantidad(cantidad);
        return true;
    }

    /**
     * Elimina el producto con el nombre indicado y lo retorna para que quien
     * llama decida qué hacer con él. Complejidad: O(n).
     * @return el producto eliminado, o null si no existía.
     */
    public Producto eliminar(String nombre) {
        if (estaVacia()) {
            return null;
        }
        if (inicio.getProducto().getNombre().equalsIgnoreCase(nombre)) {
            Producto eliminado = inicio.getProducto();
            inicio = inicio.getSiguiente();
            return eliminado;
        }
        NodoProducto anterior = inicio;
        while (anterior.getSiguiente() != null) {
            NodoProducto actual = anterior.getSiguiente();
            if (actual.getProducto().getNombre().equalsIgnoreCase(nombre)) {
                anterior.setSiguiente(actual.getSiguiente()); // se salta el nodo eliminado
                return actual.getProducto();
            }
            anterior = actual;
        }
        return null;
    }

    /**
     * Agrega una imagen al producto con el nombre indicado. Complejidad: O(n).
     * @return true si se agregó; false si el producto no existe.
     */
    public boolean agregarImagen(String nombre, String ruta) {
        Producto producto = buscar(nombre);
        if (producto == null) {
            return false;
        }
        producto.agregarImagen(ruta);
        return true;
    }

    /** Muestra todos los productos de la lista. Complejidad: O(n). */
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("La lista esta vacia.");
            return;
        }
        NodoProducto nodoTemp = inicio;
        while (nodoTemp != null) {
            System.out.println(nodoTemp.getProducto());
            System.out.println("-----");
            nodoTemp = nodoTemp.getSiguiente();
        }
    }

    /** Suma el costo (precio por cantidad) de todos los productos. Complejidad: O(n). */
    public double calcularCostoTotal() {
        double total = 0;
        NodoProducto nodoTemp = inicio;
        while (nodoTemp != null) {
            total += nodoTemp.getProducto().calcularCostoTotal();
            nodoTemp = nodoTemp.getSiguiente();
        }
        return total;
    }

    /**
     * Imprime el costo de cada producto y el costo total acumulado de la lista.
     * Complejidad: O(n).
     */
    public void imprimirReporteCostos() {
        if (estaVacia()) {
            System.out.println("La lista esta vacia.");
            return;
        }
        NodoProducto nodoTemp = inicio;
        while (nodoTemp != null) {
            Producto p = nodoTemp.getProducto();
            System.out.println("Producto: " + p.getNombre());
            System.out.println("  Precio unitario: " + dinero(p.getPrecio()));
            System.out.println("  Cantidad:        " + p.getCantidad());
            System.out.println("  Subtotal:        " + dinero(p.calcularCostoTotal()));
            System.out.println();
            nodoTemp = nodoTemp.getSiguiente();
        }
        System.out.println("TOTAL ACUMULADO: " + dinero(calcularCostoTotal()));
    }

    // Da formato de dos decimales (con punto) a un monto.
    private String dinero(double monto) {
        return String.format(Locale.US, "%.2f", monto);
    }
}
