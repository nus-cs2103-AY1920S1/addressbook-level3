package mams.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    /* Prefix definitions for Student class */
    public static final Prefix PREFIX_STUDENT = new Prefix("s/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CREDITS = new Prefix("c/");
    public static final Prefix PREFIX_PREVMODS = new Prefix("p/");
    //public static final Prefix PREFIX_MATRICID = new Prefix("m/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix definitions for Module class */
    public static final Prefix PREFIX_MODULE = new Prefix("m/");

    /*Prefix definitions for Appeal class */
    public static final Prefix PREFIX_APPEAL = new Prefix("a/");
    public static final Prefix PREFIX_MASS_RESOLVE = new Prefix("mass/");
    public static final Prefix PREFIX_REASON = new Prefix("rr/");

    /* Other misc. prefix definitions */
    public static final Prefix PREFIX_ALL = new Prefix("all/");

}
