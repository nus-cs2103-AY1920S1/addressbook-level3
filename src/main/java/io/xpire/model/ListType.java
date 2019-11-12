package io.xpire.model;

//@@author JermyTan
/**
 * Identifier for the 2 different lists.
 */
public enum ListType {
    /** Identifier for Xpire */
    XPIRE {
        @Override
        public String toString() {
            return "main";
        }
    },
    /** Identifier for Replenish List */
    REPLENISH {
        @Override
        public String toString() {
            return "replenish";
        }
    }
}
