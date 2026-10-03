import java.io.File;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ListaProductos lista = new ListaProductos();

    public static void main(String[] args) {
        menu();
    }

    static void menu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println();
            System.out.println("===== GESTION DE PRODUCTOS =====");
            System.out.println("1. Agregar producto al inicio");
            System.out.println("2. Agregar producto al final");
            System.out.println("3. Mostrar productos");
            System.out.println("4. Buscar producto");
            System.out.println("5. Modificar producto");
            System.out.println("6. Eliminar producto");
            System.out.println("7. Agregar imagen a producto");
            System.out.println("8. Mostrar reporte de costos");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");
            System.out.println();
            ejecutar(opcion);
        }
    }

    static void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                lista.insertarAlInicio(leerProducto());
                System.out.println("Producto agregado al inicio.");
                break;
            case 2:
                lista.insertarAlFinal(leerProducto());
                System.out.println("Producto agregado al final.");
                break;
            case 3:
                lista.mostrar();
                break;
            case 4:
                buscarProducto();
                break;
            case 5:
                modificarProducto();
                break;
            case 6:
                eliminarProducto();
                break;
            case 7:
                agregarImagen();
                break;
            case 8:
                lista.imprimirReporteCostos();
                break;
            case 0:
                System.out.println("Hasta luego.");
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    static Producto leerProducto() {
        String nombre = leerTexto("Nombre: ");
        double precio = leerDecimal("Precio: ");
        String categoria = leerTexto("Categoria: ");
        String fecha = leerTexto("Fecha de vencimiento (enter si no aplica): ");
        int cantidad = leerEntero("Cantidad: ");
        return new Producto(nombre, precio, categoria, fecha, cantidad);
    }

    static void buscarProducto() {
        Producto producto = lista.buscar(leerTexto("Nombre a buscar: "));
        if (producto == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println(producto);
        }
    }

    static void modificarProducto() {
        String nombre = leerTexto("Nombre del producto a modificar: ");
        if (lista.buscar(nombre) == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        System.out.println("Ingrese los nuevos datos:");
        Producto nuevo = leerProducto();
        lista.modificar(nombre, nuevo.getNombre(), nuevo.getPrecio(), nuevo.getCategoria(),
                nuevo.getFechaVencimiento(), nuevo.getCantidad());
        System.out.println("Producto modificado.");
    }

    static void eliminarProducto() {
        if (lista.eliminar(leerTexto("Nombre del producto a eliminar: "))) {
            System.out.println("Producto eliminado.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    static void agregarImagen() {
        String nombre = leerTexto("Nombre del producto: ");
        String archivo = leerTexto("Nombre del archivo dentro de imagenes/ (ej: arroz.png): ");
        String ruta = "imagenes/" + archivo;
        if (!new File(ruta).isFile()) {
            System.out.println("No existe el archivo " + ruta);
            return;
        }
        if (lista.agregarImagen(nombre, ruta)) {
            System.out.println("Imagen agregada.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        return scanner.nextLine().trim();
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

    static double leerDecimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
            }
        }
    }
}
