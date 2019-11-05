 package seedu.elisa.logic.parser.exceptions;

 /**
  * Represents a parse error encountered with parsing "24:00".
  */
 public class MidnightParseException extends ParseException {

    public MidnightParseException(String message) {
        super(message);
    }
}