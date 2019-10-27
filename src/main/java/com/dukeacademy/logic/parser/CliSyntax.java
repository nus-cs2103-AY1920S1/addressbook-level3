package com.dukeacademy.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
class CliSyntax {

    /**
     * The constant PREFIX_TITLE.
     */
    /* Prefix definitions */
    public static final Prefix PREFIX_TITLE = new Prefix("n/");
    /**
     * The constant PREFIX_TOPIC.
     */
    public static final Prefix PREFIX_TOPIC = new Prefix("p/");
    /**
     * The constant PREFIX_STATUS.
     */
    public static final Prefix PREFIX_STATUS = new Prefix("e/");
    /**
     * The constant PREFIX_DIFFICULTY.
     */
    public static final Prefix PREFIX_DIFFICULTY = new Prefix("a/");
    /**
     * The constant PREFIX_TAG.
     */
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
