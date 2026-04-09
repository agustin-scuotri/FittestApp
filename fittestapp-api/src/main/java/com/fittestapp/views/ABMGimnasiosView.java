package com.fittestapp.views;

import com.fittestapp.model.Gimnasio;
import com.fittestapp.service.GimnasioService;
import com.fittestapp.views.abm.AbmView;
import com.fittestapp.views.abm.DescriptorColumna;
import com.fittestapp.views.form.FormView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = "admin/gimnasios", layout = AdminLayout.class)
@PageTitle("ABM Gimnasios | FittestApp")
@RolesAllowed("ADMIN")
public class ABMGimnasiosView extends AbmView<Gimnasio> {

    private final GimnasioService gimnasioService;

    public ABMGimnasiosView(GimnasioService gimnasioService) {
        this.gimnasioService = gimnasioService;
    }

    @Override
    protected String getTitulo() { return "Gimnasios"; }

    @Override
    protected List<DescriptorColumna<Gimnasio>> configurarColumnas() {
        return List.of(
                DescriptorColumna.<Gimnasio>of("Nombre",    g -> g.getNombre()).autoAncho(),
                DescriptorColumna.<Gimnasio>of("Dirección", g -> g.getDireccion()).autoAncho()
        );
    }

    @Override
    protected FormView<Gimnasio> construirFormulario(Gimnasio g) {
        return new GimnasioFormView();
    }

    @Override
    protected Gimnasio crearNuevoItem() { return new Gimnasio(); }

    @Override
    protected void onGuardar(Gimnasio g) {
        gimnasioService.guardar(g);
    }

    @Override
    protected void onEliminar(Gimnasio g) {
        gimnasioService.eliminar(g.getId());
    }

    @Override
    protected List<Gimnasio> cargarItems() { return gimnasioService.listarTodos(); }
}
