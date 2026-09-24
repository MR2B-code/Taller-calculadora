package calculadora;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Reto adicional (opcional) del Momento 5: las mismas pruebas de CalculadoraTest,

 */
@DisplayName("Pruebas de la clase Calculadora (organizadas con @Nested)")
class CalculadoraTestNested {

    private static final double DELTA = 0.0001;
    private Calculadora calc;

    @BeforeEach
    void setUp() {
        calc = new Calculadora();
    }

    @Nested
    @DisplayName("Pruebas de sumar")
    class PruebasSumar {
        @Test
        @DisplayName("Sumar dos números positivos")
        void sumarDosPositivos() {
            assertEquals(8, calc.sumar(5, 3), DELTA);
        }
    }

    @Nested
    @DisplayName("Pruebas de restar")
    class PruebasRestar {
        @Test
        @DisplayName("Restar - Casos mínimos")
        void restarCasos() {
            assertAll("Verificaciones del método restar",
                    () -> assertEquals(5, calc.restar(10, 5), DELTA),
                    () -> assertEquals(-5, calc.restar(5, 10), DELTA),
                    () -> assertEquals(8.5, calc.restar(8.5, 0), DELTA)
            );
        }
    }

    @Nested
    @DisplayName("Pruebas de multiplicar")
    class PruebasMultiplicar {
        @Test
        @DisplayName("Multiplicar - Casos mínimos")
        void multiplicarCasos() {
            assertAll("Verificaciones de multiplicación",
                    () -> assertEquals(6, calc.multiplicar(2, 3), DELTA),
                    () -> assertEquals(-6, calc.multiplicar(2, -3), DELTA),
                    () -> assertEquals(6, calc.multiplicar(-2, -3), DELTA),
                    () -> assertEquals(0, calc.multiplicar(5, 0), DELTA)
            );
        }
    }

    @Nested
    @DisplayName("Pruebas de dividir")
    class PruebasDividir {
        @Test
        @DisplayName("Dividir entre cero lanza ArithmeticException")
        void dividirEntreCero() {
            ArithmeticException ex = assertThrows(ArithmeticException.class,
                    () -> calc.dividir(10, 0));
            assertEquals("No se puede dividir entre cero", ex.getMessage());
        }

        @Test
        @DisplayName("Dividir - Casos mínimos restantes")
        void dividirOtrosCasos() {
            assertAll("Verificaciones adicionales de división",
                    () -> assertEquals(2.0, calc.dividir(10, 5), DELTA),
                    () -> assertEquals(3.5, calc.dividir(7, 2), DELTA),
                    () -> assertEquals(0.0, calc.dividir(0, 5), DELTA)
            );
        }
    }

    @Nested
    @DisplayName("Pruebas de potencia")
    class PruebasPotencia {
        @Test
        @DisplayName("Potencia - Casos mínimos")
        void potenciaCasos() {
            assertAll("Verificaciones de potencia",
                    () -> assertEquals(8, calc.potencia(2, 3), DELTA),
                    () -> assertEquals(1, calc.potencia(5, 0), DELTA),
                    () -> assertEquals(0.25, calc.potencia(2, -2), DELTA),
                    () -> assertEquals(-8, calc.potencia(-2, 3), DELTA),
                    () -> assertEquals(0, calc.potencia(0, 5), DELTA)
            );
        }
    }

    @Nested
    @DisplayName("Pruebas de raizCuadrada")
    class PruebasRaizCuadrada {
        @Test
        @DisplayName("Raíz Cuadrada - Casos válidos y error negativo")
        void raizCuadradaCasos() {
            assertAll("Verificaciones de raíz cuadrada",
                    () -> assertEquals(4, calc.raizCuadrada(16), DELTA),
                    () -> assertEquals(0, calc.raizCuadrada(0), DELTA),
                    () -> assertEquals(1.414213, calc.raizCuadrada(2), DELTA)
            );

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> calc.raizCuadrada(-4));
            assertEquals("No existe raíz cuadrada real de un número negativo", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas de esPar")
    class PruebasEsPar {
        @ParameterizedTest(name = "esPar({0}) = {1}")
        @CsvSource({
                "4, true",
                "7, false",
                "0, true",
                "-2, true",
                "-3, false"
        })
        @DisplayName("Verificar si un número es par (Prueba Parametrizada)")
        void esParCasos(int numero, boolean esperado) {
            assertEquals(esperado, calc.esPar(numero));
        }
    }

    @Nested
    @DisplayName("Pruebas de factorial")
    class PruebasFactorial {
        @Test
        @DisplayName("Factorial - Valores límites y excepciones")
        void factorialCasos() {
            assertAll("Verificaciones válidas de factorial",
                    () -> assertEquals(1, calc.factorial(0)),
                    () -> assertEquals(1, calc.factorial(1)),
                    () -> assertEquals(120, calc.factorial(5)),
                    () -> assertEquals(2432902008176640000L, calc.factorial(20))
            );

            IllegalArgumentException exNegativo = assertThrows(IllegalArgumentException.class,
                    () -> calc.factorial(-1));
            assertEquals("El factorial no está definido para negativos", exNegativo.getMessage());

            ArithmeticException exExcede = assertThrows(ArithmeticException.class,
                    () -> calc.factorial(21));
            assertEquals("El resultado supera la capacidad de un long", exExcede.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas de promedio")
    class PruebasPromedio {
        @Test
        @DisplayName("Promedio - Casos mínimos y excepciones")
        void promedioCasos() {
            assertAll("Verificaciones de promedios válidos",
                    () -> assertEquals(4.0, calc.promedio(new double[]{2, 4, 6}), DELTA),
                    () -> assertEquals(5.0, calc.promedio(new double[]{5}), DELTA),
                    () -> assertEquals(-2.0, calc.promedio(new double[]{-2, 4, -8}), DELTA)
            );

            IllegalArgumentException exVacio = assertThrows(IllegalArgumentException.class,
                    () -> calc.promedio(new double[]{}));
            assertEquals("Se requiere al menos un número", exVacio.getMessage());

            IllegalArgumentException exNulo = assertThrows(IllegalArgumentException.class,
                    () -> calc.promedio(null));
            assertEquals("Se requiere al menos un número", exNulo.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas de porcentaje")
    class PruebasPorcentaje {
        @Test
        @DisplayName("Porcentaje - el 10% de 200 es 20")
        void porcentajeCasoNormal() {
            assertEquals(20, calc.porcentaje(200, 10), DELTA);
        }

        @Test
        @DisplayName("Porcentaje - el 0% de cualquier valor es 0")
        void porcentajeCero() {
            assertEquals(0, calc.porcentaje(80, 0), DELTA);
        }

        @Test
        @DisplayName("Porcentaje - se permiten porcentajes mayores a 100")
        void porcentajeMayorA100() {
            assertEquals(120, calc.porcentaje(80, 150), DELTA);
        }

        @Test
        @DisplayName("Porcentaje - porcentaje negativo lanza IllegalArgumentException")
        void porcentajeNegativoLanzaExcepcion() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> calc.porcentaje(100, -10));
            assertEquals("El porcentaje no puede ser negativo", ex.getMessage());
        }
    }
}