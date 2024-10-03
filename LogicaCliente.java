package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicaCliente {
    private static List<Cliente> clientes = new ArrayList<>();
    private static Map<String, String> map = new HashMap<>();

    public static void cargarClientes() throws IOException {
        URL url = new URL("file:src/main/resources/cliente.json");

        ObjectMapper mapper = new ObjectMapper();
        Cliente[] clientesTmp = mapper.readValue(new File(url.getPath()), Cliente[].class);

        for (Cliente cliente : clientesTmp) {
            if (map.get(cliente.getNitCliente()) != null) {
                System.out.println("Cliente con ID duplicado nit: " + cliente.getNitCliente() + " nombre: " + cliente.getNombreCliente() + " se procede a ignorarlo.");
                continue;
            }
            map.put(cliente.getNitCliente(), cliente.getNombreCliente());
            clientes.add(cliente);
        }

        System.out.println("clientes cargados: " + clientes.size());
    }

    public static void guardarClientes() throws IOException {
        URL url = new URL("file:src/main/resources/cliente.json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(url.getPath()), clientes);
        System.out.println("clientes guardados: " + clientes.size());
    }

    public static void agregarCliente(Cliente cliente) {
        if (map.get(cliente.getNitCliente()) != null) {
            clientes.forEach(c -> {
                if (c.getNitCliente().equals(cliente.getNitCliente())) {
                    cliente.getPedidosLista().forEach(pedido -> c.getPedidosLista().add(pedido));
                }
            });
            return;
        }
        System.out.println("Agregando cliente id: " + cliente.getNitCliente() + " nombre: " + cliente.getNombreCliente());
        clientes.add(cliente);
    }

    public static List<Cliente> getClientes() {
        return clientes;
    }
}
