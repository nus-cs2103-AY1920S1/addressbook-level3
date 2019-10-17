package seedu.address.logic;

/**
 * Enum that represents which function the user is currently using.
 */
public enum FunctionMode {
    FLASHCARD {
        public String toString() {
            return "flashcard";
        }
    },

    CHEATSHEET {
        public String toString() {
            return "cheatsheet";
        }
    },

    NOTE {
        public String toString() {
            return "note";
        }
    },

    UNDEFINED {
        public String toString() {
            return "undefined";
        }
    };
}
