package com.fittestapp.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("forgot-password")
@PageTitle("Olvidé mi contraseña | FittestApp")
@AnonymousAllowed
public class ForgotPasswordView extends VerticalLayout {

    public ForgotPasswordView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout card = new VerticalLayout();
        card.setAlignItems(Alignment.CENTER);
        card.setWidth("400px");
        card.getStyle()
                .set("background", "var(--lumo-base-color)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("box-shadow", "var(--lumo-box-shadow-m)")
                .set("padding", "var(--lumo-space-xl)");

        H2 titulo = new H2("Olvidé mi contraseña");

        Paragraph mensaje = new Paragraph("Esta funcionalidad estará disponible próximamente.");
        mensaje.getStyle().set("color", "var(--lumo-secondary-text-color)").set("text-align", "center");

        Button btnVolver = new Button("Volver al login", VaadinIcon.ARROW_LEFT.create(),
                e -> UI.getCurrent().navigate(LoginView.class));
        btnVolver.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        card.add(titulo, mensaje, btnVolver);
        add(card);
    }
}
