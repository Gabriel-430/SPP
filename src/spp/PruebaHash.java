package spp;

import spp.utilidades.EncriptadorContrasenia;

/*
 * Autor: Emiliano Morales
 * Fecha de creación: 16/06/2026
 * Descripción: Genera hashes BCrypt para pruebas y carga inicial de
 * usuarios del Sistema de Prácticas Profesionales.
 */
public class PruebaHash {

  public static void main(String[] args) {

    String contrasenia = "Practicante123*";

    String hash = EncriptadorContrasenia.generarHash(contrasenia);

    System.out.println("Contrasenia original: " + contrasenia);
    System.out.println("Hash generado:");
    System.out.println(hash);

    System.out.println();
    System.out.println("Verificacion:");

    boolean coincide = EncriptadorContrasenia.verificarContrasenia(
        contrasenia,
        hash);

    System.out.println("Coincide: " + coincide);
  }
}