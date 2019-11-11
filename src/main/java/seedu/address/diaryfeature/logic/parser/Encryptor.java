package seedu.address.diaryfeature.logic.parser;

/**
 * Stores the Encryption methods and and functions to encrypt and decrypt.
 */
public class Encryptor {
    /**
     * Takes an input and gives the encrypted output
     *
     * @param input
     * @return the encrypted output
     */
    public static String encrypt(String input) {
        //Convert input to a char[]
        char[] myHolder = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            //for each char, turn into ASCII and add one
            int temp = (input.charAt(i) + 1) * 5;
            //turn the ASCII back to char
            myHolder[i] = (char) temp;
        }
        return new String(myHolder);
    }

    /**
     * Takes an encrypted input and gives the decrypted output
     *
     * @param input is the encrypted String
     * @return the decrypted output
     */
    public static String decrypt(String input) {
        //Convert input to a char[]

        char[] myHolder = new char[input.length()];
        for (int i = 0; i < input.length(); i++) {
            //for each char, turn into ASCII and add one
            int temp = (input.charAt(i)) / 5 - 1;
            //turn the ASCII back to char
            myHolder[i] = (char) temp;
        }
        return new String(myHolder);
    }
}
