package com.alura.conversorMonedas;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GenerarAPI {

    public MonedaApiResponse obtenerTasas(String monedaBase) throws IOException, InterruptedException {
        //URL Base, actua con la respuesta del usuario
        //Ingresa tú APIKey
        String ApiKey = "ABC123";
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", ApiKey, monedaBase.toUpperCase());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        //Depuracion:
        //System.out.println("JSON recibido por GenerarAPI: " + json);

        Gson gson = new Gson();
        //Mapear el JSON directamente al objeto MonedaApiResponse y lo retorna.
        return gson.fromJson(json, MonedaApiResponse.class);
    }
}
