package com.challenge.literalura.principal;

import com.challenge.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final LibroService libroService;

    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    @Override
    public void run(String... args) {

        Scanner teclado = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 6) {

            System.out.println("""
                1. Buscar libro por título
                2. Listar libros registrados
                3. Listar autores registrados
                4. Listar autores vivos en determinado año
                5. Listar libros por idioma
                6. Salir
            """);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Debes ingresar un número válido.");
                opcion = -1;
                continue;
            }

            switch (opcion) {
                case 1 :
                    System.out.println("Ingrese título:");
                    String titulo = teclado.nextLine();
                    libroService.buscarYGuardar(titulo);
                    break;
                case 2 :
                    libroService.listarLibros();
                    break;
                case 3 :
                    libroService.listarAutores();
                    break;
                case 4 :
                    System.out.println("Ingrese año:");
                    //int anio = teclado.nextInt();
                    int anio = Integer.parseInt(teclado.nextLine());
                    libroService.autoresVivosEn(anio);
                    break;
                case 5 :
                    System.out.println("Ingrese idioma:");
                    System.out.println(
                            """
                                        en - Ingles
                                        es - Español
                                        fr - Frances
                                        ch - Chino
                                    """
                    );
                    String idioma = teclado.nextLine();
                    libroService.librosPorIdioma(idioma);

                    break;
                case 6 :
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}