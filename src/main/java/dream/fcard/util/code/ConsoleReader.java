package dream.fcard.util.code;

import java.util.Scanner;

/**
 * Reads code from a console and returns a string containing that code.
 */
public class ConsoleReader {
    /**
     * Reads JS code from the console.
     * @return the code in a String
     */
    static String readJsFromConsole() {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        String code = sb.toString();
        sc.close();
        return code;
    }

}
