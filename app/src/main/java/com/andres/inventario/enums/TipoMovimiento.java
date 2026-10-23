package com.andres.inventario.enums;

public enum TipoMovimiento {
    // Suma la cantidad al stock (compra, devolución de cliente).
    ENTRADA,
    // Resta la cantidad del stock (venta, merma).
    SALIDA,
    // Fija el stock al conteo físico indicado en cantidad.
    AJUSTE
}
