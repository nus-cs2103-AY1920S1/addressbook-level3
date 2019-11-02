package seedu.address.diaryfeature.logic.parser.exceptions;

public class EmptyArgumentException extends Exception {
        private String parserName;
        private String filler = "Cant have the arguments for the ";
        private String second = " command empty!!!";

        public EmptyArgumentException(String input) {
            parserName = input;
        }
        public String toString() {
            return filler + parserName + second;
        }
    }


