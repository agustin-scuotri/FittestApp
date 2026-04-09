package com.fittestapp.views;

import com.fittestapp.model.Gimnasio;
import com.fittestapp.views.form.FormView;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulario ABM para la entidad Gimnasio.
 * Campos: Nombre, Dirección.
 */
public class GimnasioFormView extends FormView<Gimnasio> {

    @Override
    protected void construirCampos(Gimnasio gimnasio, boolean esNuevo) {

        TextField tfNombre = new TextField("Nombre");
        tfNombre.setValue(gimnasio.getNombre() != null ? gimnasio.getNombre() : "");
        tfNombre.setRequired(true);
        tfNombre.addValueChangeListener(e -> gimnasio.setNombre(e.getValue()));

        TextField tfDireccion = new TextField("Dirección");
        tfDireccion.setValue(gimnasio.getDireccion() != null ? gimnasio.getDireccion() : "");
        tfDireccion.setRequired(true);
        tfDireccion.addValueChangeListener(e -> gimnasio.setDireccion(e.getValue()));

        getCamposLayout().add(tfNombre, tfDireccion);
    }
}
