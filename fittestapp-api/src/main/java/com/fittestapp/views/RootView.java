package com.fittestapp.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("")
@AnonymousAllowed
public class RootView extends Div implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            event.forwardTo(LoginView.class);
            return;
        }

        String rol = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .findFirst().orElse("");

        switch (rol) {
            case "ADMIN"          -> event.forwardTo(InicioView.class);
            case "COACH"          -> event.forwardTo(CoachView.class);
            case "ALUMNO"         -> event.forwardTo(AlumnoView.class);
            case "NUTRICIONISTA"  -> event.forwardTo(NutricionistaView.class);
            default               -> event.forwardTo(LoginView.class);
        }
    }
}
