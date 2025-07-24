package com.alura.conversorMonedas;

public class MenuConversor {

    /**
     * Muestra el menú principal de la aplicación con opciones numeradas.
     */
    public static void mostrarMenuPrincipal(){
        System.out.println("Seleccione una opción:");
        System.out.println("1. Realizar una conversión de moneda");
        System.out.println("2. Ver todas las tasas de conversión (basado en USD)");
        System.out.println("3. Ver historial de conversiones");
        System.out.println("9. SALIR");
        System.out.println("*********************************");
    }

    /**
     * Muestra un menú con las opciones predefinidas de monedas populares
     * y una instrucción para el usuario (usado en la opción 1 de conversión).
     */
    public static void mostrarMenuConversor(){
        System.out.println("\n--- Divisas Populares ---");
        System.out.println("* ARS - Peso argentino");
        System.out.println("* BOB - Boliviano boliviano");
        System.out.println("* BRL - Real brasileño");
        System.out.println("* CLP - Peso chileno");
        System.out.println("* COP - Peso colombiano");
        System.out.println("* USD - Dólar estadounidense");
    }

    /**
     * Visualiza todas las monedas disponibles y sus tasas de conversión
     * a partir de un objeto MonedaApiResponse.
     * @param apiResponse El objeto MonedaApiResponse que contiene las tasas de conversión.
     */
    public static void visualizarMonedas(MonedaApiResponse apiResponse){
        if (apiResponse != null && apiResponse.conversion_rates != null) {
            System.out.println("\n--- Tasas de conversión disponibles para " + apiResponse.base_code + " ---");
            //Itera sobre el mapa de tasas de conversión y las imprime
            apiResponse.conversion_rates.forEach((moneda, tasa) -> {
                System.out.printf("  %s: %.4f%n", moneda, tasa); //Formato para 4 decimales
            });
            System.out.println("-------------------------------------");
        } else {
            System.out.println("No se encontraron tasas de conversión para visualizar. Asegúrese de haber realizado una llamada a la API exitosa.");
        }
    }
}
