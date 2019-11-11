// @@author sreesubbash
package seedu.address.logic.util;

/**
 * Game modes for the app
 */
public enum ModeEnum {

    OPEN {
        public String toString() {
            return "open";
        }
    },
    GAME {
        public String toString() {
            return "game";
        }
    },
    HOME {
        public String toString() {
            return "home";
        }
    },
    SETTINGS {
        public String toString() {
            return "settings";
        }
    },
    Exit {
        public String toString() {
            return "exit";
        }
    }
}
