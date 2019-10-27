package com.dukeacademy.model.question;

/**
 * The enum Enum status.
 */
public enum EnumStatus {
    /**
     * The New.
     */
    NEW {
        public String toString() {
            return "New";
        }
    },
    /**
     * The Attempted.
     */
    ATTEMPTED {
        public String toString() {
            return "Attempted";
        }
    },
    /**
     * The Passed.
     */
    PASSED {
        public String toString() {
            return "Passed";
        }
    }
}
