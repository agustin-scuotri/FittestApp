package com.fittestapp.views;

import com.fittestapp.service.UsuarioService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * Layout base para todos los roles.
 * Subclases sobreescriben {@link #navItems()} para agregar ítems al drawer.
 * Si {@code navItems()} devuelve una lista vacía, no se muestra drawer ni toggle.
 */
public abstract class BaseLayout extends AppLayout {

    protected BaseLayout(UsuarioService usuarioService) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String nombreCompleto = usuarioService.buscarPorEmail(email)
                .map(u -> u.getNombre() + " " + u.getApellido())
                .orElse(email);

        List<SideNavItem> items = navItems();
        boolean tieneDrawer = !items.isEmpty();

        H3 titulo = new H3("FittestApp");

        Span spanUsuario = new Span("👤 " + nombreCompleto);
        spanUsuario.getStyle().set("font-size", "var(--lumo-font-size-s)");

        Button btnLogout = new Button("Cerrar sesión", VaadinIcon.SIGN_OUT.create(), e -> logout());
        btnLogout.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);

        HorizontalLayout derecha = new HorizontalLayout(spanUsuario, btnLogout);
        derecha.setAlignItems(FlexComponent.Alignment.CENTER);
        derecha.getStyle()
                .set("margin-left", "auto")
                .set("padding-right", "var(--lumo-space-m)");

        if (tieneDrawer) {
            DrawerToggle toggle = new DrawerToggle();
            addToNavbar(toggle, titulo, derecha);

            SideNav nav = new SideNav();
            items.forEach(nav::addItem);
            addToDrawer(nav);
            setPrimarySection(Section.DRAWER);
        } else {
            HorizontalLayout navbar = new HorizontalLayout(titulo, derecha);
            navbar.setWidthFull();
            navbar.setAlignItems(FlexComponent.Alignment.CENTER);
            navbar.getStyle().set("padding-left", "var(--lumo-space-m)");
            addToNavbar(navbar);
        }
    }

    /**
     * Retorna los ítems del menú lateral.
     * Lista vacía = sin drawer (layout de barra superior solamente).
     */
    protected List<SideNavItem> navItems() {
        return List.of();
    }

    private void logout() {
        SecurityContextHolder.clearContext();
        VaadinServletRequest.getCurrent().getHttpServletRequest().getSession().invalidate();
        UI ui = UI.getCurrent();
        if (ui != null) {
            ui.getPage().setLocation("/login");
        } else {
            VaadinSession session = VaadinSession.getCurrent();
            if (session != null) session.close();
        }
    }
}
