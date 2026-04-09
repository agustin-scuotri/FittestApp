package com.fittestapp.views;

import com.fittestapp.service.UsuarioService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "admin/inicio", layout = AdminLayout.class)
@PageTitle("Inicio | FittestApp")
@RolesAllowed("ADMIN")
public class InicioView extends VerticalLayout {

    public InicioView(UsuarioService usuarioService) {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String saludo = usuarioService.buscarPorEmail(email)
                .map(u -> "Bienvenido a FittestApp, " + u.getNombre() + " " + u.getApellido() + "!")
                .orElse("Bienvenido a FittestApp");

        add(new H2(saludo));
    }
}
