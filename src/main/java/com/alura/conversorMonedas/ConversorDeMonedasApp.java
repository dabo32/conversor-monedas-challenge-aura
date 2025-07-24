package com.alura.conversorMonedas;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List; // Importar List
import java.util.ArrayList; // Importar ArrayList

public class ConversorDeMonedasApp {
    private Scanner lectura;
    private GenerarAPI api;
    private List<String> historialConversiones; //Campo para almacenar el historial

    public ConversorDeMonedasApp() {
        this.lectura = new Scanner(System.in);
        this.api = new GenerarAPI();
        this.historialConversiones = new ArrayList<>(); //Inicializar el historial
    }

    public void ejecutar() {
        int opcionPrincipal; //Variable para la opción del menú principal

        System.out.println("******************************************");
        System.out.println("Bienvenido al Conversor de Monedas");
        System.out.println("******************************************");

        do {
            MenuConversor.mostrarMenuPrincipal(); //Muestra el menú principal (1. Conversión, 2. Ver Tasas, 9. Salir)

            try {
                System.out.print("Ingrese su opción: ");
                opcionPrincipal = lectura.nextInt();
                lectura.nextLine(); //Consumir el salto de línea pendiente después de nextInt()

                switch (opcionPrincipal) {
                    case 1: //Opción: Realizar una conversión de moneda
                        realizarConversion();
                        break;
                    case 2: //Opción: Ver todas las tasas de conversión (basado en USD)
                        verTodasLasTasas();
                        break;
                    case 3: //Opción: Ver historial de conversiones
                        mostrarHistorial();
                        break;
                    case 9: //Opción: Salir del programa
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, ingrese una opción del menú.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("¡Entrada inválida! Por favor, ingrese un número para seleccionar una opción del menú.");
                lectura.nextLine(); //Consumir la entrada incorrecta para evitar un bucle infinito
                opcionPrincipal = 0; //Reinicia la opción para que el bucle continúe
            } catch (IOException e) {
                System.out.println("Error de conexión o de entrada/salida al comunicarse con la API: " + e.getMessage());
                System.out.println("Asegúrese de tener conexión a internet.");
                opcionPrincipal = 0; //Reinicia la opción para que el bucle continúe
            } catch (InterruptedException e) {
                System.out.println("La conexión con la API fue interrumpida: " + e.getMessage());
                Thread.currentThread().interrupt(); //Restaurar el estado de interrupción
                opcionPrincipal = 0; //Reinicia la opción para que el bucle continúe
            } catch (Exception e) { //Captura cualquier otra excepción inesperada
                System.out.println("Ocurrió un error inesperado en el programa: " + e.getMessage());
                e.printStackTrace(); //Para ver el stack trace completo en caso de error inesperado
                opcionPrincipal = 0; //Reinicia la opción para que el bucle continúe
            }

            //No preguntar si desea continuar si la opción fue 9 (salir)
            if (opcionPrincipal != 9) {
                System.out.print("\n¿Desea volver al menú principal? (Sí/No): ");
                String respuesta = lectura.nextLine().trim().toLowerCase();
                if (!respuesta.equals("s") && !respuesta.equals("si")) {
                    opcionPrincipal = 9; //Si no desea continuar, establece la opción a 9 para salir del bucle
                }
            }

        } while (opcionPrincipal != 9); //El bucle continúa mientras la opción no sea 9

        System.out.println("\n******************************************");
        System.out.println("¡Gracias por usar el conversor!");
        System.out.println("******************************************");
        lectura.close(); //Siempre cierra el Scanner al finalizar el programa
    }

    /**
     * Lógica para realizar una conversión de moneda.
     */
    private void realizarConversion() throws IOException, InterruptedException {
        MenuConversor.mostrarMenuConversor(); //Muestra el menú de divisas populares

        System.out.print("Ingrese el código de la moneda base (ej. USD, EUR, COP): ");
        String opcionUsuario = lectura.nextLine().toUpperCase(); //Asegura mayúsculas

        MonedaApiResponse apiResponse = api.obtenerTasas(opcionUsuario);

        if (apiResponse != null && apiResponse.result.equals("success")) {
            //Visualiza todas las tasas de conversión disponibles usando MenuConversor
            MenuConversor.visualizarMonedas(apiResponse);

            //Lógica para pedir la moneda destino
            System.out.print("\nIngrese el código de la moneda a la que desea convertir (ej. JPY, BRL): ");
            String monedaDestino = lectura.nextLine().toUpperCase();

            //--- INICIO DEL BUCLE PARA REPETIR LA ENTRADA DE CANTIDAD ---
            double cantidadAConvertir = 0;
            boolean cantidadValida = false;
            while (!cantidadValida) {
                System.out.print("Ingrese la cantidad en " + apiResponse.base_code + " para convertir: ");
                try {
                    cantidadAConvertir = lectura.nextDouble();
                    cantidadValida = true; //Si se lee un double, la cantidad es válida
                } catch (InputMismatchException e) {
                    System.out.println("¡Entrada inválida! Por favor, ingrese un número válido para la cantidad.");
                    //No se establece cantidadValida a true, por lo que el bucle se repetirá
                } finally {
                    lectura.nextLine(); //Consumir el salto de línea pendiente, siempre
                }
            }
            //--- FIN DEL BUCLE PARA REPETIR LA ENTRADA DE CANTIDAD ---


            if (apiResponse.conversion_rates != null && apiResponse.conversion_rates.containsKey(monedaDestino)) {
                double tasaDestino = apiResponse.conversion_rates.get(monedaDestino);
                double resultado = cantidadAConvertir * tasaDestino;
                String registroConversion = String.format("%.2f %s equivale a %.2f %s", cantidadAConvertir, apiResponse.base_code, resultado, monedaDestino);
                System.out.println(registroConversion);
                historialConversiones.add(registroConversion); //Añadir al historial
            } else {
                System.out.println("Error: Moneda de destino '" + monedaDestino + "' no encontrada en las tasas disponibles para " + apiResponse.base_code + ".");
                System.out.println("Por favor, verifique el código de la moneda e intente de nuevo.");
            }

        } else if (apiResponse != null) {
            System.out.println("Error en la respuesta de la API para la moneda base '" + opcionUsuario + "'.");
            System.out.println("Mensaje de la API: " + apiResponse.result); //Muestra el mensaje de error de la API
            System.out.println("Asegúrese de que el código de la moneda base sea válido (ej. USD, EUR).");
        } else {
            System.out.println("No se pudo obtener la respuesta de la API. Verifique su conexión o intente más tarde.");
        }
    }

    /**
     * Lógica para ver todas las tasas de conversión (usando USD como base por defecto).
     */
    private void verTodasLasTasas() throws IOException, InterruptedException {
        System.out.println("\nObteniendo todas las tasas de conversión (base: USD)...");
        MonedaApiResponse apiResponse = api.obtenerTasas("USD"); //Llama a la API con USD como base por defecto

        if (apiResponse != null && apiResponse.result.equals("success")) {
            MenuConversor.visualizarMonedas(apiResponse);
        } else if (apiResponse != null) {
            System.out.println("Error al obtener las tasas completas: " + apiResponse.result);
        } else {
            System.out.println("No se pudo obtener las tasas completas. Verifique su conexión.");
        }
    }

    /**
     * Muestra el historial de conversiones realizadas durante la ejecución del programa.
     */
    private void mostrarHistorial() {
        System.out.println("\n--- Historial de Conversiones ---");
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas aún.");
        } else {
            for (int i = 0; i < historialConversiones.size(); i++) {
                System.out.println((i + 1) + ". " + historialConversiones.get(i));
            }
        }
        System.out.println("---------------------------------");
    }
}
