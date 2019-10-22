package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import seedu.address.model.ModulesInfo;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building StudyPlan objects.
 */
public class StudyPlanBuilder {

    // default values
    //TODO: DEFAULT_SEMESTERS is currently not used.
    public static final UniqueSemesterList DEFAULT_SEMESTERS = getDefaultSemesters();
    public static final String DEFAULT_TITLE = "Test Study Plan";
    public static final int DEFAULT_INDEX = 1;
    public static final String DEFAULT_CURRENT_SEMESTER = "Y1S1";
    public static final boolean DEFAULT_ACTIVATED_STATUS = true;
    public static final HashMap<String, Module> DEFAULT_MODULES = TypicalModuleHashMap.getTypicalModuleHashMap();
    public static final UniqueTagList DEFAULT_TAGS = new UniqueTagList();

    // member fields
    private UniqueSemesterList semesters;
    private Title title;
    private int index; // unique identifier of this study plan
    private SemesterName currentSemester;
    private boolean isActivated = false;

    // the "Mega-List" of modules of this study plan. All modules in an *active* study plan refer to a module here.
    // note: this Mega-List is only constructed when a study plan gets activated.
    private HashMap<String, Module> modules;

    // the unique list of tags of this study plan.
    // All tags in an *active* study plan refer to a tag here.
    // note: this unique list of tags is only constructed when a study plan gets activated.
    private UniqueTagList tags;


    /**
     * Default constructor.
     */
    public StudyPlanBuilder() {
        semesters = DEFAULT_SEMESTERS;
        title = new Title(DEFAULT_TITLE);
        index = DEFAULT_INDEX;
        currentSemester = SemesterName.valueOf(DEFAULT_CURRENT_SEMESTER);
        isActivated = DEFAULT_ACTIVATED_STATUS;
        modules = DEFAULT_MODULES;
        tags = DEFAULT_TAGS;
        tags.initDefaultTags();
    }

    /**
     * Initializes the StudyPlanBuilder with the data of {@code studyPlanToCopy}.
     */
    public StudyPlanBuilder(StudyPlan studyPlanToCopy) {
        requireNonNull(studyPlanToCopy);

        semesters = studyPlanToCopy.getSemesters();
        title = studyPlanToCopy.getTitle();
        index = studyPlanToCopy.getIndex();
        currentSemester = studyPlanToCopy.getCurrentSemester();
        isActivated = studyPlanToCopy.isActivated();
        modules = studyPlanToCopy.getModules();
        tags = studyPlanToCopy.getTags();
    }

    /**
     * Sets the {@code title} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code index} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withIndex(int index) {
        this.index = index;
        return this;
    }

    /**
     * Sets the {@code currentSemester} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withCurrentSemester(String currentSemester) {
        this.currentSemester = SemesterName.valueOf(currentSemester);
        return this;
    }

    /**
     * Sets the {@code isActivated} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withActivated(boolean isActivated) {
        this.isActivated = isActivated;
        return this;
    }

    /**
     * Sets the mega hash map of {@code modules} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withModules(HashMap<String, Module> modules) {
        this.modules = modules;
        return this;
    }

    /**
     * Sets the mega list of {@code tags} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withTags(UniqueTagList tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code List<Tag>} and set it to the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withTags(String... tags) {
        this.tags.setTags(SampleDataUtil.getTagList(tags));
        return this;
    }

    /**
     * Builds a new study plan.
     */
    public StudyPlan build() {
        ModulesInfo sampleModulesInfo = TypicalModulesInfo.getTypicalModulesInfo();
        SemesterName sampleCurrentSemester = currentSemester;
        StudyPlan toReturn = new StudyPlan(title, sampleModulesInfo,
                sampleCurrentSemester);
        toReturn.setDefaultSemesters();
        toReturn.setIndex(index);
        toReturn.setActivated(isActivated);
        toReturn.setModules(modules);
        toReturn.setTags(tags);

        return toReturn;
    }

    /**
     * Populates the unique semester list with the 8 semesters in the normal 4-year candidature. These
     * semesters will be empty initially.
     */
    public static UniqueSemesterList getDefaultSemesters() {
        UniqueSemesterList semesterList = new UniqueSemesterList();
        semesterList.add(new Semester(SemesterName.Y1S1));
        semesterList.add(new Semester(SemesterName.Y1S2));
        semesterList.add(new Semester(SemesterName.Y2S1));
        semesterList.add(new Semester(SemesterName.Y2S2));
        semesterList.add(new Semester(SemesterName.Y3S1));
        semesterList.add(new Semester(SemesterName.Y3S2));
        semesterList.add(new Semester(SemesterName.Y4S1));
        semesterList.add(new Semester(SemesterName.Y4S2));
        return semesterList;
    }
}
