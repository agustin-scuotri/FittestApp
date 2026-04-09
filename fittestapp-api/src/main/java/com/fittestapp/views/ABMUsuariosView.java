package com.fittestapp.views;

import com.fittestapp.model.Usuario;
import com.fittestapp.service.UsuarioService;
import com.fittestapp.views.abm.AbmView;
import com.fittestapp.views.abm.DescriptorColumna;
import com.fittestapp.views.form.FormView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "admin/usuarios", layout = AdminLayout.class)
@PageTitle("ABM Usuarios | FittestApp")
@RolesAllowed("ADMIN")
public class ABMUsuariosView extends AbmView<Usuario> {

    private static final DateTimeFormatter FECHA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final UsuarioService usuarioService;

    public ABMUsuariosView(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    protected String getTitulo() { return "Usuarios"; }

    @Override
    protected List<DescriptorColumna<Usuario>> configurarColumnas() {
        return List.of(
                DescriptorColumna.<Usuario>of("Código",     u -> String.valueOf(u.getCodigo())).ancho("90px").noOrdenable(),
                DescriptorColumna.<Usuario>of("Nombre",     u -> u.getNombre()).autoAncho(),
                DescriptorColumna.<Usuario>of("Apellido",   u -> u.getApellido()).autoAncho(),
                DescriptorColumna.<Usuario>of("DNI",        u -> u.getDni() != null ? u.getDni() : "").ancho("120px"),
                DescriptorColumna.<Usuario>of("Fecha Nac.", u -> u.getFechaNacimiento() != null
                        ? u.getFechaNacimiento().format(FECHA_FMT) : "").ancho("120px"),
                DescriptorColumna.<Usuario>of("Rol",        u -> u.getRol().name()).ancho("130px")
        );
    }

    @Override
    protected FormView<Usuario> construirFormulario(Usuario u) {
        return new UsuarioFormView();
    }

    @Override
    protected Usuario crearNuevoItem() { return new Usuario(); }

    @Override
    protected void onGuardar(Usuario u) {
        if (u.getId() == null) {
            usuarioService.registrar(u);
        } else {
            usuarioService.actualizar(u);
        }
    }

    @Override
    protected void onEliminar(Usuario u) {
        usuarioService.eliminar(u.getId());
    }

    @Override
    protected List<Usuario> cargarItems() {
        return usuarioService.listarTodos();
    }
}
