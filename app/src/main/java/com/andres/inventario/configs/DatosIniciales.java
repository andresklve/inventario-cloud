package com.andres.inventario.configs;

import com.andres.inventario.entities.Producto;
import com.andres.inventario.repositories.IProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DatosIniciales {
    @Bean
    CommandLineRunner cargarProductos(IProductoRepository repo) {
        return args -> {
            if (repo.count() > 0) {
                return;
            }
            repo.saveAll(List.of(
                    producto("Laptop Lenovo IdeaPad 3", 12, "2499.90"),
                    producto("Mouse inalámbrico Logitech M170", 45, "39.90"),
                    producto("Teclado mecánico Redragon K552", 20, "159.00"),
                    producto("Monitor Samsung 24\" Full HD", 15, "549.00"),
                    producto("Disco SSD Kingston 480GB", 30, "189.50"),
                    producto("Memoria RAM DDR4 8GB", 25, "129.00"),
                    producto("Audífonos Sony WH-CH520", 18, "229.00"),
                    producto("Webcam Logitech C920", 10, "349.00"),
                    producto("Router TP-Link Archer C6", 22, "169.90"),
                    producto("Cable HDMI 2m", 80, "19.90"),
                    producto("Hub USB 3.0 4 puertos", 35, "49.00"),
                    producto("Impresora Epson L3250", 7, "899.00"),
                    producto("Tinta Epson 544 negro", 60, "34.50"),
                    producto("Silla ergonómica de oficina", 9, "429.00"),
                    producto("Parlantes Genius SP-HF160", 0, null)
            ));
        };
    }

    private Producto producto(String nombre, int cantidad, String precio) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setCantidad(cantidad);
        p.setPrecio(precio == null ? null : new BigDecimal(precio));
        return p;
    }
}
