package seedu.module.logic.commands.linkcommands;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.module.logic.commands.CommandResult;
import seedu.module.model.Model;
import seedu.module.model.ModelManager;
import seedu.module.model.ModuleBook;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.Link;
import seedu.module.model.module.TrackedModule;
import seedu.module.testutil.ArchivedModuleBuilder;
import seedu.module.testutil.ArchivedModuleListBuilder;
import seedu.module.testutil.ModuleBookBuilder;
import seedu.module.testutil.TrackedModuleBuilder;

public class EditLinkCommandTest {
    private final Link link = new Link ("website", "http://example.com");
    private EditLinkCommand command = new EditLinkCommand("website", Optional.of("new"), Optional.empty());
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @BeforeEach
    public void beforeEach() {
        model = new ModelManager();
        expectedModel = new ModelManager();
        ArchivedModule archivedModule = new ArchivedModuleBuilder().build();
        ModuleBook moduleBook = new ModuleBookBuilder().withArchivedModules(
                new ArchivedModuleListBuilder().withArchivedModule(archivedModule).build())
                .build();
        model.setModuleBook(moduleBook);
        expectedModel.setModuleBook(moduleBook);
        TrackedModule trackedModule = new TrackedModuleBuilder()
                .withLinks(new ArrayList<>(Arrays.asList(link))).build();
        model.addModule(trackedModule);
        model.setDisplayedModule(trackedModule);
    }

    @Test
    public void execute_editLinkCommand_success() {
        Link editedLink = new Link ("new", "http://example.com");
        TrackedModule expectedModule = new TrackedModuleBuilder()
                .withLinks(new ArrayList<>(Arrays.asList(editedLink))).build();
        expectedModel.addModule(expectedModule);
        expectedModel.setDisplayedModule(expectedModule);
        CommandResult expectedResult = new CommandResult(LinkCommand.MESSAGE_EDIT_SUCCESS,
                false, true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_linkNotFound_throwsCommandException() {
        EditLinkCommand wrongCommand = new EditLinkCommand("wrong", Optional.of("new"), Optional.empty());
        assertCommandFailure(wrongCommand, model, LinkCommand.MESSAGE_LINK_NOT_FOUND);
    }

    @Test
    public void execute_noEditInfo_throwsCommandException() {
        EditLinkCommand wrongCommand = new EditLinkCommand("website", Optional.empty(), Optional.empty());
        assertCommandFailure(wrongCommand, model, EditLinkCommand.MESSAGE_NO_EDIT_INFO);
    }

    @Test
    public void execute_duplicateTitle_throwsCommandException() {
        TrackedModule trackedModule = (TrackedModule) model.getDisplayedModule().get();
        trackedModule.addLink(new Link("new", "http://example.com"));
        assertCommandFailure(command, model, EditLinkCommand.MESSAGE_DUPLICATE_TITLE);
    }
}
