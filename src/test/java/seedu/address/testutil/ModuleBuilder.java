package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Color;
import seedu.address.model.PrereqTree;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {
    public static final String DEFAULT_NAME = "DISCRETE STRUCTURES";
    public static final int DEFAULT_MC_COUNT = 4;
    public static final Color DEFAULT_COLOR = Color.RED;
    public static final boolean DEFAULT_PREREQS_SATISFIED = true;
    public static final String DEFAULT_MODULE_CODE = "CS1231";
    public static final PrereqTree DEFAULT_PREREQ_TREE = null;

    private ModuleCode moduleCode;
    private Name name;
    private int mcCount;
    private Color color;
    private boolean prereqsSatisfied;
    private PrereqTree prereqTree;
    private UniqueTagList tags;

    public ModuleBuilder() {
        name = new Name(DEFAULT_NAME);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        mcCount = DEFAULT_MC_COUNT;
        color = DEFAULT_COLOR;
        prereqsSatisfied = DEFAULT_PREREQS_SATISFIED;
        tags = new UniqueTagList();
        prereqTree = DEFAULT_PREREQ_TREE;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code personToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        name = moduleToCopy.getName();
        mcCount = moduleToCopy.getMcCount();
        color = moduleToCopy.getColor();
        prereqsSatisfied = moduleToCopy.getPrereqsSatisfied();
        moduleCode = moduleToCopy.getModuleCode();
        prereqTree = ParserUtil.parsePrereqTree(moduleToCopy.getPrereqString());
        tags = new UniqueTagList();
        tags.initDefaultTags();
        tags.setTags(moduleToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withTags(String... tags) {
        this.tags = new UniqueTagList();
        this.tags.setTags(SampleDataUtil.getUserTagList(tags));
        return this;
    }

    /**
     * Sets the tags to the list of tags in the module
     */
    public ModuleBuilder withTags(Tag... tags) {
        for (Tag tag : tags) {
            this.tags.addTag(tag);
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code mcCount} of the {@code Module} that we are building.
     */
    public ModuleBuilder withMcCount(int mcCount) {
        this.mcCount = mcCount;
        return this;
    }

    /**
     * Sets the {@code Color} of the {@code Module} that we are building.
     */
    public ModuleBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    /**
     * Sets the {@code PrereqsSatsified} of the {@code Module} that we are building.
     */
    public ModuleBuilder withPrereqsSatisfied(boolean prereqsSatisfied) {
        this.prereqsSatisfied = prereqsSatisfied;
        return this;
    }

    /**
     * Sets the {@code PrereqTree} of the {@code Module} that we are building.
     */
    public ModuleBuilder withPrereqTree(PrereqTree prereqTree) {
        this.prereqTree = prereqTree;
        return this;
    }

    public Module build() {
        return new Module(name, moduleCode, mcCount, color, prereqTree, tags);
    }

}
