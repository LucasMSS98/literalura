package com.alura.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConsumoApi {

    public String obterDados(String busca) {
        String buscaFromatado = URLEncoder.encode(busca, StandardCharsets.UTF_8);
        String endereco = "https://gutendex.com/books?search="+buscaFromatado;

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Status de resposta: "+ response.statusCode());
            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.body());
                return jsonNode.toPrettyString();
            }else {
                return "Erro: Recebeu Status " + response.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao obter dados da API: " + e.getMessage());
            throw new RuntimeException("Falha na requisição", e);
        }
    }
}
