package com.fittestapp.views.abm;

import com.fittestapp.views.form.FormView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.PostConstruct;

import java.util.List;

public abstract class AbmView<T> extends VerticalLayout {

    protected Grid<T> grilla;

    private Button btnNuevo;
    private Button btnEditar;
    private Button btnEliminar;

    private T itemSeleccionado;

    @PostConstruct
    protected void init() {
        setSizeFull();
        setPadding(true);
        setSpacing(false);
        getStyle().set("gap", "var(--lumo-space-m)");

        add(construirHeader());
        add(construirGrilla());
        setFlexGrow(1, grilla);
        refrescarGrilla();
    }

    // -------------------------------------------------------------------------
    // Header y grilla
    // -------------------------------------------------------------------------

    private HorizontalLayout construirHeader() {
        H2 titulo = new H2(getTitulo());
        titulo.getStyle().set("margin", "0");

        btnNuevo = new Button("Nuevo", VaadinIcon.PLUS.create());
        btnNuevo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnNuevo.addClickListener(e -> abrirDialogo(crearNuevoItem(), true));

        btnEditar = new Button("Editar", VaadinIcon.PENCIL.create());
        btnEditar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnEditar.setEnabled(false);
        btnEditar.addClickListener(e -> { if (itemSeleccionado != null) abrirDialogo(itemSeleccionado, false); });

        btnEliminar = new Button("Eliminar", VaadinIcon.TRASH.create());
        btnEliminar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        btnEliminar.setEnabled(false);
        btnEliminar.addClickListener(e -> { if (itemSeleccionado != null) confirmarEliminar(itemSeleccionado); });

        HorizontalLayout acciones = new HorizontalLayout(btnEditar, btnEliminar, btnNuevo);
        botonesExtra().forEach(d -> acciones.add(d.construir()));
        acciones.setAlignItems(Alignment.CENTER);
        acciones.getStyle().set("gap", "var(--lumo-space-s)");

        HorizontalLayout header = new HorizontalLayout(titulo, acciones);
        header.setWidthFull();
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.getStyle()
                .set("padding", "var(--lumo-space-s) 0")
                .set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        return header;
    }

    private Grid<T> construirGrilla() {
        grilla = new Grid<>();
        grilla.setSizeFull();
        grilla.setSelectionMode(Grid.SelectionMode.SINGLE);
        grilla.addThemeVariants(
                GridVariant.LUMO_ROW_STRIPES,
                GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_COLUMN_BORDERS
        );
        grilla.setColumnReorderingAllowed(true);
        grilla.setMultiSort(true, Grid.MultiSortPriority.APPEND);

        configurarColumnas().forEach(descriptor -> {
            Grid.Column<T> col = grilla
                    .addColumn(descriptor.getProveedor())
                    .setHeader(descriptor.getEncabezado())
                    .setSortable(descriptor.isOrdenable())
                    .setResizable(true);

            if (descriptor.getAncho() != null) col.setWidth(descriptor.getAncho());
            if (descriptor.isAutoAncho())       col.setAutoWidth(true);
        });

        grilla.addSelectionListener(evento -> {
            itemSeleccionado = evento.getFirstSelectedItem().orElse(null);
            boolean haySeleccion = itemSeleccionado != null;
            btnEditar.setEnabled(haySeleccion);
            btnEliminar.setEnabled(haySeleccion);
        });

        // Doble click abre edición directamente
        grilla.addItemDoubleClickListener(e -> abrirDialogo(e.getItem(), false));

        return grilla;
    }

    // -------------------------------------------------------------------------
    // Diálogos
    // -------------------------------------------------------------------------

    private void abrirDialogo(T item, boolean esNuevo) {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle(esNuevo ? "Nuevo — " + getTitulo() : "Editar — " + getTitulo());
        dialogo.setCloseOnOutsideClick(false);
        dialogo.setWidth("560px");
        dialogo.getElement().getStyle().set("--vaadin-dialog-content-padding", "var(--lumo-space-l)");

        FormView<T> formulario = construirFormulario(item);
        formulario.setAccionGuardar(() -> {
            onGuardar(item);
            dialogo.close();
            refrescarGrilla();
        });
        formulario.setAccionCancelar(dialogo::close);
        formulario.inicializar(item, esNuevo);

        dialogo.add(formulario);
        dialogo.open();
    }

    private void confirmarEliminar(T item) {
        Dialog confirmacion = new Dialog();
        confirmacion.setWidth("400px");

        VerticalLayout contenido = new VerticalLayout();
        contenido.setSpacing(false);
        contenido.getStyle().set("gap", "var(--lumo-space-s)").set("padding", "0");

        Span icono = new Span(VaadinIcon.EXCLAMATION_CIRCLE.create());
        icono.getStyle().set("color", "var(--lumo-error-color)").set("font-size", "var(--lumo-icon-size-l)");

        Paragraph pregunta = new Paragraph("¿Está seguro que desea eliminar este registro?");
        pregunta.getStyle().set("margin", "0").set("color", "var(--lumo-secondary-text-color)");

        contenido.add(icono, new H2("Eliminar registro"), pregunta);
        confirmacion.add(contenido);

        Button btnConfirmar = new Button("Eliminar", e -> {
            onEliminar(item);
            confirmacion.close();
            grilla.deselectAll();
            refrescarGrilla();
        });
        btnConfirmar.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);

        Button btnCancelar = new Button("Cancelar", e -> confirmacion.close());
        btnCancelar.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        confirmacion.getFooter().add(btnCancelar, btnConfirmar);
        confirmacion.open();
    }

    // -------------------------------------------------------------------------
    // API pública
    // -------------------------------------------------------------------------

    public void refrescarGrilla() {
        grilla.setItems(cargarItems());
        itemSeleccionado = null;
        if (btnEditar   != null) btnEditar.setEnabled(false);
        if (btnEliminar != null) btnEliminar.setEnabled(false);
    }

    // -------------------------------------------------------------------------
    // Métodos abstractos
    // -------------------------------------------------------------------------

    protected abstract String getTitulo();
    protected abstract List<DescriptorColumna<T>> configurarColumnas();
    protected abstract FormView<T> construirFormulario(T item);
    protected abstract T crearNuevoItem();
    protected abstract void onGuardar(T item);
    protected abstract void onEliminar(T item);
    protected abstract List<T> cargarItems();

    protected List<DescriptorBoton> botonesExtra() {
        return List.of();
    }
}
