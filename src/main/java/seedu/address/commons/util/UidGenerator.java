package seedu.address.commons.util;

import java.util.Random;

public class UidGenerator {

    public static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            + "_!@#$%^&*()-+=|}{[]:;,.<>/?";
    public static final int UID_LENGTH = 32;

    public static String get() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        while (sb.length() < UID_LENGTH) { // length of the random string.
            int index = rand.nextInt(VALID_CHARS.length());
            sb.append(VALID_CHARS.charAt(index));
        }
        return sb.toString();

    }
}
