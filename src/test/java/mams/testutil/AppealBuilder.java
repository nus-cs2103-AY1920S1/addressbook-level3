package mams.testutil;

/**
 * A utility class to help with building Appeal objects.
 */
public class AppealBuilder {

    public static final String DEFAULT_APPEAL_ID = "C000001";
    public static final String DEFAULT_APPEAL_TYPE = "add module";
    public static final String DEFAULT_STUDENT_ID = "A0180000A";
    public static final String DEFAULT_ACADEMIC_YEAR = "AY2019";
    public static final int DEFAULT_WORKLOAD = 20;
    public static final String DEFAULT_DESCRIPTION = "I want to graduate early";
    public static final String DEFAULT_PREVIOUS_MODULE = "";
    public static final String DEFAULT_NEW_MODULE = "";
    public static final String DEFAULT_MODULE_TO_ADD = "CS2100";
    public static final String DEFAULT_MODULE_TO_DROP = "";
    public static final boolean DEFAULT_RESOLVED = false;
    public static final String DEFAULT_RESULT = "";

    private String appealId;
    private String appealType;
    private String studentId;
    private String academicYear;
    private int workLoad;
    private String appealDescription;
    private String previousModule;
    private String newModule;
    private String moduleToAdd;
    private String moduleToDrop;
    private boolean isResolved;
    private String result;

    /**
     * Initializes the AppealBuilder with the data of {@code appealToCopy}.
     */
    public AppealBuilder() {
        appealId = DEFAULT_APPEAL_ID;
        appealType = DEFAULT_APPEAL_TYPE;
        studentId = DEFAULT_STUDENT_ID;
        academicYear = DEFAULT_ACADEMIC_YEAR;
        workLoad = DEFAULT_WORKLOAD;
        appealDescription = DEFAULT_DESCRIPTION;
        previousModule = DEFAULT_PREVIOUS_MODULE;
        newModule = DEFAULT_NEW_MODULE;
        moduleToAdd = DEFAULT_MODULE_TO_ADD;
        moduleToDrop = DEFAULT_MODULE_TO_DROP;
        isResolved = DEFAULT_RESOLVED;
        result = DEFAULT_RESULT;
    }


}
