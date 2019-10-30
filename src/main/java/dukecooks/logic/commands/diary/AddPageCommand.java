package dukecooks.logic.commands.diary;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_DESCRIPTION;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_TITLE;

import java.util.List;

import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import javafx.collections.ObservableList;


/**
 * Adds a diary to Duke Cooks.
 */
public class AddPageCommand extends AddCommand {

    public static final String VARIANT_WORD = "page";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a page to a specific diary "
            + "Parameters: "
            + PREFIX_DIARY_NAME + " DIARY NAME "
            + PREFIX_PAGE_TITLE + " PAGE TITLE "
            + PREFIX_PAGE_DESCRIPTION + " PAGE DESCRIPTION ";

    public static final String MESSAGE_SUCCESS = "You have added a new page with title: %1$s";
    public static final String MESSAGE_NON_EXISTENT_DIARY = "This diary does not exists!";
    public static final String MESSAGE_DUPLICATE_PAGE = "This page already exists in this diary!";

    private final Page pageToAdd;
    private final DiaryName specifiedDiaryName;

    /**
     * Creates an AddPageCommand to add the specified {@code Page} to specified Diary
     */
    public AddPageCommand(Page pageToAdd, DiaryName specifiedDiaryDiaryName) {
        requireAllNonNull(pageToAdd, specifiedDiaryDiaryName);
        this.pageToAdd = pageToAdd;
        this.specifiedDiaryName = specifiedDiaryDiaryName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();
        Diary wantedDiary = new Diary(specifiedDiaryName);

        // check if diary exists
        if (!lastShownList.contains(wantedDiary)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_DIARY);
        }

        // Get specified diary in DiaryList
        wantedDiary = lastShownList.get(lastShownList.indexOf(wantedDiary));

        // check if page exists
        if (wantedDiary.hasPage(pageToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PAGE);
        }

        // add page to diary
        ObservableList<Page> newPageList = wantedDiary.getPages();
        newPageList.add(pageToAdd);
        model.setDiary(wantedDiary, new Diary(wantedDiary.getDiaryName(), newPageList));
        return new CommandResult(String.format(MESSAGE_SUCCESS, pageToAdd.getTitle()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPageCommand // instanceof handles nulls
                && pageToAdd.equals(((AddPageCommand) other).pageToAdd));
    }
}
