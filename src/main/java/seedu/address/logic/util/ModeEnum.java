package seedu.address.logic.util;

/**
 * Game modes for the app
 */
public enum ModeEnum {

    APP {
        public String toString() {
            return "home";
        }
    },
    GAME {
        public String toString() {
            return "start";
        }
    },
    LOAD {
        public String toString() {
            return "load";
        }
    },
    SETTINGS {
        public String toString() {
            return "settings";
        }
    }
}
