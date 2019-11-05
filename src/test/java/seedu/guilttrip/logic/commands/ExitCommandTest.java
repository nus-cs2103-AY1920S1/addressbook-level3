//package seedu.guilttrip.logic.commands;
//
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.guilttrip.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.model.Model;
//import seedu.guilttrip.model.ModelManager;
//
//public class ExitCommandTest {
//    private Model model = new ModelManager();
//    private Model expectedModel = new ModelManager();
//
//    @Test
//    public void execute_exit_success() {
//        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
//        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
//    }
//}
