package com.mindhub.homebanking.utils;
import com.mindhub.homebanking.models.Account;
public class Utilities {
    public static String numberAccountGenerator (Account account) {
        return "VIN" + String.format("%08d", 99999-account.getId());
    }
//    public static short cvvGenerator() {
//        short cvv = (short) Math.floor(Math.random() * 999);
//        if (cvv < 100) {
//            cvv = Short.parseShort("0" + cvv);
//        } else if (cvv < 10) {
//            cvv = Short.parseShort("00" + cvv);
//        }
//        return cvv;
//    }
    public static short cvvGenerator () {
        return Short.parseShort(String.format("%03d", (short) Math.floor(Math.random() * 999)));
    }
    public static String cardNumberGenerator () {
        return String.format("%04d", (short) Math.floor(Math.random() * 9999))
                + "-" + String.format("%04d", (short) Math.floor(Math.random() * 9999))
                + "-" + String.format("%04d", (short) Math.floor(Math.random() * 9999))
                + "-" + String.format("%04d", (short) Math.floor(Math.random() * 9999));
    }
}
// Usar Utilities.nombreDelMetodo() para llamar al método en otra clase
// RECUERDA IMPORTAR LA CLASE ORIGEN EN LA DE DESTINO