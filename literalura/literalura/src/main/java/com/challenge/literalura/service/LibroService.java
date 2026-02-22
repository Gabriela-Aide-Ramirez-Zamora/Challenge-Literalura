package com.challenge.literalura.service;

import com.challenge.literalura.dto.*;
import com.challenge.literalura.model.*;
import com.challenge.literalura.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final GutendexService gutendexService;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(GutendexService gutendexService,
                        LibroRepository libroRepository,
                        AutorRepository autorRepository) {
        this.gutendexService = gutendexService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarYGuardar(String titulo) {

        GutendexResponseDTO respuesta = gutendexService.buscarLibroPorTitulo(titulo);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        LibroDTO libroDTO = respuesta.resultados().get(0);
        AutorDTO autorDTO = libroDTO.autores().get(0);

        Autor autor = autorRepository.findByNombre(autorDTO.nombre())
                .orElseGet(() -> {
                    Autor nuevo = new Autor();
                    nuevo.setNombre(autorDTO.nombre());
                    nuevo.setNacimiento(autorDTO.nacimiento());
                    nuevo.setFallecimiento(autorDTO.fallecimiento());
                    return autorRepository.save(nuevo);
                });

        if (libroRepository.findByTitulo(libroDTO.titulo()).isPresent()) {
            System.out.println("El libro ya está registrado.");
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(libroDTO.titulo());
        libro.setIdioma(libroDTO.idiomas().get(0));
        libro.setNumeroDescargas(libroDTO.numeroDescargas());
        libro.setAutor(autor);

        libroRepository.save(libro);

        System.out.println("Libro guardado correctamente.");
    }

    public void listarLibros() {
        libroRepository.findAll().forEach(
                l -> System.out.println(l.getTitulo())
        );
    }

    public void listarAutores() {
        autorRepository.findAll().forEach(
                a -> System.out.println(a.getNombre())
        );
    }

    public void autoresVivosEn(int anio) {
        List<Autor> autores = autorRepository
                .findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(anio, anio);

        autores.forEach(a -> System.out.println(a.getNombre()));
    }

    public void librosPorIdioma(String idioma) {
        libroRepository.findByIdioma(idioma)
                .forEach(l -> System.out.println(l.getTitulo()));
    }
}