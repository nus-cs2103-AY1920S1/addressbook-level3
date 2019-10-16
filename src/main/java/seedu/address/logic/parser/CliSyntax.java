package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_IDENTIFICATION_NUMBER = new Prefix("/id");
    public static final Prefix PREFIX_NAME = new Prefix("/name");
    public static final Prefix PREFIX_PHONE_NUMBER = new Prefix("/phoneNo");
    public static final Prefix PREFIX_SEX = new Prefix("/sex");
    public static final Prefix PREFIX_DATE_OF_BIRTH = new Prefix("/dob");
    public static final Prefix PREFIX_DATE_JOINED = new Prefix("/dateJoined");
    public static final Prefix PREFIX_DESIGNATION = new Prefix("/designation");
    public static final Prefix PREFIX_EMPLOYMENT_STATUS = new Prefix("/employmentStatus"); // todo: change UG
    public static final Prefix PREFIX_STATUS = new Prefix("/status");
    public static final Prefix PREFIX_DATE_OF_DEATH = new Prefix("/dod");
    public static final Prefix PREFIX_DATE_OF_ADMISSION = new Prefix("/doa");
    public static final Prefix PREFIX_NRIC = new Prefix("/nric");
    public static final Prefix PREFIX_RELIGION = new Prefix("/religion");
    public static final Prefix PREFIX_NAME_NOK = new Prefix("/nameNOK");
    public static final Prefix PREFIX_PHONE_NOK = new Prefix("/phoneNOK");
    public static final Prefix PREFIX_CAUSE_OF_DEATH = new Prefix("/cod");
    public static final Prefix PREFIX_BODY_DETAILS = new Prefix("/details");
    public static final Prefix PREFIX_ORGANS_FOR_DONATION = new Prefix("/organsForDonation");
    public static final Prefix PREFIX_FRIDGE_ID = new Prefix("/fridgeId");
    public static final Prefix PREFIX_RELATIONSHIP = new Prefix("/relationship");
    public static final Prefix PREFIX_FLAG = new Prefix("-");

    // to be deleted
    //public static final Prefix PREFIX_NAME = new Prefix("n/");
    //public static final Prefix PREFIX_PHONE_NUMBER = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
