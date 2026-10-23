package com.andres.inventario.configs;

import com.andres.inventario.entities.Categoria;
import com.andres.inventario.entities.MovimientoInventario;
import com.andres.inventario.entities.Producto;
import com.andres.inventario.entities.Proveedor;
import com.andres.inventario.enums.TipoMovimiento;
import com.andres.inventario.enums.UnidadMedida;
import com.andres.inventario.repositories.ICategoriaRepository;
import com.andres.inventario.repositories.IProductoRepository;
import com.andres.inventario.repositories.IProveedorRepository;
import com.andres.inventario.servicesinterfaces.IMovimientoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

// Carga datos de demostración solo cuando la base está vacía.
@Configuration
public class DatosIniciales {
    @Bean
    CommandLineRunner cargarDatos(ICategoriaRepository cR, IProveedorRepository prR,
                                  IProductoRepository pR, IMovimientoService mS) {
        return args -> {
            if (cR.count() > 0 || pR.count() > 0) {
                return;
            }

            Categoria computo = cR.save(new Categoria("Cómputo", "Laptops, PCs y componentes internos"));
            Categoria perifericos = cR.save(new Categoria("Periféricos", "Teclados, mouse, webcams y audio"));
            Categoria monitores = cR.save(new Categoria("Monitores", "Pantallas y accesorios de video"));
            Categoria redes = cR.save(new Categoria("Redes", "Routers, switches y cableado"));
            Categoria impresion = cR.save(new Categoria("Impresión", "Impresoras y consumibles"));
            Categoria mobiliario = cR.save(new Categoria("Mobiliario", "Sillas y escritorios de oficina"));

            // Proveedores ficticios para la demo.
            Proveedor andina = prR.save(new Proveedor("20601234561", "Distribuidora TecnoAndina S.A.C.",
                    "Lucía Ramos", "01 4567890", "ventas@tecnoandina.example"));
            Proveedor limaTech = prR.save(new Proveedor("20509876542", "Importaciones Lima Tech E.I.R.L.",
                    "Jorge Salas", "987 654 321", "pedidos@limatech.example"));
            Proveedor ofiSur = prR.save(new Proveedor("20456789123", "Suministros OfiSur S.A.C.",
                    "Carmen Quispe", "054 223344", "contacto@ofisur.example"));

            // SKU, nombre, categoría, proveedor, unidad, compra, venta, stock mínimo, stock inicial, salidas.
            cargar(pR, mS, "LAP-LEN-IP3", "Laptop Lenovo IdeaPad 3", computo, andina, UnidadMedida.UNIDAD, "2050.00", "2499.90", 3, 12, 4);
            cargar(pR, mS, "SSD-KNG-480", "Disco SSD Kingston 480GB", computo, limaTech, UnidadMedida.UNIDAD, "135.00", "189.50", 10, 30, 8);
            cargar(pR, mS, "RAM-DDR4-8G", "Memoria RAM DDR4 8GB", computo, limaTech, UnidadMedida.UNIDAD, "92.00", "129.00", 10, 25, 21);
            cargar(pR, mS, "MOU-LOG-M170", "Mouse inalámbrico Logitech M170", perifericos, andina, UnidadMedida.UNIDAD, "24.00", "39.90", 15, 45, 12);
            cargar(pR, mS, "TEC-RED-K552", "Teclado mecánico Redragon K552", perifericos, limaTech, UnidadMedida.UNIDAD, "110.00", "159.00", 5, 20, 3);
            cargar(pR, mS, "AUD-SNY-CH520", "Audífonos Sony WH-CH520", perifericos, andina, UnidadMedida.UNIDAD, "165.00", "229.00", 5, 18, 15);
            cargar(pR, mS, "CAM-LOG-C920", "Webcam Logitech C920", perifericos, andina, UnidadMedida.UNIDAD, "255.00", "349.00", 4, 10, 10);
            cargar(pR, mS, "MON-SAM-24FHD", "Monitor Samsung 24\" Full HD", monitores, andina, UnidadMedida.UNIDAD, "420.00", "549.00", 4, 15, 5);
            cargar(pR, mS, "CAB-HDMI-2M", "Cable HDMI 2m", monitores, limaTech, UnidadMedida.UNIDAD, "8.50", "19.90", 20, 80, 26);
            cargar(pR, mS, "RTR-TPL-C6", "Router TP-Link Archer C6", redes, limaTech, UnidadMedida.UNIDAD, "120.00", "169.90", 5, 22, 6);
            cargar(pR, mS, "HUB-USB3-4P", "Hub USB 3.0 4 puertos", redes, limaTech, UnidadMedida.UNIDAD, "28.00", "49.00", 10, 35, 9);
            cargar(pR, mS, "CAB-UTP-C6", "Cable UTP Cat6 (bobina 305m)", redes, limaTech, UnidadMedida.CAJA, "310.00", "419.00", 2, 6, 5);
            cargar(pR, mS, "IMP-EPS-L3250", "Impresora Epson L3250", impresion, ofiSur, UnidadMedida.UNIDAD, "690.00", "899.00", 2, 7, 2);
            cargar(pR, mS, "TIN-EPS-544N", "Tinta Epson 544 negro", impresion, ofiSur, UnidadMedida.UNIDAD, "22.00", "34.50", 20, 60, 44);
            cargar(pR, mS, "PAP-A4-75G", "Papel bond A4 75g (paquete 500 hojas)", impresion, ofiSur, UnidadMedida.PAQUETE, "14.50", "21.90", 30, 120, 70);
            cargar(pR, mS, "SIL-ERG-OF01", "Silla ergonómica de oficina", mobiliario, ofiSur, UnidadMedida.UNIDAD, "310.00", "429.00", 3, 9, 2);
        };
    }

    private void cargar(IProductoRepository pR, IMovimientoService mS, String sku, String nombre,
                        Categoria categoria, Proveedor proveedor, UnidadMedida unidad, String compra,
                        String venta, int stockMinimo, int stockInicial, int salidas) {
        Producto p = new Producto();
        p.setSku(sku);
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setProveedor(proveedor);
        p.setUnidadMedida(unidad);
        p.setPrecioCompra(new BigDecimal(compra));
        p.setPrecioVenta(new BigDecimal(venta));
        p.setStockMinimo(stockMinimo);
        p = pR.save(p);

        // El stock se arma con movimientos para que el kardex cuadre desde el inicio.
        mS.registrar(p.getId(), new MovimientoInventario(TipoMovimiento.ENTRADA, stockInicial,
                "Inventario inicial", "INV-INICIAL"));
        if (salidas > 0) {
            mS.registrar(p.getId(), new MovimientoInventario(TipoMovimiento.SALIDA, salidas,
                    "Ventas de la semana", "BOL-DEMO"));
        }
    }
}
