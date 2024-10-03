package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LogicaProducto {
    private static List<Producto> productos = new ArrayList<>();
    private static Map<Integer, Integer> map = new HashMap<>();

    public static Producto despacharProducto(int idProducto, int cantidadRequerida)  {
        Producto producto = getProducto(idProducto);
        Integer cantidad = producto.getStock();

        if (cantidad < cantidadRequerida) {
            throw new IllegalArgumentException("No hay suficiente cantidad de producto id: " + idProducto + " nombre: " + producto.getNombre());
        }

        producto.setStock(cantidad - cantidadRequerida);

        if (producto.getStock() == 0) {
            producto.setEstado("Agotado");
        }
        return new Producto(producto.getProductoId(), producto.getNombre(), cantidadRequerida, producto.getEstado());
    }

    public static void salvarProductos() throws IOException {
        URL url = new URL("file:src/main/resources/producto.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(url.getPath()), productos);
    }

    public static void adjuntarProductos() throws IOException {
        URL url = new URL("file:src/main/resources/producto.json");

        ObjectMapper mapper = new ObjectMapper();
        Producto[] produtosTemp = mapper.readValue(new File(url.getPath()), Producto[].class);

        Integer index = 0;
        for (Producto producto : produtosTemp) {
            if (map.get(producto.getProductoId()) != null) {
                System.out.println("Producto con ID duplicado id: " + producto.getProductoId() + " nombre: " + producto.getNombre() + " se procede a ignorarlo.");
                continue;
            }
            map.put(producto.getProductoId(), index++);
            productos.add(producto);
        }
    }

    public static void agregarProducto(Producto producto) {
        System.out.println("Agregando producto id: " + producto.getProductoId() + " nombre: " + producto.getNombre());
        if (map.get(producto.getProductoId()) != null) {
            throw new IllegalArgumentException("Producto con ID duplicado id: " + producto.getProductoId() + " nombre: " + producto.getNombre());
        }
        productos.add(producto);
    }

    public static List<Producto> getProductos() {
        return productos;
    }

    private static Producto getProducto(int idProducto) {
        Integer productoId = map.get(idProducto);
        if (productoId == null) {
            throw new IllegalArgumentException("Producto no encontrado id: " + idProducto);
        }
        return productos.get(productoId);
    }
}
