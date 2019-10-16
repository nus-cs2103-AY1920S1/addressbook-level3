package mams.testutil;

import java.util.HashSet;
import java.util.Set;

import mams.model.module.Module;
import mams.model.tag.Tag;
import mams.model.util.SampleDataUtil;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS1010";
    public static final String DEFAULT_MODULE_NAME = "Programming Methodology";
    public static final String DEFAULT_MODULE_DESCRIPTION = "Test description";
    public static final String DEFAULT_LECTURER_NAME = "Tung Kum Hoe Anthony";
    public static final String DEFAULT_TIME_SLOT = "1,2,45,46";
    public static final String DEFAULT_QUOTA = "100";

    private String moduleCode;
    private String moduleName;
    private String moduleDescription;
    private String lecturerName;
    private String timeSlot;
    private String quota;
    private Set<Tag> students;

    public ModuleBuilder() {
        moduleCode = DEFAULT_MODULE_CODE;
        moduleName = DEFAULT_MODULE_NAME;
        moduleDescription = DEFAULT_MODULE_DESCRIPTION;
        lecturerName = DEFAULT_LECTURER_NAME;
        timeSlot = DEFAULT_TIME_SLOT;
        quota = DEFAULT_QUOTA;
        students = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        moduleCode = moduleToCopy.getModuleCode();
        moduleName = moduleToCopy.getModuleName();
        moduleDescription = moduleToCopy.getModuleDescription();
        lecturerName = moduleToCopy.getLecturerName();
        timeSlot = moduleToCopy.getTimeSlot();
        quota = moduleToCopy.getQuota();
        students = new HashSet<>(moduleToCopy.getStudents());
    }

    /**
     * Sets the {@code moduleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String name) {
        this.moduleCode = name;
        return this;
    }

    /**
     * Sets the {@code moduleName} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    /**
     * Sets the {@code MatricId} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
        return this;
    }

    /**
     * Sets the {@code lecturerName} of the {@code Module} that we are building.
     */
    public ModuleBuilder withLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
        return this;
    }

    /**
     * Sets the {@code timeSlot} of the {@code Module} that we are building.
     */
    public ModuleBuilder withTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
        return this;
    }

    /**
     * Sets the {@code timeSlot} of the {@code Module} that we are building.
     */
    public ModuleBuilder withQuota(String quota) {
        this.quota = quota;
        return this;
    }

    /**
     * Parses the {@code students} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withTags(String ... tags) {
        this.students = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Returns a module for testing
     * @return Module
     */
    public Module build() {
        return new Module(moduleCode, moduleName, moduleDescription, lecturerName,
                timeSlot , quota, students);
    }
}
