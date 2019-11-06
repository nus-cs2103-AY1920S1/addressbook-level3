package io.xpire.model;

/**
 * Identifier for the 2 different lists.
 */
public enum ListType {
    XPIRE {
        @Override
        public String toString() {
            return "main";
        }
    },
    REPLENISH {
        @Override
        public String toString() {
            return "replenish";
        }
    }
}
