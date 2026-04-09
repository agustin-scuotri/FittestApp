package com.fittestapp.views;

import com.fittestapp.service.UsuarioService;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNavItem;

import java.util.List;

public class AdminLayout extends BaseLayout {

    public AdminLayout(UsuarioService usuarioService) {
        super(usuarioService);
    }

    @Override
    protected List<SideNavItem> navItems() {
        return List.of(
                new SideNavItem("Inicio",         InicioView.class,            VaadinIcon.HOME.create()),
                new SideNavItem("Usuarios",       ABMUsuariosView.class,       VaadinIcon.USERS.create()),
                new SideNavItem("Admins",         ABMAdminView.class,          VaadinIcon.LOCK.create()),
                new SideNavItem("Alumnos",        ABMAlumnosView.class,        VaadinIcon.BOOK.create()),
                new SideNavItem("Nutricionistas", ABMNutricionistasView.class, VaadinIcon.HEART.create()),
                new SideNavItem("Coaches",        ABMCoachesView.class,        VaadinIcon.TROPHY.create()),
                new SideNavItem("Gimnasios",      ABMGimnasiosView.class,      VaadinIcon.OFFICE.create())
        );
    }
}
