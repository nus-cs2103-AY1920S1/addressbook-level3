package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;

import seedu.address.model.ModulesInfo;
import seedu.address.model.module.Module;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building StudyPlan objects.
 */
public class StudyPlanBuilder {

    // default values
    public static final String DEFAULT_TITLE = "Test Study Plan";
    public static final int DEFAULT_INDEX = 1;
    public static final String DEFAULT_CURRENT_SEMESTER = "Y1S1";
    public static final boolean DEFAULT_ACTIVATED_STATUS = true;
    public static final HashMap<String, Module> DEFAULT_MODULES = TypicalModuleHashMap.getTypicalModuleHashMap();

    // member fields
    private UniqueSemesterList semesters;
    private Title title;
    private int index; // unique identifier of this study plan
    private SemesterName currentSemester;
    private boolean isActivated;

    // the "Mega-List" of modules of this study plan. All modules in an *active* study plan refer to a module here.
    // note: this Mega-List is only constructed when a study plan gets activated.
    private HashMap<String, Module> modules;

    // the unique list of module tags of this study plan.
    // All tags in an *active* study plan refer to a tag here.
    // note: this unique list of tags is only constructed when a study plan gets activated.
    private UniqueTagList tags;

    // the unique list of tags for the current study plan.
    private UniqueTagList studyPlanTags;


    /**
     * Default constructor.
     */
    public StudyPlanBuilder() {
        title = new Title(DEFAULT_TITLE);
        index = DEFAULT_INDEX;
        semesters = new UniqueSemesterList();
        currentSemester = SemesterName.valueOf(DEFAULT_CURRENT_SEMESTER);
        isActivated = DEFAULT_ACTIVATED_STATUS;
        modules = DEFAULT_MODULES;
        tags = new UniqueTagList();
        tags.initDefaultTags();
        studyPlanTags = new UniqueTagList();
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
        tags = studyPlanToCopy.getModuleTags();
        studyPlanTags = studyPlanToCopy.getStudyPlanTags();
    }

    /**
     * Sets the {@code title} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withTitle(Title title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the {@code semesters} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withSemesters(UniqueSemesterList semesters) {
        this.semesters = semesters;
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
     * Sets the mega list of module {@code tags} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withModuleTags(UniqueTagList tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Parses the module {@code tags} into a {@code List<Tag>} and set it to the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withModuleTags(String... tags) {
        List<Tag> tagList = SampleDataUtil.getUserTagList(tags);
        for (Tag tag : tagList) {
            this.tags.addTag(tag);
        }
        return this;
    }

    /**
     * Sets the module tags to the study plan.
     */
    public StudyPlanBuilder withModuleTags(Tag... tags) {
        for (Tag tag : tags) {
            this.tags.addTag(tag);
        }
        return this;
    }

    /**
     * Sets the mega list of {@code tags} of the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withStudyPlanTags(UniqueTagList tags) {
        this.studyPlanTags = tags;
        return this;
    }

    /**
     * Parses the study plan {@code tags} into a {@code List<Tag>} and
     * set it to the {@code StudyPlan} that we are building.
     */
    public StudyPlanBuilder withStudyPlanTags(Tag... tags) {
        for (Tag tag : tags) {
            this.studyPlanTags.addTag(tag);
        }
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
        toReturn.setIndex(index);
        toReturn.getSemesters().setSemesters(this.semesters);
        toReturn.setActivated(isActivated);
        toReturn.setModules(modules);
        toReturn.setModuleTags(tags);
        toReturn.setStudyPlanTags(studyPlanTags);
        return toReturn;
    }
}
