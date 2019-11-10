//package seedu.revision.ui;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.LinkedList;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import org.junit.jupiter.api.Test;
//
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.CustomMenuItem;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import seedu.revision.logic.parser.exceptions.ParseException;
//import seedu.revision.model.Model;
//import seedu.revision.model.ModelManager;
//
//
//public class AutoCompleteTest {
//    private static final SortedSet<String> entriesStub = new TreeSet<>();
//
//    private Model model = new ModelManager();
//    private Model expectedModel = new ModelManager();
//    //private Label testLabel = new Label("test");
//    //private CustomMenuItem testItem = new CustomMenuItem(testLabel, true);
//    //private ContextMenu popUpTest = new ContextMenu();
//    //private LinkedList<String> searchResult = new LinkedList<>();
//    private AutoComplete autocomplete;
//
//    @Test
//    public void execute_autoCompleteGetEntries_success() throws ParseException {
//        autocomplete = new AutoComplete();
//        entriesStub.add("add type/ q/ y/ x/ cat/ diff/ ");
//        entriesStub.add("clear");
//        entriesStub.add("delete");
//        entriesStub.add("edit");
//        entriesStub.add("exit");
//        entriesStub.add("find");
//        entriesStub.add("list");
//        entriesStub.add("list diff/");
//        entriesStub.add("list cat/");
//        entriesStub.add("help");
//        entriesStub.add("restore");
//        entriesStub.add("start mode/");
//        entriesStub.add("start mode/normal");
//        entriesStub.add("start mode/arcade");
//        entriesStub.add("start mode/custom");
//        entriesStub.add("history");
//
//        assertEquals((AutoComplete.getEntries()), entriesStub);
//
//    }
//}
