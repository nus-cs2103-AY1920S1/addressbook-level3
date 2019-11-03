package seedu.address.diaryfeature.logic.parser.exceptions;

public class EmptyArgumentException extends Exception {
        private String parserName;
        private String filler = "Cant have the arguments for the ";
        private String second = " command empty!!!";
        private String usage;

        public EmptyArgumentException(String parsername, String usage) {

            parserName = parsername;
            this.usage = usage;
        }
    public EmptyArgumentException(String parsername) {

        parserName = parsername;
        this.usage = "";
    }
        public String toString() {
            return filler + parserName + second + "\n" + usage;
        }
    }


