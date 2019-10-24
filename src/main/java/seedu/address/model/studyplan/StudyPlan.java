package seedu.address.model.studyplan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import seedu.address.model.Color;
import seedu.address.model.ModuleInfo;
import seedu.address.model.ModulesInfo;
import seedu.address.model.PrereqTree;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Name;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.semester.exceptions.SemesterAlreadyBlockedException;
import seedu.address.model.semester.exceptions.SemesterNotFoundException;
import seedu.address.model.tag.DefaultTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;

/**
 * Represents a study plan in the module planner.
 */
public class StudyPlan implements Cloneable {

    private static int totalNumberOfStudyPlans = 0;

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


    // to create a study plan without a Title
    public StudyPlan(ModulesInfo modulesInfo, SemesterName currentSemester) {
        this(new Title(""), modulesInfo, currentSemester);
    }

    // to create a study plan with a Title
    public StudyPlan(Title title, ModulesInfo modulesInfo, SemesterName currentSemester) {
        this.title = title;
        this.semesters = new UniqueSemesterList();
        this.currentSemester = currentSemester;
        setDefaultSemesters();

        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.

        tags = new UniqueTagList();
        tags.initDefaultTags();

        setMegaModuleHashMap(modulesInfo);

        totalNumberOfStudyPlans++;
        this.index = totalNumberOfStudyPlans;
    }

    /**
     * This constructor is used for {@code JsonAdaptedStudyPlan}.
     */
    public StudyPlan(Title modelTitle, int modelIndex, List<Semester> modelSemesters,
                     HashMap<String, Module> modelModules, List<Tag> modelTags, SemesterName currentSemester) {
        this.title = modelTitle;
        this.index = modelIndex;
        this.semesters = new UniqueSemesterList();
        this.semesters.setSemesters(modelSemesters);
        this.modules = modelModules;
        this.tags = new UniqueTagList();
        tags.initDefaultTags();
        for (Tag tag : modelTags) {
            tags.addTag(tag);
        }
        this.currentSemester = currentSemester;
    }

    // make a copy of the current study without incrementing the index, for version tracking commits
    public StudyPlan copyForCommit() throws CloneNotSupportedException {
        return this.clone();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Title getTitle() {
        return title;
    }

    public UniqueSemesterList getSemesters() {
        return semesters;
    }

    public int getIndex() {
        return index;
    }

    // "Mega-list" of modules
    public HashMap<String, Module> getModules() {
        return modules;
    }

    // "Mega-list" of tags
    public UniqueTagList getTags() {
        return tags;
    }

    public SemesterName getCurrentSemester() {
        return currentSemester;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    // for testing
    public void setIndex(int index) {
        this.index = index;
    }

    // for testing
    public void setModules(HashMap<String, Module> modules) {
        this.modules = modules;
    }

    // for testing
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public static int getTotalNumberOfStudyPlans() {
        return totalNumberOfStudyPlans;
    }

    public static void setTotalNumberOfStudyPlans(int totalNumberOfStudyPlans) {
        StudyPlan.totalNumberOfStudyPlans = totalNumberOfStudyPlans;
    }

    public int getTotalMcCount() {
        int totalMcCount = 0;
        for (Semester sem : semesters) {
            totalMcCount += sem.getMcCount();
        }
        return totalMcCount;
    }

    public int getCompletedMcCount() {
        int completedMcCount = 0;
        for (Semester sem : semesters) {
            if (sem.getSemesterName().compareTo(currentSemester) < 0) {
                completedMcCount += sem.getMcCount();
            }
        }
        return completedMcCount;
    }

    public String getMcCountString() {
        return "(" + getCompletedMcCount() + "/" + getTotalMcCount() + ")";
    }

    /**
     * Populates the unique semester list with the 8 semesters in the normal 4-year candidature. These
     * semesters will be empty initially.
     */
    public void setDefaultSemesters() {
        semesters.add(new Semester(SemesterName.Y1S1));
        semesters.add(new Semester(SemesterName.Y1S2));
        semesters.add(new Semester(SemesterName.Y2S1));
        semesters.add(new Semester(SemesterName.Y2S2));
        semesters.add(new Semester(SemesterName.Y3S1));
        semesters.add(new Semester(SemesterName.Y3S2));
        semesters.add(new Semester(SemesterName.Y4S1));
        semesters.add(new Semester(SemesterName.Y4S2));
    }

    public void setCurrentSemester(SemesterName currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     * Given a {@code ModuleInfo} object, convert it to a {@code Module}.
     */
    private Module convertModuleInfoToModule(ModuleInfo moduleInfo) {
        UniqueTagList moduleTagList = assignDefaultTags(moduleInfo);
        Name name = new Name(moduleInfo.getName());
        ModuleCode moduleCode = new ModuleCode(moduleInfo.getCode());
        int mcCount = moduleInfo.getMc();
        PrereqTree prereqTree = moduleInfo.getPrereqTree();
        return new Module(name, moduleCode, mcCount, Color.RED, prereqTree, moduleTagList);
    }

    /**
     * Returns a {@code UniqueTagList} with the default tags attached to the module with the given module info.
     *
     * @param moduleInfo The module info of the module.
     * @return A {@code UniqueTagList} with the default tags.
     */
    // made public so as to be accessible from activate method from ModulePlanner
    public UniqueTagList assignDefaultTags(ModuleInfo moduleInfo) {
        UniqueTagList moduleTagList = new UniqueTagList();
        UniqueTagList studyPlanTagList = tags;

        // assign focus primary tags
        assignFocusPrimaryTags(moduleInfo, moduleTagList, studyPlanTagList);

        // assign focus elective tags
        assignFocusElectiveTags(moduleInfo, moduleTagList, studyPlanTagList);

        // assign s/u-able tag
        assignSuTag(moduleInfo, moduleTagList, studyPlanTagList);

        // assign completed tag
        assignCompletedTag(moduleInfo, moduleTagList, studyPlanTagList);

        // assign core tag
        assignCoreTag(moduleInfo, moduleTagList, studyPlanTagList);

        //TODO ue and ulr tags?

        return moduleTagList;
    }

    /**
     * Assigns a default core tag to the module.
     *
     * @param moduleInfo       The module info of the current module.
     * @param moduleTagList    The list of tags for the current module.
     * @param studyPlanTagList The list of tags for the current study plan.
     */
    private void assignCoreTag(ModuleInfo moduleInfo, UniqueTagList moduleTagList, UniqueTagList studyPlanTagList) {
        boolean isCore = moduleInfo.getIsCore();
        if (isCore) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag("Core"));
        }
    }

    /**
     * Assigns a default completed tag to the module.
     *
     * @param moduleInfo       The module info of the current module.
     * @param moduleTagList    The list of tags for the current module.
     * @param studyPlanTagList The list of tags for the current study plan.
     */
    private void assignCompletedTag(ModuleInfo moduleInfo, UniqueTagList moduleTagList,
                                    UniqueTagList studyPlanTagList) {
        for (Semester semester : semesters) {
            UniqueModuleList uniqueModuleList = semester.getModules();
            for (Module module : uniqueModuleList) {
                if (module.getModuleCode().toString().equals(moduleInfo.getCode())) {
                    if (semester.getSemesterName().compareTo(currentSemester) < 0) {
                        moduleTagList.addTag(studyPlanTagList.getDefaultTag("Completed"));
                    }
                    break;
                }
            }
        }
    }

    /**
     * Assigns a default S/U-able tag to the module.
     *
     * @param moduleInfo       The module info of the current module.
     * @param moduleTagList    The list of tags for the current module.
     * @param studyPlanTagList The list of tags for the current study plan.
     */
    private void assignSuTag(ModuleInfo moduleInfo, UniqueTagList moduleTagList, UniqueTagList studyPlanTagList) {
        boolean canSu = moduleInfo.getSuEligibility();
        if (canSu) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag("S/U-able"));
        }
    }

    /**
     * Assigns all the default focus elective tags to the module.
     *
     * @param moduleInfo       The module info of the current module.
     * @param moduleTagList    The list of tags for the current module.
     * @param studyPlanTagList The list of tags for the current study plan.
     */
    private void assignFocusElectiveTags(ModuleInfo moduleInfo, UniqueTagList moduleTagList,
                                         UniqueTagList studyPlanTagList) {
        List<String> focusElectives = moduleInfo.getFocusElectives();
        for (String focusElective : focusElectives) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag(focusElective + ":E"));
        }
    }

    /**
     * Assigns all the default focus primary tags to the module.
     *
     * @param moduleInfo       The module info of the current module.
     * @param moduleTagList    The list of tags for the current module.
     * @param studyPlanTagList The list of tags for the current study plan.
     */
    private void assignFocusPrimaryTags(ModuleInfo moduleInfo, UniqueTagList moduleTagList,
                                        UniqueTagList studyPlanTagList) {
        List<String> focusPrimaries = moduleInfo.getFocusPrimaries();
        for (String focusPrimary : focusPrimaries) {
            moduleTagList.addTag(studyPlanTagList.getDefaultTag(focusPrimary + ":P"));
        }
    }

    /**
     * Updates the completed tags in all the modules.
     * Add completed tags to modules in semesters before the current semester and remove them from semesters
     * after the current semester.
     */
    public void updateAllCompletedTags() {
        for (Semester semester : semesters) {
            if (semester.getSemesterName().compareTo(currentSemester) < 0) {
                addCompletedTags(semester);
            } else {
                removeCompletedTags(semester);
            }
        }
    }

    /**
     * Adds completed tags to all modules in the given semester.
     *
     * @param semester The semester where the modules are contained.
     */
    private void addCompletedTags(Semester semester) {
        UniqueModuleList uniqueModuleList = semester.getModules();
        for (Module module : uniqueModuleList) {
            UniqueTagList uniqueTagList = module.getTags();
            DefaultTag completedTag = tags.getDefaultTag("Completed");
            if (uniqueTagList.contains(completedTag)) {
                continue;
            }
            uniqueTagList.addTag(completedTag);
        }
    }

    /**
     * Removes completed tags from all modules in the given semester.
     *
     * @param semester The semester where the modules are contained.
     */
    private void removeCompletedTags(Semester semester) {
        UniqueModuleList uniqueModuleList = semester.getModules();
        for (Module module : uniqueModuleList) {
            UniqueTagList uniqueTagList = module.getTags();
            DefaultTag completedTag = tags.getDefaultTag("Completed");
            if (uniqueTagList.contains(completedTag)) {
                uniqueTagList.removeCompletedTag(completedTag);
            }
        }
    }

    private void setMegaModuleHashMap(ModulesInfo modulesInfo) {
        HashMap<String, ModuleInfo> moduleInfoHashMap = modulesInfo.getModuleInfoHashMap();
        modules = new HashMap<>();
        for (ModuleInfo moduleInfo : moduleInfoHashMap.values()) {
            Module module = convertModuleInfoToModule(moduleInfo);
            modules.put(module.getModuleCode().toString(), module);
        }
    }

    /**
     * Adds a module to a semester, given the {@code ModuleCode} and {@code SemesterName}.
     *
     * @param moduleCode   module code of the module to be added.
     * @param semesterName semester name of the target semester.
     * @throws SemesterNotFoundException
     */
    public void addModuleToSemester(ModuleCode moduleCode, SemesterName semesterName)
            throws SemesterNotFoundException {
        Semester targetSemester = null;
        for (Semester semester : semesters) {
            if (semester.getSemesterName().equals(semesterName)) {
                targetSemester = semester;
                break;
            }
        }
        if (targetSemester == null) {
            throw new SemesterNotFoundException();
        }

        Module moduleToAdd = modules.get(moduleCode.toString());
        targetSemester.addModule(moduleToAdd);
    }

    /**
     * Blocks a semester with the given {@code SemesterName} so that the user cannot add modules to that semester.
     * The user can enter a reason for blocking it (e.g. NOC, internship).
     */
    public void blockSemester(SemesterName semesterName, String reasonForBlock) throws SemesterNotFoundException {
        Semester semesterToBlock = null;
        Iterator<Semester> iterator = semesters.iterator();
        while (iterator.hasNext()) {
            Semester semester = iterator.next();
            if (semester.getSemesterName().equals(semesterName)) {
                semesterToBlock = semester;
            }
        }
        if (semesterToBlock == null) {
            throw new SemesterNotFoundException();
        }
        if (semesterToBlock.isBlocked()) {
            throw new SemesterAlreadyBlockedException();
        }
        semesterToBlock.setBlocked(true);
        semesterToBlock.setReasonForBlocked(reasonForBlock);
    }

    /**
     * Returns true if both study plans of the same index have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two study plans.
     */
    public boolean isSameStudyPlan(StudyPlan other) {
        if (other == null) {
            return false;
        } else {
            return this.index == other.index && this.modules == other.modules;
        }
    }

    /**
     * Updates the prerequisites of all modules in the study plan.
     * This method should be called every time there is a change in its modules.
     */
    public void updatePrereqs() {
        ArrayList<String> prevSemCodes = new ArrayList<>();
        for (Semester sem : semesters) {
            ArrayList<String> thisSemCodes = new ArrayList<>();
            for (Module mod : sem.getModules()) {
                String moduleCode = mod.getModuleCode().toString();
                thisSemCodes.add(moduleCode);
                mod.verifyAndUpdate(prevSemCodes);
            }
            prevSemCodes.addAll(thisSemCodes);
        }
    }

    /**
     * Returns a list of valid modules that can be taken in a given semester.
     * This will return all valid modules for the semester, even if they're already in the study plan.
     */
    public List<String> getValidMods(SemesterName semName) {
        ArrayList<String> prevSemCodes = new ArrayList<>();
        for (Semester sem : semesters) {
            if (sem.getSemesterName() == semName) {
                break;
            }
            for (Module mod : sem.getModules()) {
                prevSemCodes.add(mod.getModuleCode().toString());
            }
        }

        ArrayList<String> result = new ArrayList<>();
        for (Module mod : modules.values()) {
            String moduleCode = mod.getModuleCode().toString();
            if (prevSemCodes.contains(moduleCode)) {
                continue;
            }
            // At this point, mod should not be inside prevSemCodes -- if verify, add to result
            if (mod.verify(prevSemCodes)) {
                result.add(moduleCode);
            }
        }

        Collections.sort(result);
        return result;
    }

    /**
     * Returns a copy of the current study plan.
     *
     * @return a clone of this study plan.
     * @throws CloneNotSupportedException
     */
    public StudyPlan clone() throws CloneNotSupportedException {
        StudyPlan clone = (StudyPlan) super.clone();
        clone.semesters = semesters.clone();
        clone.title = title.clone();
        clone.index = index;
        clone.isActivated = isActivated;

        // because of this, the mega-lists fields don't have final keyword
        clone.modules = new HashMap<>();
        for (Module module : modules.values()) {
            clone.modules.put(module.getModuleCode().toString(), module.clone());
        }

        clone.tags = (UniqueTagList) tags.clone();

        return clone;
    }

    /**
     * Adds the given {@code UserTag} to the module with the given module code in this study plan.
     *
     * @param tag        The tag to be added.
     * @param moduleCode The module code of the target module
     * @return True if the tag was successfully added.
     */
    public boolean addTag(UserTag tag, String moduleCode) {
        if (!tags.contains(tag)) {
            tags.addTag(tag);
        }
        Module targetModule = modules.get(moduleCode);
        return targetModule.addTag(tag);
    }

    /**
     * Checks if this study plan contains the given {@code UserTag}
     *
     * @param tagName The name of the tag to be checked.
     * @return True if this study plan contains the tag.
     */
    public boolean containsTag(String tagName) {
        return tags.containsTagWithName(tagName);
    }

    /**
     * Returns the tag in this study plan that corresponds to the given tag name.
     */
    public Tag getTag(String tagName) {
        return tags.getTag(tagName);
    }

    /**
     * Deletes the given {@code UserTag} from this study plan.
     * Also removes it from all modules in this study plan that has the tag.
     */
    public void deleteTag(UserTag toDelete) {
        tags.removeTag(toDelete);
        Set<String> moduleCodes = modules.keySet();
        for (String moduleCode : moduleCodes) {
            Module currentModule = modules.get(moduleCode);
            currentModule.deleteUserTag(toDelete);
        }
    }

    /**
     * Removes the given tag from all modules in this study plan that has the tag.
     * The tag is not deleted from this study plan.
     */
    public void removeTagFromAllModules(UserTag toRemove) {
        Set<String> moduleCodes = modules.keySet();
        for (String moduleCode : moduleCodes) {
            Module currentModule = modules.get(moduleCode);
            currentModule.deleteUserTag(toRemove);
        }
    }

    /**
     * Removes the given {@code UserTag} from the {@code Module} with the given module code.
     *
     * @return True if the tag was successfully removed.
     */
    public boolean removeTagFromModule(UserTag toRemove, String moduleCode) {
        Module targetModule = modules.get(moduleCode);
        return targetModule.deleteUserTag(toRemove);
    }

    /**
     * Returns all the tags that the module with the given module code is attached to.
     *
     * @return The UniqueTagList containing all the tags.
     */
    public UniqueTagList getModuleTags(String moduleCode) {
        Module targetModule = modules.get(moduleCode);
        return targetModule.getTags();
    }

    @Override
    public String toString() {
        String toReturn = "Study Plan index: " + index + " Title: " + title.toString();
        return toReturn;
    }

    @Override
    // TODO: this currently compares only the index. Does this need to be modified?
    public boolean equals(Object other) {
        if (other instanceof StudyPlan) {
            return this.index == ((StudyPlan) other).index;
            //&& this.semesters.equals(((StudyPlan) other).getSemesters());
        } else {
            return false;
        }
    }
}
