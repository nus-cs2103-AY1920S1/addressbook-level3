package dream.fcard.util.json;

import dream.fcard.util.json.exceptions.JsonFormatException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.datastructures.Pair;

/**
 * Parses json string to JsonValue structures.
 */
public class JsonParser {
    /**
     * Given a string input, parse it as a JsonValue.
     * @param input input string
     * @return      JsonValue
     * @throws JsonFormatException Exceptions indicate incorrect syntax for json files
     */
    public static JsonValue parseJsonInput(String input) throws JsonFormatException {
        return processDynamicValue(input.toCharArray(), 0).snd;
    }

    /**
     * Parses from input[i] onwards as a json value, which can be of type specified in ValueTypes
     * enum.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and DynamicValue; algebraic sum type of all possible ValueTypes
     * @throws JsonFormatException file format error
     */
    public static Pair<Integer,JsonValue> processDynamicValue(char[] input, int i)
            throws JsonFormatException {
        Pair<Integer,JsonValue> obj;
        try {
            Pair<Integer,Integer> res1 = parseJsonInt(input, i);
            obj = new Pair<>(res1.fst, new JsonValue(res1.snd));
        } catch (JsonFormatException e1) {
            try {
                Pair<Integer,Double> res2 = parseJsonDouble(input, i);
                obj = new Pair<>(res2.fst, new JsonValue(res2.snd));
            } catch (JsonFormatException e2) {
                try {
                    Pair<Integer,Boolean> res3 = parseJsonBoolean(input, i);
                    obj = new Pair<>(res3.fst, new JsonValue(res3.snd));
                } catch (JsonFormatException e3) {
                    try {
                        Pair<Integer,String> res4 = parseJsonString(input, i);
                        obj = new Pair<>(res4.fst, new JsonValue(res4.snd));
                    } catch (JsonFormatException e4) {
                        try {
                            Pair<Integer,JsonObject> res5 = parseJsonObject(input,
                                    i);
                            obj = new Pair<>(res5.fst, new JsonValue(res5.snd));
                        } catch (JsonFormatException e5) {
                            try {
                                Pair<Integer,JsonArray> res6 = parseJsonArray(input,
                                        i);
                                obj = new Pair<>(res6.fst, new JsonValue(res6.snd));
                            } catch (JsonFormatException e6) {
                                if (e3.getErrorCode() == 2 && e4.getErrorCode() == 2
                                        && e5.getErrorCode() == 2 && e6.getErrorCode() == 2) {
                                    throw new JsonFormatException(input, i, "Is of unknown format");
                                } else if (e4.getErrorCode() == 2 && e5.getErrorCode() == 2
                                        && e6.getErrorCode() == 2) {
                                    throw e3;
                                } else if (e5.getErrorCode() == 2 && e6.getErrorCode() == 2) {
                                    throw e4;
                                } else if (e6.getErrorCode() == 2) {
                                    throw e5;
                                } else {
                                    throw e6;
                                }
                            }
                        }
                    }
                }
            }
        }
        return obj;
    }

    /**
     * Parses from input[i] onwards as a json array.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and array
     * @throws JsonFormatException file format error
     */
    private static Pair<Integer,JsonArray> parseJsonArray(char[] input, int i)
            throws JsonFormatException {
        JsonArray arr = new JsonArray();
        i = skipWhiteSpace(input, i);
        if (input[i] != '[') {
            throw new JsonFormatException(input, i, "Expecting [", 2);
        }
        // find '['

        i = skipWhiteSpace(input, i + 1);
        if (i >= input.length) {
            throw new JsonFormatException(input, i, "Empty array did not close");
        }
        // search first value

        boolean isFirst = true;
        while (input[i] != ']') {
            if (!isFirst) {
                i = skipWhiteSpace(input, i);
                if (i >= input.length) {
                    break;
                }
                if (input[i] != ',') {
                    throw new JsonFormatException(input, i, "Value pairs must be comma separated");
                }
                i = skipWhiteSpace(input, i + 1);
                if (i >= input.length) {
                    break;
                }
            }
            // advance ',' between value pairs

            Pair<Integer,JsonValue> valuePair = processDynamicValue(input, i);
            i = skipWhiteSpace(input, valuePair.fst);
            if (i >= input.length) {
                break;
            }
            arr.add(valuePair.snd);
            // parse value

            isFirst = false;
        }
        // process key value pairs

        i = skipWhiteSpace(input, i);
        if (i >= input.length || input[i] != ']') {
            throw new JsonFormatException(input, i, "Array did not close");
        }
        // find ']'
        return new Pair<>(i + 1, arr);
    }

    /**
     * Parses from input[i] onwards as a json object.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and object / key value pairs HashMap
     * @throws JsonFormatException file format error
     */
    private static Pair<Integer,JsonObject> parseJsonObject(char[] input, int i)
            throws JsonFormatException {
        JsonObject obj = new JsonObject();
        i = skipWhiteSpace(input, i);
        if (input[i] != '{') {
            throw new JsonFormatException(input, i, "Expecting {", 2);
        }
        // find '{'

        i = skipWhiteSpace(input, i + 1);
        if (i >= input.length) {
            throw new JsonFormatException(input, i, "Empty object did not close");
        }
        // search first key

        boolean isFirst = true;
        while (input[i] != '}') {
            if (!isFirst) {
                i = skipWhiteSpace(input, i);
                if (i >= input.length) {
                    break;
                }
                if (input[i] != ',') {
                    throw new JsonFormatException(input, i, "Key value pairs must be comma separated");
                }
                i = skipWhiteSpace(input, i + 1);
                if (i >= input.length) {
                    break;
                }
            }
            // advance ',' between key value pairs

            String key;
            try {
                Pair<Integer,String> keyPair = parseJsonString(input, i);
                i = keyPair.fst;
                key = keyPair.snd;
            } catch (JsonFormatException ignored) {
                throw new JsonFormatException(input, i, "Object keys must be strings at " + i);
            }
            // parse key

            skipWhiteSpace(input, i);
            if (i >= input.length) {
                break;
            }
            if (input[i] != ':') {
                throw new JsonFormatException(input, i, "Expected : after key name");
            }
            i = skipWhiteSpace(input, i + 1);
            if (i >= input.length) {
                break;
            }
            // find ':'

            Pair<Integer,JsonValue> valuePair = processDynamicValue(input, i);
            i = skipWhiteSpace(input, valuePair.fst);
            if (i >= input.length) {
                break;
            }
            obj.put(key, valuePair.snd);
            // parse value

            isFirst = false;
        }

        i = skipWhiteSpace(input, i);
        if (i >= input.length || input[i] != '}') {
            throw new JsonFormatException(input, i, "Object did not close");
        }
        // find '}'
        return new Pair<>(i + 1, obj);
    }

    /**
     * Parses from input[i] onwards as an int.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and int
     * @throws JsonFormatException file format error
     */
    private static Pair<Integer,Integer> parseJsonInt(char[] input, int i)
            throws JsonFormatException {
        StringBuilder value = new StringBuilder();
        while (i < input.length && (Character.isDigit(input[i]) || input[i] == '-')) {
            value.append(input[i]);
            i++;
        }
        if (value.length() > 0 && (i >= input.length || checkIfLegalAfterValue(input[i]))) {
            try {
                return new Pair<>(i, Integer.parseInt(value.toString()));
            } catch (NumberFormatException ignored) {
                throw new JsonFormatException(input, i, "Text is not an Integer");
            }
        }
        throw new JsonFormatException(input, i, "Expected Integer but encountered something");
    }

    /**
     * Parses from input[i] onwards as a double.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and double
     * @throws JsonFormatException file format error
     */
    private static Pair<Integer,Double> parseJsonDouble(char[] input, int i)
            throws JsonFormatException {
        StringBuilder value = new StringBuilder();
        while (i < input.length && (Character.isDigit(input[i]) || input[i] == 'e' || input[i] == '.'
                || input[i] == '-')) {
            value.append(input[i]);
            i++;
        }
        if (value.length() > 0 && (i == input.length || checkIfLegalAfterValue(input[i]))) {
            try {
                return new Pair<>(i, Double.parseDouble(value.toString()));
            } catch (NumberFormatException ignored) {
                throw new JsonFormatException(input, i, "Text is not a Double");
            }
        }
        throw new JsonFormatException(input, i, "Expected Double but encountered something else");
    }

    /**
     * Parses from input[i] onwards as a boolean.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and boolean
     * @throws JsonFormatException file format error
     */
    private static Pair<Integer,Boolean> parseJsonBoolean(char[] input, int i)
            throws JsonFormatException {
        boolean value;
        if (input[i] != 't' && input[i] != 'f') {
            throw new JsonFormatException(input, i, "Expected Boolean but encountered something else", 2);
        }
        if (i + 3 < input.length && input[i] == 't' && input[i + 1] == 'r'
                && input[i + 2] == 'u' && input[i + 3] == 'e') {
            value = true;
            i += 4;
        } else if (i + 4 < input.length && input[i] == 'f' && input[i + 1] == 'a'
                && input[i + 2] == 'l' && input[i + 3] == 's' && input[i + 4] == 'e') {
            value = false;
            i += 5;
        } else {
            throw new JsonFormatException(input, i, "Expected Boolean but encountered something else");
        }

        if (i < input.length && !checkIfLegalAfterValue(input[i])) {
            throw new JsonFormatException(input, i, "Expected Boolean but encountered something else");
        }

        return new Pair<>(i, value);
    }

    /**
     * Parses from input[i] onwards as a string with double quotes surround. Escaped double quotes
     * are also replaced with regular double quotes
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index and string
     * @throws JsonFormatException file format error
     */
    private static Pair<Integer,String> parseJsonString(char[] input, int i)
            throws JsonFormatException {
        StringBuilder value = new StringBuilder();
        boolean escape = false;
        i = skipWhiteSpace(input, i);
        if (input[i] != '"') {
            throw new JsonFormatException(input, i,
                    "Expected starting double quotes for string but encountered something else", 2);
        }
        i++;
        while (i < input.length) {
            if (input[i] == '"' && !escape) {
                i++;
                break;
            } else if (input[i] == '\\' && !escape) {
                escape = true;
            } else if (escape) {
                if (input[i] == '"') {
                    value.append(input[i]);
                } else if (input[i] == 'n') {
                    value.append('\n');
                } else {
                    value.append('\\');
                    value.append(input[i]);
                }
                escape = false;
            } else {
                value.append(input[i]);
            }
            i++;
        }
        if (i > input.length) {
            throw new JsonFormatException(input, i, "String did not terminate with double quotes");
        }
        if (i < input.length && !checkIfLegalAfterValue(input[i])) {
            throw new JsonFormatException(
                    input, i, "Expected string but encountered something else");
        }
        return new Pair<>(i, value.toString());
    }

    /**
     * Check if character is a legal possibility after non object, array values.
     *
     * @param c character to test
     * @return True if legal
     */
    private static boolean checkIfLegalAfterValue(char c) {
        return Character.isWhitespace(c) || c == ',' || c == ']' || c == '}' || c == ':';
    }

    /**
     * Skip whitespace input[i] onwards till non whitespace encountered.
     *
     * @param input input character array
     * @param i     index to start parsing
     * @return resulting index
     */
    private static int skipWhiteSpace(char[] input, int i) {
        while (i < input.length && Character.isWhitespace(input[i])) {
            i++;
        }
        return i;
    }

    /**
     * Replace instances of " and n as escape characters for formatting as json string.
     * @param str   string to format
     * @return      formatted string
     */
    public static String formatStringForJson(String str) {
        return "\"" + str.replaceAll("\"", "\\\\\"").replaceAll("\n", "\\\\\n") + "\"";
    }
}
