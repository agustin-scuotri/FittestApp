package com.fittestapp.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "alumno", layout = UserLayout.class)
@PageTitle("Alumno | FittestApp")
@RolesAllowed("ALUMNO")
public class AlumnoView extends VerticalLayout {

    public AlumnoView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        add(VaadinIcon.BOOK.create());
        add(new H2("Próximamente disponible"));
        add(new H3("Estamos trabajando en tu panel de Alumno."));
    }
}
