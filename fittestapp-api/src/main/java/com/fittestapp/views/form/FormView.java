package com.fittestapp.views.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class FormView<T> extends VerticalLayout {

    protected final FormLayout camposLayout = new FormLayout();

    private Runnable accionGuardar;
    private Runnable accionCancelar;

    protected FormView() {
        setPadding(false);
        setSpacing(false);
        setWidth("100%");
        getStyle().set("gap", "var(--lumo-space-m)");

        camposLayout.setWidthFull();
        camposLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",     1),
                new FormLayout.ResponsiveStep("480px", 2)
        );
        camposLayout.getStyle().set("row-gap", "0");
    }

    public final void inicializar(T item, boolean esNuevo) {
        removeAll();
        camposLayout.removeAll();
        construirCampos(item, esNuevo);

        Hr separador = new Hr();
        separador.getStyle().set("margin", "var(--lumo-space-s) 0 0 0");

        add(camposLayout, separador, construirBotones());
    }

    protected abstract void construirCampos(T item, boolean esNuevo);

    protected FormLayout getCamposLayout() {
        return camposLayout;
    }

    public void setAccionGuardar(Runnable accion)  { this.accionGuardar  = accion; }
    public void setAccionCancelar(Runnable accion) { this.accionCancelar = accion; }

    private HorizontalLayout construirBotones() {
        Button btnGuardar = new Button("Guardar", VaadinIcon.CHECK.create());
        btnGuardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnGuardar.getStyle().set("min-width", "120px");
        btnGuardar.addClickListener(e -> { if (accionGuardar != null) accionGuardar.run(); });

        Button btnCancelar = new Button("Cancelar", VaadinIcon.CLOSE.create());
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnCancelar.addClickListener(e -> { if (accionCancelar != null) accionCancelar.run(); });

        HorizontalLayout botones = new HorizontalLayout(btnCancelar, btnGuardar);
        botones.setJustifyContentMode(JustifyContentMode.END);
        botones.setWidthFull();
        botones.getStyle().set("padding-top", "var(--lumo-space-s)");
        return botones;
    }
}
