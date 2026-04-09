package com.fittestapp.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "coach", layout = UserLayout.class)
@PageTitle("Coach | FittestApp")
@RolesAllowed("COACH")
public class CoachView extends VerticalLayout {

    public CoachView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        add(VaadinIcon.TROPHY.create());
        add(new H2("Próximamente disponible"));
        add(new H3("Estamos trabajando en tu panel de Coach."));
    }
}
