package seedu.address.diaryfeature.logic.parser;

public class Encryptor {

    public static String encrypt(String input) {
        char[] myHolder = new char[input.length()];
        for(int i = 0; i<input.length(); i++) {
            int temp = input.charAt(i) + 1;
            myHolder[i] = (char)temp;
        }

        return new String(myHolder);
    }

    public static String decrypt(String input) {
        char[] myHolder = new char[input.length()];
        for(int i = 0; i<input.length(); i++) {
            int temp = input.charAt(i) - 1;
            myHolder[i] = (char)temp;
        }

        return new String(myHolder);
    }

}
