package com.fittestapp.config;

import com.fittestapp.model.Rol;
import com.fittestapp.model.Usuario;
import com.fittestapp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Carga usuarios de prueba al iniciar la aplicación si no existen.
 * Contraseña de todos los usuarios de prueba: Temporal123
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {

    private static final String PASSWORD_PRUEBA = "Temporal123";

    private final UsuarioService usuarioService;

    @Override
    public void run(ApplicationArguments args) {
        crearSiNoExiste("admin@fittestapp.com",        "admin123",    "Admin",        "Sistema",       Rol.ADMIN);
        crearSiNoExiste("alumno@fittestapp.com",       PASSWORD_PRUEBA, "Alumno",     "Prueba",        Rol.ALUMNO);
        crearSiNoExiste("coach@fittestapp.com",        PASSWORD_PRUEBA, "Coach",      "Prueba",        Rol.COACH);
        crearSiNoExiste("nutricionista@fittestapp.com",PASSWORD_PRUEBA, "Nutricionista","Prueba",      Rol.NUTRICIONISTA);
    }

    private void crearSiNoExiste(String email, String password, String nombre, String apellido, Rol rol) {
        if (usuarioService.buscarPorEmail(email).isPresent()) {
            log.info("Usuario {} ya existe, se omite.", email);
            return;
        }
        Usuario usuario = Usuario.builder()
                .email(email)
                .password(password)
                .nombre(nombre)
                .apellido(apellido)
                .rol(rol)
                .activo(true)
                .build();
        usuarioService.registrar(usuario);
        log.info("Usuario de prueba creado: {} ({})", email, rol);
    }
}
