//package seedu.planner.logic.commands;
//
//import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.planner.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.planner.logic.commands.result.CommandResult;
//import seedu.planner.model.Model;
//import seedu.planner.model.ModelManager;
//
//public class HelpCommandTest {
//    private Model model = new ModelManager();
//    private Model expectedModel = new ModelManager();
//
//    @Test
//    public void execute_help_success() {
//        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
//        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
//    }
//}
