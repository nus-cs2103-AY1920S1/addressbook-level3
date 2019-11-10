package io.xpire.logic.commands;

import static io.xpire.commons.core.Messages.MESSAGE_EMPTY_LIST;
import static io.xpire.logic.commands.ExportCommand.BORDER;
import static io.xpire.logic.commands.ExportCommand.RESOLUTION_SIZE;
import static io.xpire.logic.commands.ExportCommand.SHOWING_EXPORT_MESSAGE;
import static io.xpire.model.ListType.REPLENISH;
import static io.xpire.testutil.TypicalItems.getTypicalLists;

import org.junit.jupiter.api.Test;

import io.xpire.commons.util.StringUtil;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Item;
import javafx.collections.ObservableList;

//@@author JermyTan
public class ExportCommandTest {
    @Test
    public void execute_emptyLists_failure() {
        Model model = new ModelManager();

        CommandTestUtil.assertCommandFailure(new ExportCommand(), model, MESSAGE_EMPTY_LIST);
    }

    @Test
    public void execute_nonEmptyXpire_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(new ExportCommand(), model,
                new CommandResult(SHOWING_EXPORT_MESSAGE, true, this.getPngData(model)), expectedModel);
    }

    @Test
    public void execute_nonEmptyReplenishList_success() {
        Model model = new ModelManager(getTypicalLists(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLists(), new UserPrefs());
        model.setCurrentList(REPLENISH);
        expectedModel.setCurrentList(REPLENISH);

        CommandTestUtil.assertCommandSuccess(new ExportCommand(), model,
                new CommandResult(SHOWING_EXPORT_MESSAGE, true, this.getPngData(model)), expectedModel);
    }

    private byte[] getPngData(Model model) {
        ObservableList<? extends Item> currentList = model.getCurrentList();
        StringBuilder formattedOutput = new StringBuilder(BORDER);
        for (int index = 1; index <= currentList.size(); index++) {
            formattedOutput.append(String.format("%d. %s\n", index, currentList.get(index - 1).toString()));
            formattedOutput.append(BORDER);
        }

        byte[] pngData = StringUtil.getQrCode(formattedOutput.toString(), RESOLUTION_SIZE);
        return pngData;
    }
}
