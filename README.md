# Conversor de Monedas - Java 17

## Descripción

Este proyecto es un **Conversor de Monedas** desarrollado en Java, diseñado para obtener tasas de cambio en tiempo real y realizar conversiones de divisas a través de una interfaz de consola. Utiliza la **Exchange Rate API** para asegurar que las tasas sean precisas y estén actualizadas.

El objetivo principal de este proyecto es:

- Aprender a consumir y procesar datos de una API RESTful.
- Manejar respuestas en formato JSON utilizando la librería **Gson**.
- Desarrollar una aplicación de consola modular y fácil de usar.
- Aplicar principios de diseño de software como la separación de responsabilidades.

Resuelve el problema de la conversión manual de monedas, ofreciendo una herramienta práctica y automática para obtener tasas actualizadas y realizar cálculos de forma sencilla.

## Características

- **Conversión de Divisas:** Realiza conversiones entre diferentes monedas basándose en tasas de cambio en tiempo real.
- **Selección Flexible de Divisas:** Permite al usuario especificar tanto la moneda base como la moneda destino.
- **Obtención Dinámica de Tasas de Cambio:** Las tasas se obtienen en tiempo real desde la Exchange Rate API.
- **Menú Interactivo:** Ofrece opciones para:
  - Realizar conversiones.
  - Visualizar tasas de cambio disponibles para una moneda base (por defecto USD).
  - Ver un historial de conversiones realizadas.
- **Validación de Entradas:** Manejo robusto de entradas inválidas (texto, códigos incorrectos, etc.).
- **Bucle de Ejecución:** Permite múltiples operaciones hasta que el usuario decida salir.

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación.
- **Maven / Gradle**: Sistema de construcción (se asume Maven para ejecución).
- **Google Gson**: Librería para manejar JSON.
- **Java HttpClient (java.net.http)**: Para peticiones HTTP.

## Estructura del Proyecto

```text
src/main/java/com/alura/conversorMonedas/
├── Main.java                   # Punto de entrada principal
├── ConversorDeMonedasApp.java # Lógica principal y flujo de ejecución
├── GenerarAPI.java            # Comunicación con la API y parseo JSON
├── MenuConversor.java         # Visualización de menús
└── MonedaApiResponse.java     # Modelo para respuesta JSON
```
# Cómo Ejecutar el Proyecto
## Clonar el Repositorio


```bash
git clone https://github.com/dabo32/conversor-monedas-challenge-aura.git
cd conversor-monedas-challenge-aura
```

## Obtener una API Key
Regístrate en Exchange Rate API para obtener tu propia API Key gratuita.

⚠️ Importante: Reemplaza {ApiKey} en GenerarAPI.java con tu propia API Key.

## Compilar y Ejecutar (con Maven)

Asegúrate de tener Maven instalado.
Navega a la raíz del proyecto (donde está pom.xml).
Ejecuta:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.alura.conversorMonedas.Main"
También puedes ejecutar el proyecto desde tu IDE favorito (IntelliJ, Eclipse, VS Code).
```

# Posibles Mejoras Futuras
- Persistencia del Historial: Guardar conversiones en archivo (CSV, JSON) o base de datos local.
- Validación Más Robusta: Usar expresiones regulares para validar códigos de moneda.
- Interfaz Gráfica (GUI): Crear una interfaz con Swing o JavaFX.
- Manejo de Errores: Clases de excepción personalizadas para distintos errores.
- Internacionalización: Soporte multilenguaje para la aplicación.

Autor |
David Santiago Bohorquez Gonzalez |
GitHub: dabo32
