package com.fittestapp.service;

import com.fittestapp.model.Gimnasio;
import com.fittestapp.repository.GimnasioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GimnasioService {

    private final GimnasioRepository gimnasioRepository;

    public List<Gimnasio> listarTodos() {
        return gimnasioRepository.findAll();
    }

    @Transactional
    public Gimnasio guardar(Gimnasio gimnasio) {
        return gimnasioRepository.save(gimnasio);
    }

    @Transactional
    public void eliminar(Long id) {
        gimnasioRepository.deleteById(id);
    }
}
