package com.andres.inventario.configs;

import com.andres.inventario.dtos.MovimientoDTO;
import com.andres.inventario.dtos.ProductoDTO;
import com.andres.inventario.entities.MovimientoInventario;
import com.andres.inventario.entities.Producto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // STRICT solo mapea campos con el mismo nombre; los datos de las relaciones se declaran abajo.
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(Producto.class, ProductoDTO.class).addMappings(m -> {
            m.map(src -> src.getCategoria().getId(), ProductoDTO::setCategoriaId);
            m.map(src -> src.getCategoria().getNombre(), ProductoDTO::setCategoriaNombre);
            m.map(src -> src.getProveedor().getId(), ProductoDTO::setProveedorId);
            m.map(src -> src.getProveedor().getRazonSocial(), ProductoDTO::setProveedorRazonSocial);
        });

        modelMapper.typeMap(MovimientoInventario.class, MovimientoDTO.class).addMappings(m -> {
            m.map(src -> src.getProducto().getId(), MovimientoDTO::setProductoId);
            m.map(src -> src.getProducto().getSku(), MovimientoDTO::setProductoSku);
            m.map(src -> src.getProducto().getNombre(), MovimientoDTO::setProductoNombre);
        });

        return modelMapper;
    }
}
