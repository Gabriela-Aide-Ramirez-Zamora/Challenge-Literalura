package com.challenge.literalura.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.challenge.literalura.dto.GutendexResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GutendexResponseDTO buscarLibroPorTitulo(String titulo) {

        try {
            String url = "https://gutendex.com/books?search=" + titulo.replace(" ","%20");
            String json = restTemplate.getForObject(url, String.class);

            return objectMapper.readValue(json, GutendexResponseDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al consumir API", e);
        }
    }
}
