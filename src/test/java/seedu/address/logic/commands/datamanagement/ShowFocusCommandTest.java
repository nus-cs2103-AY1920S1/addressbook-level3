package seedu.address.logic.commands.datamanagement;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulesInfo;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonModulesInfoStorage;
import seedu.address.storage.ModulesInfoStorage;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TagBuilder;
import seedu.address.ui.ResultViewType;

public class ShowFocusCommandTest {

    private Model model;
    private ModulesInfo modulesInfo;
    private TagBuilder tagBuilder;
    private Tag coreTag;
    private Tag suTag;
    private Tag algoPTag;
    private Tag sePTag;
    private Tag aiPTag;
    private Tag mirPTag;

    @BeforeEach
    public void setUp() {
        ModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(Paths.get("modules_cs.json"));
        modulesInfo = initModulesInfo(modulesInfoStorage);
        model = new ModelManager(getTypicalModulePlanner(), new UserPrefs(), modulesInfo);
        tagBuilder = new TagBuilder();
        coreTag = tagBuilder.buildDefaultCoreTag();
        algoPTag = tagBuilder.buildDefaultTag(DefaultTagType.ALGO_P);
        sePTag = tagBuilder.buildDefaultTag(DefaultTagType.SE_P);
        aiPTag = tagBuilder.buildDefaultTag(DefaultTagType.AI_P);
        mirPTag = tagBuilder.buildDefaultTag(DefaultTagType.MIR_P);
    }

    @Test
    public void execute_success() {
        // Command
        ShowFocusCommand showFocusCommand = new ShowFocusCommand();

        // Expected result
        UniqueModuleList expectedList = new UniqueModuleList();
        Module cs3230 = new ModuleBuilder().withModuleCode("CS3230").withTags(algoPTag, coreTag).build();
        Module cs2103t = new ModuleBuilder().withModuleCode("CS2103T").withTags(sePTag, coreTag).build();
        Module cs4248 = new ModuleBuilder().withModuleCode("CS4248").withTags(aiPTag, mirPTag, coreTag).build();
        expectedList.add(cs3230);
        expectedList.add(cs2103t);
        expectedList.add(cs4248);
        CommandResult expectedCommandResult = new CommandResult(ShowFocusCommand.MESSAGE_SUCCESS,
                ResultViewType.MODULE, expectedList.asUnmodifiableObservableList());

        assertCommandSuccess(showFocusCommand, model, expectedCommandResult, model);
    }

    /**
     * Initialises modules info from storage.
     */
    protected ModulesInfo initModulesInfo(ModulesInfoStorage storage) {
        ModulesInfo initializedModulesInfo;
        try {
            Optional<ModulesInfo> prefsOptional = storage.readModulesInfo();
            initializedModulesInfo = prefsOptional.orElse(new ModulesInfo());
        } catch (DataConversionException e) {
            initializedModulesInfo = new ModulesInfo();
        } catch (IOException e) {
            initializedModulesInfo = new ModulesInfo();
        }
        return initializedModulesInfo;
    }
}
