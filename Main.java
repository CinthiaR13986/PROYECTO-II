package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        LogicaProducto.adjuntarProductos();
        LogicaCliente.cargarClientes();
        Scanner scanner = new Scanner(System.in);
        Integer opcion = 0;
        do {
            System.out.println("1. Seleccione un producto");
            System.out.println("2. Elegir productos");
            System.out.println("3. Ver las compras realizadas");
            System.out.println("4. Agregar un producto");
            System.out.println();
            System.out.println("- Ingrese 0 para salir");


            opcion = scanner.nextInt();
            switch (opcion) {
                case 1: {
                    Cliente cliente = new Cliente();
                    System.out.println("Ingrese su nit");
                    cliente.setNitCliente(scanner.next());
                    System.out.println("Ingrese su nombre");
                    cliente.setNombreCliente(scanner.next());
                    System.out.println("Ingrese su celular");
                    cliente.setCelular(scanner.next());
                    System.out.println("Ingrese su direccion");
                    cliente.setDireccion(scanner.next());
                   

                    Pedido pedido = new Pedido();
                    Random random = new Random();
                    pedido.setPedidoId((random.nextInt(1000)));
                    pedido.setFechaOrden(new Date());
                    pedido.modificarEstado("Generado");

                    while (true) {
                        LogicaProducto.getProductos().forEach(producto -> {
                            System.out.println("Id: " + producto.getProductoId() + " Nombre: " + producto.getNombre() + " Estado: " + producto.getEstado());
                        });
                        scanner.nextLine();

                        System.out.println("Ingrese id del producto que desea comprar");
                        Integer idProducto = scanner.nextInt();
                        System.out.println("Ingrese la cantidad que desea comprar");
                        Integer cantidad = scanner.nextInt();

                        Producto producto = LogicaProducto.despacharProducto(idProducto, cantidad);
                        pedido.agregarProductoAOrden(producto);

                        System.out.println("Desea seguir agregando productos? (si/no)");
                        String respuesta = scanner.next();
                        if ("no".equalsIgnoreCase(respuesta)) {
                            break;
                        }
                    }
                    cliente.setPedidosLista(List.of(pedido));
                    System.out.println("Pedido realizado");
                    LogicaCliente.agregarCliente(cliente);
                    LogicaCliente.guardarClientes();
                    LogicaProducto.salvarProductos();
                    break;
                }
                case 2: {
                    LogicaProducto.getProductos().forEach(producto -> System.out.println("Id del producto: " + producto.getProductoId() + " Nombre: " + producto.getNombre() + " stock:" + producto.getStock() + " Estado: " + producto.getEstado()));
                    System.out.println();
                    break;
                }
                case 3: {
                    LogicaCliente.getClientes().forEach(cliente -> {
                        System.out.println("Cliente: " + cliente.getNombreCliente());
                        System.out.println("Nit: " + cliente.getNitCliente());
                        System.out.println("Direccion: " + cliente.getDireccion());
                  
                        System.out.println("Pedidos: ");
                        cliente.getPedidosLista().forEach(pedido -> {
                            System.out.println("    Id Pedido:" + pedido.getPedidoId());
                            System.out.println("    Estado: " + pedido.getEstado());
                            System.out.println("    Fecha de la venta: " + pedido.getFechaOrden());
                            List<Producto> productos = pedido.getProductos();
                            if (productos != null) {
                                for (Producto producto : productos) {
                                    System.out.println("        Producto: " + producto.getNombre());
                                    System.out.println("        Cantidad: " + producto.getStock());
                                    System.out.println("        Id del producto: " + producto.getProductoId());
                                    System.out.println();
                                }
                            }
                            System.out.println();
                        });
                    });
                    break;
                }
                case 4: {
                    Producto producto = new Producto();
                    Random random = new Random();
                    producto.setProductoId(random.nextInt(1000));
                    System.out.println("Ingrese el nombre del producto");
                    producto.setNombre(scanner.next());
                    System.out.println("Ingrese el stock del producto");
                    producto.setStock(scanner.nextInt());
                    if (producto.getStock() > 0) {
                        producto.setEstado("Existencia");
                    } else {
                        producto.setEstado("Agotado");
                    }

                    LogicaProducto.agregarProducto(producto);
                    LogicaProducto.salvarProductos();
                    break;
                }
                default:
                    System.out.println("Opcion no valida");
            }
        } while (opcion != 0);
        scanner.close();
    }

}