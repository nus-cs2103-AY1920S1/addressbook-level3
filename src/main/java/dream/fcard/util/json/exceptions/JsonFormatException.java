package dream.fcard.util.json.exceptions;

import dream.fcard.util.Printer;

/**
 * Exception when string being parsed is of unexpected json format.
 */
public class JsonFormatException extends Exception {

    /**
     * 1 - failed due to content of file.
     * 2 - failed due to wrong prediction of value data type.
     */
    private int errorCode;

    /**
     * Construct the exception without message and default errorCode.
     */
    public JsonFormatException() {
        super();
        errorCode = 1;
    }

    /**
     * Construct the exception with message and errorCode.
     * @param msg   message
     * @param err   errorCode
     */
    public JsonFormatException(String msg, Integer err) {
        super(msg);
        errorCode = err;
    }

    /**
     * Construct the exception with message and default errorCode.
     * @param msg   message
     */
    public JsonFormatException(String msg) {
        super(msg);
        errorCode = 1;
    }

    /**
     * Construct the exception with references to the source json file with an error code.
     * @param input json file character string
     * @param index index where error occurred in string
     * @param msg   message for the error
     * @param err   error code
     */
    public JsonFormatException(char[] input, Integer index, String msg, Integer err) {
        super(msg + "\n" + getPointToErrorString(input, index));
        errorCode = err;
    }

    /**
     * Construct the exception with references to the source json file.
     * @param input json file character string
     * @param index index where error occurred in string
     * @param msg   message for the error
     */
    public JsonFormatException(char[] input, Integer index, String msg) {
        super(msg + "\n" + getPointToErrorString(input, index));
        errorCode = 1;
    }

    /**
     * Generate reference string for error.
     * @param input json file character string
     * @param index index where error occurred in string
     * @return  formatted reference string
     */
    private static String getPointToErrorString(char[] input, Integer index) {
        int line = 1;
        int col = 0;
        StringBuilder lineBuf = new StringBuilder();
        for (int i = 0; i <= index && i < input.length; i++) {
            lineBuf.append(input[i]);
            col++;
            if (input[i] == '\n') {
                line++;
                col = 0;
                lineBuf = new StringBuilder();
            }
        }
        for (int i = index + 1; i < input.length; i++) {
            if (input[i] == '\n') {
                break;
            }
            lineBuf.append(input[i]);
        }
        return lineBuf.toString() + "\n"
                + Printer.indentString("^", Printer.repeatChar(col - 1, ' '))
                + "(" + line + ":" + col + ")\n";
    }

    /**
     * Get error code of object.
     * @return  errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }
}
