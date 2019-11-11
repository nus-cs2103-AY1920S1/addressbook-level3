package cs.f10.t1.nursetraverse.autocomplete;

import static cs.f10.t1.nursetraverse.autocomplete.UserinputParserUtil.parseFirstSegment;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.ADD_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.CLEAR_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.DELETE_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.EDIT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.FIND_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.LIST_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PAT_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_ADDRESS_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_EMAIL_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_NAME_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_PATIENT_VISIT_TODO_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_PHONE_DESCRIPTION;
import static cs.f10.t1.nursetraverse.autocomplete.WordDescriptionHelper.PREFIX_TAG_DESCRIPTION;

import cs.f10.t1.nursetraverse.logic.commands.AddCommand;
import cs.f10.t1.nursetraverse.logic.commands.ClearCommand;
import cs.f10.t1.nursetraverse.logic.commands.DeleteCommand;
import cs.f10.t1.nursetraverse.logic.commands.EditCommand;
import cs.f10.t1.nursetraverse.logic.commands.FindCommand;
import cs.f10.t1.nursetraverse.logic.commands.ListCommand;
import cs.f10.t1.nursetraverse.logic.parser.CliSyntax;
import cs.f10.t1.nursetraverse.model.appointment.AutoCompleteWord;
import cs.f10.t1.nursetraverse.model.autocomplete.CommandWord;
import cs.f10.t1.nursetraverse.model.autocomplete.ObjectWord;
import cs.f10.t1.nursetraverse.model.autocomplete.PrefixWord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Stub class for AutoCompleteWordStorage
 */
public class AutoCompleteStorageStub extends AutoCompleteWordStorage {
    public static final String PAT_OBJECT_WORD = parseFirstSegment(AddCommand.COMMAND_WORD).get(0);

    private ObservableList<AutoCompleteWord> oListAllObjectWord;
    private ObservableList<AutoCompleteWord> oListAllCommandWord;
    private ObservableList<AutoCompleteWord> oListAllPrefixWord;

    public AutoCompleteStorageStub() {
        super(null, null, null);
        initAllCommandWordList();
        initAllObjectWordList();
        initAllPrefixWordList();
    }

    /**
     * Initialise command list
     */
    private void initAllCommandWordList() {
        ObservableList<AutoCompleteWord> oListAllCommandWord = FXCollections.observableArrayList();
        // Patient commands
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(ListCommand.COMMAND_WORD).get(1), LIST_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(FindCommand.COMMAND_WORD).get(1), FIND_DESCRIPTION,
                false, false));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1), ADD_DESCRIPTION,
                false, true));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1), EDIT_DESCRIPTION,
                true, true));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(DeleteCommand.COMMAND_WORD).get(1), DELETE_DESCRIPTION,
                true, false));
        oListAllCommandWord.add(new CommandWord(PAT_OBJECT_WORD,
                parseFirstSegment(ClearCommand.COMMAND_WORD).get(1), CLEAR_DESCRIPTION,
                false, false));
        this.oListAllCommandWord = oListAllCommandWord;
    }

    /**
     * Initialise prefix list
     */
    private void initAllPrefixWordList() {
        ObservableList<AutoCompleteWord> oListAllPrefixWord = FXCollections.observableArrayList();
        // Pat prefixes
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_NAME.getPrefix(), PREFIX_NAME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PHONE.getPrefix(), PREFIX_PHONE_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_EMAIL.getPrefix(), PREFIX_EMAIL_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_ADDRESS.getPrefix(), PREFIX_ADDRESS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_TAG.getPrefix(), PREFIX_TAG_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(AddCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PATIENT_VISIT_TODO.getPrefix(), PREFIX_PATIENT_VISIT_TODO_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_NAME.getPrefix(), PREFIX_NAME_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PHONE.getPrefix(), PREFIX_PHONE_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_EMAIL.getPrefix(), PREFIX_EMAIL_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_ADDRESS.getPrefix(), PREFIX_ADDRESS_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_TAG.getPrefix(), PREFIX_TAG_DESCRIPTION));
        oListAllPrefixWord.add(new PrefixWord(PAT_OBJECT_WORD,
                parseFirstSegment(EditCommand.COMMAND_WORD).get(1),
                CliSyntax.PREFIX_PATIENT_VISIT_TODO.getPrefix(), PREFIX_PATIENT_VISIT_TODO_DESCRIPTION));
        this.oListAllPrefixWord = oListAllPrefixWord;
    }

    /**
     * Initialise object list
     */
    private void initAllObjectWordList() {
        ObservableList<AutoCompleteWord> oListAllObjectWord = FXCollections.observableArrayList();
        oListAllObjectWord.add(new ObjectWord(PAT_OBJECT_WORD, PAT_DESCRIPTION));
        this.oListAllObjectWord = oListAllObjectWord;
    }

    public ObservableList<AutoCompleteWord> getOListAllCommandWord() {
        return FXCollections.observableArrayList(oListAllCommandWord);
    }

    public ObservableList<AutoCompleteWord> getOListAllPrefixWord() {
        return FXCollections.observableArrayList(oListAllPrefixWord);
    }

    public ObservableList<AutoCompleteWord> getOListAllObjectWord() {
        return FXCollections.observableArrayList(oListAllObjectWord);
    }
}
