package com.fittestapp.views;

import com.fittestapp.model.Rol;
import com.fittestapp.model.Usuario;
import com.fittestapp.views.form.FormView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulario ABM para la entidad Usuario.
 * Alta  : Código(auto), Nombre, Apellido, Email, Contraseña, DNI, Fecha Nac., Rol
 * Edición: Código(read-only), Nombre, Apellido, Email(read-only), DNI, Fecha Nac., Rol
 */
public class UsuarioFormView extends FormView<Usuario> {

    @Override
    protected void construirCampos(Usuario usuario, boolean esNuevo) {

        TextField tfCodigo = new TextField("Código");
        tfCodigo.setValue(esNuevo ? "Auto" : String.valueOf(usuario.getCodigo()));
        tfCodigo.setReadOnly(true);

        TextField tfNombre = new TextField("Nombre");
        tfNombre.setValue(usuario.getNombre() != null ? usuario.getNombre() : "");
        tfNombre.setRequired(true);
        tfNombre.addValueChangeListener(e -> usuario.setNombre(e.getValue()));

        TextField tfApellido = new TextField("Apellido");
        tfApellido.setValue(usuario.getApellido() != null ? usuario.getApellido() : "");
        tfApellido.setRequired(true);
        tfApellido.addValueChangeListener(e -> usuario.setApellido(e.getValue()));

        EmailField tfEmail = new EmailField("Email");
        tfEmail.setValue(usuario.getEmail() != null ? usuario.getEmail() : "");
        tfEmail.setRequired(true);
        tfEmail.setReadOnly(!esNuevo);
        if (esNuevo) {
            tfEmail.addValueChangeListener(e -> usuario.setEmail(e.getValue()));
        }

        TextField tfDni = new TextField("DNI");
        tfDni.setValue(usuario.getDni() != null ? usuario.getDni() : "");
        tfDni.addValueChangeListener(e -> usuario.setDni(e.getValue()));

        DatePicker dpFechaNac = new DatePicker("Fecha de Nacimiento");
        if (usuario.getFechaNacimiento() != null) {
            dpFechaNac.setValue(usuario.getFechaNacimiento());
        }
        dpFechaNac.addValueChangeListener(e -> usuario.setFechaNacimiento(e.getValue()));

        ComboBox<Rol> cbRol = new ComboBox<>("Rol");
        cbRol.setItems(Rol.values());
        cbRol.setItemLabelGenerator(Rol::name);
        cbRol.setRequired(true);
        if (usuario.getRol() != null) cbRol.setValue(usuario.getRol());
        cbRol.addValueChangeListener(e -> usuario.setRol(e.getValue()));

        getCamposLayout().add(tfCodigo, tfNombre, tfApellido, tfEmail, tfDni, dpFechaNac, cbRol);
    }
}
