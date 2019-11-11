package seedu.address.testutil.moduleutil;

/**
 * Examples of valid & invalid strings for {@code NusModsShareLink}.
 */
public class NusModsShareLinkStrings {
    public static final String VALID_LINK_1 = "https://nusmods.com/timetable/sem-1/share?CS2101=&CS2103T="
            + "LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    public static final String VALID_LINK_2 = "https://nusmods.com/timetable/sem-1/share?CFG1002=LEC:11&"
            + "CS2100=TUT:08,LAB:15,LEC:1&CS2101=&CS2103T=LEC:G05&GEQ1000=TUT:D44&GER1000=TUT:E37";
    //Will automatically remove duplicates due to the use of map in NusModsShareLink.parseLink(), thus valid link.
    public static final String VALID_LINK_DUPLICATE_MODULE_LESSONS = "https://nusmods.com/timetable/sem-1/share?"
            + "CS2103T=LEC:G05&CS2103T=LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    //Can't check validity of module code within parser as there's no access to model.
    public static final String VALID_LINK_INVALID_MODULE_CODE = "https://nusmods.com/timetable/sem-1/share?CS2101=&"
            + "INVALIDMODULE=LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    //Can't check validity of class number within parser as there's no access to model.
    public static final String VALID_LINK_INVALID_CLASS_NUMBER = "https://nusmods.com/timetable/sem-1/share?CS2101=&"
            + "CS2103T=LEC:GXX&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    public static final String INVALID_LINK_NOT_URL = "not a url";
    public static final String INVALID_LINK_ANOTHER_URL = "https://google.com/";
    public static final String INVALID_LINK_TYPO_URL = "https://nusbods.com/timetable/sem-1/share?CS2101=&CS2103T="
            + "LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    public static final String INVALID_LINK_INVALID_SEMESTER = "https://nusmods.com/timetable/sem999/share?CS2101=&"
            + "CS2103T=LEC:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
    public static final String INVALID_LINK_MISSING_QUERY_STRING = "https://nusmods.com/timetable/sem-1/share?";
    public static final String INVALID_LINK_INVALID_QUERY_STRING = "https://nusmods.com/timetable/sem-1/share?CS2101";
    public static final String INVALID_LINK_INVALID_CLASS_TYPE = "https://nusmods.com/timetable/sem-1/share?CS2101=&"
            + "CS2103T=LOL:G05&CS3230=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";
}
