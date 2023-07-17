package com.mindhub.homebanking.utils;
import com.mindhub.homebanking.models.Account;
public final class Utilities {
    public static String numberAccountGenerator(Account account) {
        return "VIN - " + String.format("%08d", 99999-account.getId());
    }
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