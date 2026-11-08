import java.io.File;
import java.util.Locale;
import java.util.Scanner;

/**
 * Clase principal: contiene el menú de consola y la rutina main().
 * El menú permite insertar productos en el inventario de la Tienda, registrar
 * clientes (llenando su carrito desde el inventario) y atender al siguiente
 * cliente imprimiendo su factura. Además permite consultar y administrar el
 * inventario.
 */
public class Main {

    // Atributos.
    private static final Scanner scanner = new Scanner(System.in);
    private static final Tienda tienda = new Tienda();
    private static final String CARPETA_IMAGENES = "imagenes/";

    /** Punto de entrada del programa: solo lanza el menú. */
    public static void main(String[] args) {
        menu();
    }

    // ---------- Menú ----------

    /** Muestra el menú en bucle hasta que el usuario elige salir. */
    static void menu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.println("===== TIENDA - GESTION DE INVENTARIO =====");
            System.out.println(" 1. Agregar producto al inventario");
            System.out.println(" 2. Registrar cliente y llenar su carrito");
            System.out.println(" 3. Atender al siguiente cliente (factura)");
            System.out.println(" 4. Mostrar inventario");
            System.out.println(" 5. Ver clientes en espera");
            System.out.println(" 6. Buscar producto");
            System.out.println(" 7. Eliminar producto del inventario");
            System.out.println(" 8. Agregar imagen a un producto");
            System.out.println(" 0. Salir");
            opcion = leerEntero("Opcion: ");
            System.out.println();
            ejecutar(opcion);
        }
    }

    /** Llama a la operación que corresponde a la opción elegida. */
    static void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                agregarProducto();
                break;
            case 2:
                registrarCliente();
                break;
            case 3:
                atenderCliente();
                break;
            case 4:
                tienda.getInventario().mostrarInorden();
                break;
            case 5:
                tienda.getCola().mostrar();
                break;
            case 6:
                buscarProducto();
                break;
            case 7:
                eliminarProducto();
                break;
            case 8:
                agregarImagen();
                break;
            case 0:
                System.out.println("Hasta luego.");
                break;
            default:
                System.out.println("Opcion invalida. Elija un numero del menu.");
        }
    }


    /** Opción 1: pide los datos de un producto y lo inserta en el inventario. */
    static void agregarProducto() {
        Producto producto = leerProducto();
        if (tienda.agregarProducto(producto)) {
            System.out.println("Producto agregado al inventario.");
        } else {
            System.out.println("Ya existe un producto con ese nombre; no se agrego.");
        }
    }

    /**
     * Opción 2: crea el cliente, llena su carrito desde el inventario y lo
     * inserta en la cola. Un cliente con el carrito vacío no se registra.
     */
    static void registrarCliente() {
        if (tienda.getInventario().estaVacio()) {
            System.out.println("El inventario esta vacio. Agregue productos primero.");
            return;
        }
        String nombre = leerTextoNoVacio("Nombre del cliente: ");
        int prioridad = leerPrioridad();
        Cliente cliente = new Cliente(nombre, prioridad);

        System.out.println();
        System.out.println("Inventario disponible:");
        tienda.getInventario().mostrarInorden();
        System.out.println("Llene el carrito (deje el nombre vacio para terminar).");

        while (true) {
            String nombreProducto = leerTexto("Producto a comprar: ");
            if (nombreProducto.isEmpty()) {
                break;
            }
            Producto producto = tienda.buscarProducto(nombreProducto);
            if (producto == null) {
                System.out.println("Ese producto no esta en el inventario.");
                continue;
            }
            if (producto.getCantidad() == 0) {
                System.out.println("No hay existencias de " + producto.getNombre() + ".");
                continue;
            }
            int cantidad = leerEntero("Cantidad (disponibles: " + producto.getCantidad() + "): ");
            if (tienda.agregarAlCarrito(cliente, nombreProducto, cantidad)) {
                System.out.println("Agregado al carrito. Total hasta ahora: "
                        + dinero(cliente.getCarrito().calcularCostoTotal()));
            } else {
                System.out.println("Cantidad invalida o mayor a las existencias.");
            }
        }

        if (cliente.getCarrito().estaVacia()) {
            System.out.println("El carrito esta vacio; el cliente no fue registrado.");
            return;
        }
        tienda.registrarCliente(cliente);
        System.out.println("Cliente registrado en la cola: " + cliente);
    }

    /** Opción 3: atiende al cliente de mayor prioridad e imprime su factura. */
    static void atenderCliente() {
        Cliente cliente = tienda.atenderSiguiente();
        if (cliente == null) {
            System.out.println("No hay clientes en espera.");
            return;
        }
        imprimirFactura(cliente);
    }

    /** Imprime la factura de un cliente: sus datos, su carrito y el total acumulado. */
    static void imprimirFactura(Cliente cliente) {
        System.out.println("========== FACTURA ==========");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Tipo:    " + cliente.getTipo() + " (prioridad " + cliente.getPrioridad() + ")");
        System.out.println("-----------------------------");
        cliente.getCarrito().imprimirReporteCostos();
        System.out.println("=============================");
    }

    /** Opción 6: busca un producto por nombre y lo muestra. */
    static void buscarProducto() {
        Producto producto = tienda.buscarProducto(leerTexto("Nombre a buscar: "));
        if (producto == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println(producto);
        }
    }

    /** Opción 7: elimina un producto del inventario. */
    static void eliminarProducto() {
        Producto eliminado = tienda.eliminarProducto(leerTexto("Nombre del producto a eliminar: "));
        if (eliminado == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println("Producto eliminado: " + eliminado.getNombre());
        }
    }

    /** Opción 8: asocia a un producto una imagen que exista en la carpeta imagenes/. */
    static void agregarImagen() {
        Producto producto = tienda.buscarProducto(leerTexto("Nombre del producto: "));
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        String archivo = leerTextoNoVacio("Nombre del archivo dentro de " + CARPETA_IMAGENES + " (ej: ejemplo.png): ");
        String ruta = CARPETA_IMAGENES + archivo;
        if (!new File(ruta).isFile()) {
            System.out.println("No existe el archivo " + ruta);
            return;
        }
        producto.agregarImagen(ruta);
        System.out.println("Imagen agregada.");
    }

    // ---------- Lectura de datos (todas validan y repiten hasta recibir un dato válido) ----------

    /** Pide todos los datos de un producto, validando cada uno. */
    static Producto leerProducto() {
        String nombre = leerTextoNoVacio("Nombre: ");
        double precio = leerDecimalPositivo("Precio: ");
        String categoria = leerTextoNoVacio("Categoria: ");
        String fecha = leerTexto("Fecha de vencimiento (enter si no aplica): ");
        int cantidad = leerEnteroNoNegativo("Cantidad: ");
        return new Producto(nombre, precio, categoria, fecha, cantidad);
    }

    static int leerPrioridad() {
        while (true) {
            System.out.println("Tipo de cliente: 1 = Basico, 2 = Afiliado, 3 = Premium");
            int prioridad = leerEntero("Prioridad: ");
            if (prioridad >= 1 && prioridad <= 3) {
                return prioridad;
            }
            System.out.println("La prioridad debe ser 1, 2 o 3.");
        }
    }

    /** Lee una línea sin espacios sobrantes. Si se acaba la entrada, termina el programa. */
    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    static String leerTextoNoVacio(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este dato es obligatorio.");
        }
    }

    static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    static int leerEnteroNoNegativo(String mensaje) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero >= 0) {
                return numero;
            }
            System.out.println("El numero no puede ser negativo.");
        }
    }

    static double leerDecimalPositivo(String mensaje) {
        while (true) {
            try {
                double numero = Double.parseDouble(leerTexto(mensaje));
                if (numero > 0 && !Double.isInfinite(numero)) {
                    return numero;
                }
                System.out.println("El valor debe ser mayor que cero.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido (use punto para los decimales).");
            }
        }
    }

    /** Da formato de dos decimales (con punto) a un monto. */
    static String dinero(double monto) {
        return String.format(Locale.US, "%.2f", monto);
    }
}
