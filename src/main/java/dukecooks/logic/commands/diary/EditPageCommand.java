package dukecooks.logic.commands.diary;

import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_IMAGE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_DESCRIPTION;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_TITLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PAGE_TYPE;
import static dukecooks.model.Model.PREDICATE_SHOW_ALL_DIARIES;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Image;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;
import javafx.collections.ObservableList;

/**
 * Edits the details of an existing page in Duke Cooks.
 */
public class EditPageCommand extends EditCommand {

    public static final String VARIANT_WORD = "page";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the page identified "
            + "by the page index shown in the page list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DIARY_NAME + "DIARY NAME] "
            + "[" + PREFIX_PAGE_TITLE + "TITLE] "
            + "[" + PREFIX_PAGE_TYPE + "PAGE TYPE] "
            + "[" + PREFIX_PAGE_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_IMAGE + "IMAGE] "
            + "Example: " + COMMAND_WORD + " " + VARIANT_WORD + " 1 "
            + PREFIX_PAGE_TITLE + "Sushi";

    public static final String MESSAGE_EDIT_PAGE_SUCCESS = "You have edited the page: %1$s";
    public static final String MESSAGE_DIARY_NOT_FOUND = "This diary does not exist!";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PAGE = "This page already exists in the Duke Cooks.";

    private static Event event;
    private final Index index;
    private final DiaryName diaryName;
    private final EditPageDescriptor editPageDescriptor;

    /**
     * @param index of the page in the diary list to edit
     * @param editPageDescriptor details to edit the page with
     */
    public EditPageCommand(Index index, DiaryName diaryName, EditPageDescriptor editPageDescriptor) {
        requireNonNull(index);
        requireNonNull(editPageDescriptor);

        this.index = index;
        this.diaryName = diaryName;
        this.editPageDescriptor = new EditPageDescriptor(editPageDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();
        Diary targetDiary = new Diary(diaryName);

        // Check if diary exists
        if (!lastShownList.contains(targetDiary)) {
            throw new CommandException(MESSAGE_DIARY_NOT_FOUND);
        }

        int diaryIndex = lastShownList.indexOf(targetDiary);
        ObservableList<Page> pageList = lastShownList.get(diaryIndex).getPages();

        // Check if page exists
        if (index.getZeroBased() >= pageList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PAGE_DISPLAYED_INDEX);
        }

        Page pageToEdit = pageList.get(index.getZeroBased());
        Page editedPage = createEditedPage(pageToEdit, editPageDescriptor);

        if (!pageToEdit.isSamePage(editedPage) && pageList.contains(editedPage)) {
            throw new CommandException(MESSAGE_DUPLICATE_PAGE);
        }

        // Update page list
        pageList.set(pageList.indexOf(pageToEdit), editedPage);
        Diary newDiary = new Diary(targetDiary.getDiaryName(), pageList);

        model.setDiary(targetDiary, newDiary);
        model.updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);

        // Navigate to diary tab
        event = Event.getInstance();
        event.set("diary", "all");

        return new CommandResult(String.format(MESSAGE_EDIT_PAGE_SUCCESS, editedPage.getTitle()));
    }

    /**
     * Creates and returns a {@code Page} with the details of {@code pageToEdit}
     * edited with {@code editPageDescriptor}.
     */
    private static Page createEditedPage(Page pageToEdit, EditPageDescriptor editPageDescriptor) {
        assert pageToEdit != null;

        Title updatedTitle = editPageDescriptor.getTitle().orElse(pageToEdit.getTitle());
        PageType updatedPageType = editPageDescriptor.getPageType().orElse(pageToEdit.getPageType());
        PageDescription updatedPageDescription =
                editPageDescriptor.getPageDescription().orElse(pageToEdit.getDescription());
        Image updatedImage = editPageDescriptor.getImage().orElse(pageToEdit.getImage());

        return new Page(updatedTitle, updatedPageType, updatedPageDescription, updatedImage);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPageCommand)) {
            return false;
        }

        // state check
        EditPageCommand e = (EditPageCommand) other;
        return index.equals(e.index)
                && editPageDescriptor.equals(e.editPageDescriptor);
    }

    /**
     * Stores the details to edit the page with. Each non-empty field value will replace the
     * corresponding field value of the page.
     */
    public static class EditPageDescriptor {
        private Title title;
        private PageType type;
        private PageDescription description;
        private Image image;

        public EditPageDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditPageDescriptor(EditPageDescriptor toCopy) {
            setTitle(toCopy.title);
            setPageType(toCopy.type);
            setPageDescription(toCopy.description);
            setImage(toCopy.image);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, type, description, image);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public void setPageType(PageType type) {
            this.type = type;
        }

        public void setPageDescription(PageDescription description) {
            this.description = description;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public Optional<PageType> getPageType() {
            return Optional.ofNullable(type);
        }

        public Optional<PageDescription> getPageDescription() {
            return Optional.ofNullable(description);
        }

        public Optional<Image> getImage() {
            return Optional.ofNullable(image);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPageDescriptor)) {
                return false;
            }

            // state check
            EditPageDescriptor e = (EditPageDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getPageType().equals(e.getPageType())
                    && getPageDescription().equals(e.getPageDescription())
                    && getImage().equals(e.getImage());
        }
    }
}
