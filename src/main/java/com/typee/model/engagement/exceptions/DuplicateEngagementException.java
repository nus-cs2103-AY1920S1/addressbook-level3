package com.typee.model.engagement.exceptions;

/**
 * Signals that the operation will result in duplicate Engagements
 * (Engagements are considered duplicates if they have the same fields).
 */
public class DuplicateEngagementException extends RuntimeException {
    public DuplicateEngagementException() {
        super("Operation would result in duplicate engagements");
    }
}
