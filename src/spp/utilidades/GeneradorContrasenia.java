package spp.utilidades;

import java.security.SecureRandom;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 17/06/2026
 * Descripción: Genera contraseñas aleatorias para usuarios del Sistema
 * de Prácticas Profesionales.
 */
public class GeneradorContrasenia {

    private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMEROS = "0123456789";
    private static final String SIMBOLOS = "*@$%";
    private static final int LONGITUD_CONTRASENIA = 10;
    private static final SecureRandom GENERADOR_ALEATORIO = new SecureRandom();

    private GeneradorContrasenia() {
    }

    public static String generarContrasenia() {
        String caracteresDisponibles = MAYUSCULAS + MINUSCULAS + NUMEROS
                + SIMBOLOS;

        StringBuilder contrasenia = new StringBuilder();

        contrasenia.append(obtenerCaracterAleatorio(MAYUSCULAS));
        contrasenia.append(obtenerCaracterAleatorio(MINUSCULAS));
        contrasenia.append(obtenerCaracterAleatorio(NUMEROS));
        contrasenia.append(obtenerCaracterAleatorio(SIMBOLOS));

        while (contrasenia.length() < LONGITUD_CONTRASENIA) {
            contrasenia.append(obtenerCaracterAleatorio(
                    caracteresDisponibles));
        }

        return mezclarCaracteres(contrasenia.toString());
    }

    private static char obtenerCaracterAleatorio(String caracteres) {
        int indice = GENERADOR_ALEATORIO.nextInt(caracteres.length());

        return caracteres.charAt(indice);
    }

    private static String mezclarCaracteres(String texto) {
        char[] caracteres = texto.toCharArray();

        for (int indice = caracteres.length - 1; indice > 0; indice--) {
            int posicionAleatoria = GENERADOR_ALEATORIO.nextInt(indice + 1);
            char temporal = caracteres[indice];

            caracteres[indice] = caracteres[posicionAleatoria];
            caracteres[posicionAleatoria] = temporal;
        }

        return new String(caracteres);
    }
}
