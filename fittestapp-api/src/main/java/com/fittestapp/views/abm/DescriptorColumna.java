package com.fittestapp.views.abm;

import com.vaadin.flow.function.ValueProvider;

/**
 * Descriptor declarativo para una columna de la grilla ABM.
 * Uso: DescriptorColumna.of("Email", u -> u.getEmail()).autoAncho()
 */
public class DescriptorColumna<T> {

    private final String encabezado;
    private final ValueProvider<T, String> proveedor;
    private String ancho;
    private boolean ordenable = true;
    private boolean autoAncho = false;

    private DescriptorColumna(String encabezado, ValueProvider<T, String> proveedor) {
        this.encabezado = encabezado;
        this.proveedor = proveedor;
    }

    public static <T> DescriptorColumna<T> of(String encabezado, ValueProvider<T, String> proveedor) {
        return new DescriptorColumna<>(encabezado, proveedor);
    }

    public DescriptorColumna<T> ancho(String ancho) {
        this.ancho = ancho;
        return this;
    }

    public DescriptorColumna<T> autoAncho() {
        this.autoAncho = true;
        return this;
    }

    public DescriptorColumna<T> noOrdenable() {
        this.ordenable = false;
        return this;
    }

    // --- Getters ---

    public String getEncabezado() { return encabezado; }
    public ValueProvider<T, String> getProveedor() { return proveedor; }
    public String getAncho() { return ancho; }
    public boolean isOrdenable() { return ordenable; }
    public boolean isAutoAncho() { return autoAncho; }
}
