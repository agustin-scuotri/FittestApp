package com.fittestapp.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "nutricionista", layout = UserLayout.class)
@PageTitle("Nutricionista | FittestApp")
@RolesAllowed("NUTRICIONISTA")
public class NutricionistaView extends VerticalLayout {

    public NutricionistaView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        add(VaadinIcon.HEART.create());
        add(new H2("Próximamente disponible"));
        add(new H3("Estamos trabajando en tu panel de Nutricionista."));
    }
}
