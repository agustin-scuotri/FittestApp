package com.fittestapp.views;

import com.fittestapp.model.Usuario;
import com.fittestapp.service.UsuarioService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("dashboard")
@PageTitle("Dashboard | FittestApp")
@PermitAll
@Slf4j
public class DashboardView extends VerticalLayout {

    public DashboardView(UsuarioService usuarioService) {
        setPadding(true);
        setSpacing(true);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        usuarioService.buscarPorEmail(email).ifPresentOrElse(
                usuario -> buildDashboard(usuario),
                () -> add(new H2("Bienvenido"))
        );
    }

    private void buildDashboard(Usuario usuario) {
        add(
                new H2("Bienvenido, " + usuario.getNombre() + " " + usuario.getApellido()),
                new Paragraph("Rol: " + usuario.getRol().name()),
                new Paragraph("Email: " + usuario.getEmail())
        );
    }
}
