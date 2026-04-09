package com.fittestapp.views.abm;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Descriptor declarativo para un botón extra en la barra de herramientas ABM.
 * Uso: DescriptorBoton.of("Exportar", VaadinIcon.DOWNLOAD, e -> exportar())
 *                      .variante(ButtonVariant.LUMO_CONTRAST)
 */
public class DescriptorBoton {

    private final String etiqueta;
    private final VaadinIcon icono;
    private final ComponentEventListener<ClickEvent<Button>> accion;
    private ButtonVariant variante = ButtonVariant.LUMO_TERTIARY;

    private DescriptorBoton(String etiqueta, VaadinIcon icono,
                             ComponentEventListener<ClickEvent<Button>> accion) {
        this.etiqueta = etiqueta;
        this.icono = icono;
        this.accion = accion;
    }

    public static DescriptorBoton of(String etiqueta, VaadinIcon icono,
                                     ComponentEventListener<ClickEvent<Button>> accion) {
        return new DescriptorBoton(etiqueta, icono, accion);
    }

    public DescriptorBoton variante(ButtonVariant variante) {
        this.variante = variante;
        return this;
    }

    /** Construye el componente Button listo para agregar al layout. */
    public Button construir() {
        Button btn = new Button(etiqueta, icono.create(), accion);
        btn.addThemeVariants(variante);
        return btn;
    }
}
