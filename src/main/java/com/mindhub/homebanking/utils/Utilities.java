package com.mindhub.homebanking.utils;
import com.mindhub.homebanking.models.Account;
public class Utilities {
    public static String numberAccountGenerator(Account account) {
        return "VIN - " + String.format("%08d", 99999-account.getId());
    }

// método 1 y 2 tiene el error de que al parsear a short este no alamacena los 0 a la izquierda,
// asi que aunque en un inicio usando el String.format los tienen al parsear a short ya no están.

    // método 1 cvv

//    public static short cvvGenerator() {
//        short cvv = (short) Math.floor(Math.random() * 999);
//        if (cvv < 100) {
//            cvv = Short.parseShort("0" + cvv);
//        }
//        if (cvv < 10) {
//            cvv = Short.parseShort("00" + cvv);
//        }
//        return cvv;
//    }

    // método 2 cvv

//    public static short cvvGenerator() {
//        return Short.parseShort(String.format("%03d", (short) Math.floor(Math.random() * 999)));
//    }

    // método 3 cvv

    public static short cvvGenerator() {
        return (short) (Math.floor(Math.random() * 899) + 100);
    }
    public static String cardNumberGenerator() {
        return String.format("%04d", (short) Math.floor(Math.random() * 9999))
                + "-" + String.format("%04d", (short) Math.floor(Math.random() * 9999))
                + "-" + String.format("%04d", (short) Math.floor(Math.random() * 9999))
                + "-" + String.format("%04d", (short) Math.floor(Math.random() * 9999));
    }
}
// Usar Utilities.nombreDelMetodo() para llamar al método en otra clase
// RECUERDA IMPORTAR LA CLASE ORIGEN EN LA DE DESTINO

//      NOTAS:
//          -- En el método generador de números random
//          El símbolo "%" indica el comienzo de la especificación de formato.
//          "0" indica que el relleno debe ser realizado con ceros.
//          "8" indica que el ancho total del campo debe ser de 8 caracteres, incluyendo ceros a la izquierda si es necesario.
//          "d" indica que el argumento proporcionado es un número entero decimal.