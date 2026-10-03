public class ListaProductos {
    private NodoProducto inicio;

    public ListaProductos() {
        inicio = null;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public void insertarAlInicio(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        nuevo.siguiente = inicio;
        inicio = nuevo;
    }

    public void insertarAlFinal(Producto producto) {
        NodoProducto nuevo = new NodoProducto(producto);
        if (inicio == null) {
            inicio = nuevo;
            return;
        }
        NodoProducto actual = inicio;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo;
    }

    public Producto buscar(String nombre) {
        NodoProducto actual = inicio;
        while (actual != null) {
            if (actual.producto.getNombre().equalsIgnoreCase(nombre)) {
                return actual.producto;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean modificar(String nombre, String nuevoNombre, double precio, String categoria,
            String fechaVencimiento, int cantidad) {
        Producto producto = buscar(nombre);
        if (producto == null) {
            return false;
        }
        producto.setNombre(nuevoNombre);
        producto.setPrecio(precio);
        producto.setCategoria(categoria);
        producto.setFechaVencimiento(fechaVencimiento);
        producto.setCantidad(cantidad);
        return true;
    }

    public boolean eliminar(String nombre) {
        if (inicio == null) {
            return false;
        }
        if (inicio.producto.getNombre().equalsIgnoreCase(nombre)) {
            inicio = inicio.siguiente;
            return true;
        }
        NodoProducto anterior = inicio;
        while (anterior.siguiente != null) {
            if (anterior.siguiente.producto.getNombre().equalsIgnoreCase(nombre)) {
                anterior.siguiente = anterior.siguiente.siguiente;
                return true;
            }
            anterior = anterior.siguiente;
        }
        return false;
    }

    public boolean agregarImagen(String nombre, String ruta) {
        Producto producto = buscar(nombre);
        if (producto == null) {
            return false;
        }
        producto.agregarImagen(ruta);
        return true;
    }

    public void mostrar() {
        if (inicio == null) {
            System.out.println("La lista esta vacia.");
            return;
        }
        NodoProducto actual = inicio;
        while (actual != null) {
            System.out.println(actual.producto);
            System.out.println("-----");
            actual = actual.siguiente;
        }
    }

    public void imprimirReporteCostos() {
        if (inicio == null) {
            System.out.println("La lista esta vacia.");
            return;
        }
        double costoLista = 0;
        NodoProducto actual = inicio;
        while (actual != null) {
            Producto p = actual.producto;
            double costoProducto = p.calcularCostoTotal();
            System.out.println("Producto: " + p.getNombre());
            System.out.println("Precio: " + p.getPrecio());
            System.out.println("Cantidad: " + p.getCantidad());
            System.out.println("Costo total: " + costoProducto);
            System.out.println();
            costoLista = costoLista + costoProducto;
            actual = actual.siguiente;
        }
        System.out.println("Costo total de la lista: " + costoLista);
    }
}
