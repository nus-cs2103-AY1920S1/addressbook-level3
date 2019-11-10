package seedu.module.logic.commands.linkcommands;

import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.Arrays;

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

public class MarkLinkCommandTest {
    private MarkLinkCommand command = new MarkLinkCommand("website", true);
    private final Link link = new Link ("website", "http://example.com");
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
    public void execute_markLinkCommand_success() {
        Link markedLink = new Link ("website", "http://example.com", true);
        TrackedModule expectedModule = new TrackedModuleBuilder()
                .withLinks(new ArrayList<>(Arrays.asList(markedLink))).build();
        expectedModel.addModule(expectedModule);
        expectedModel.setDisplayedModule(expectedModule);
        CommandResult expectedResult = new CommandResult(LinkCommand.MESSAGE_MARK_SUCCESS,
                false, true, false);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_markAlreadyMarked_successWithDifferentCommandResult() {
        TrackedModule trackedModule = (TrackedModule) model.getDisplayedModule().get();
        trackedModule.getLink().get(0).setMarked();
        Link markedLink = new Link ("website", "http://example.com", true);
        TrackedModule expectedModule = new TrackedModuleBuilder()
                .withLinks(new ArrayList<>(Arrays.asList(markedLink))).build();
        expectedModel.addModule(expectedModule);
        expectedModel.setDisplayedModule(expectedModule);
        CommandResult expectedResult = new CommandResult(MarkLinkCommand.MESSAGE_ALREADY_MARKED);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_linkNotFound_throwsCommandException() {
        MarkLinkCommand wrongCommand = new MarkLinkCommand("wrong", true);
        assertCommandFailure(wrongCommand, model, LinkCommand.MESSAGE_LINK_NOT_FOUND);
    }

}
