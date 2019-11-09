package seedu.billboard.model.versionedbillboard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.TypicalExpenses.getDeleteTypicalBillboard;
import static seedu.billboard.testutil.TypicalExpenses.getTypicalBillboard;

import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.billboard.logic.commands.CommandResult;
import seedu.billboard.model.Model;
import seedu.billboard.model.ModelManager;
import seedu.billboard.model.UserPrefs;

class VersionedBillboardTest {
    private VersionedBillboard vb = new VersionedBillboard();
    private Model model;
    private Stack<Model> modelList = new Stack<>();
    private Stack<String> cmdList = new Stack<>();

    @BeforeEach
    void setUp() {
        VersionedBillboard.clearStateList();
        modelList.clear();
        cmdList.clear();
        model = new ModelManager(getTypicalBillboard(), new UserPrefs());
    }

    @Test
    void commit() {
        //currentState == 0
        VersionedBillboard.addCmd("start",
                new CommandResult("", false, false, null));
        VersionedBillboard.commit(model);
        modelList.push(model);
        assertTrue(vb.compareBillboardModels(modelList));
        //currentState != 0
        VersionedBillboard.setCurrentStatePointer(1);
        Model delModel = new ModelManager(getDeleteTypicalBillboard(), new UserPrefs());
        VersionedBillboard.commit(delModel);
        modelList.clear();
        modelList.push(delModel);
        assertTrue(vb.compareBillboardModels(modelList));
    }

    @Test
    void setCurrentState() {
        //All empty -> True
        assertTrue(vb.compareBillboardModels(modelList));
        //vb not empty, other empty -> False
        VersionedBillboard.commit(model);
        assertFalse(vb.compareBillboardModels(modelList));
        //vb and other same and not empty -> True
        modelList.push(model);
        assertTrue(vb.compareBillboardModels(modelList));
        //vb empty, other not empty -> False
        VersionedBillboard.clearStateList();
        assertFalse(vb.compareBillboardModels(modelList));
    }
}
