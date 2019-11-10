package seedu.address.model.appstatus;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.isBothNullOrEqual;

import javafx.collections.transformation.SortedList;
import seedu.address.logic.commands.currency.EditCurrencyFieldCommand;
import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand.EditTripDescriptor;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.DiaryEntryList;
import seedu.address.model.diary.EditDiaryEntryDescriptor;
import seedu.address.model.expense.Expense;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.trip.Trip;

/**
 * Abstraction of the page context of the TravelPal application.
 */
public class PageStatus {
    private final PageType pageType;
    private final Trip trip;
    private final Day day;
    private final Event event;
    private final Expense expense;
    private final DiaryEntry diaryEntry;
    private final CustomisedCurrency customisedCurrency;
    private final EditTripDescriptor editTripDescriptor;
    private final EditDayFieldCommand.EditDayDescriptor editDayDescriptor;
    private final EditEventFieldCommand.EditEventDescriptor editEventDescriptor;
    private final EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor;
    private final EditDiaryEntryDescriptor editDiaryEntryDescriptor;
    private final EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor;
    private final SortedList sortedOccurrencesList;

    public PageStatus(PageType pageType, Trip trip, Day day, Event event, DiaryEntry diaryEntry,
                      Expense expense, CustomisedCurrency customisedCurrency,
                      EditTripDescriptor editTripDescriptor,
                      EditDayFieldCommand.EditDayDescriptor editDayDescriptor,
                      EditEventFieldCommand.EditEventDescriptor editEventDescriptor,
                      EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor,
                      EditDiaryEntryDescriptor editDiaryEntryDescriptor,
                      EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor,
                      SortedList sortedOccurrencesList) {
        this.pageType = pageType;
        this.trip = trip;
        this.day = day;
        this.event = event;
        this.expense = expense;
        this.diaryEntry = diaryEntry;
        this.customisedCurrency = customisedCurrency;
        this.editTripDescriptor = editTripDescriptor;
        this.editDayDescriptor = editDayDescriptor;
        this.editEventDescriptor = editEventDescriptor;
        this.editExpenseDescriptor = editExpenseDescriptor;
        this.editDiaryEntryDescriptor = editDiaryEntryDescriptor;
        this.editCurrencyDescriptor = editCurrencyDescriptor;
        this.sortedOccurrencesList = sortedOccurrencesList;
    }

    /**
     * Immutable chained constructor.
     *
     * @param pageType page type to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewPageType(PageType pageType) {
        return new PageStatus(pageType, getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param trip trip to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param day day to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewDay(Day day) {
        return new PageStatus(getPageType(), getTrip(), day, getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param event event to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEvent(Event event) {
        return new PageStatus(getPageType(), getTrip(), getDay(), event, getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param expense expense to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewExpense(Expense expense) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), expense,
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    public PageStatus withResetTrip() {
        return withNewTrip(null);
    }

    public PageStatus withResetDay() {
        return withNewDay(null);
    }

    public PageStatus withResetEvent() {
        return withNewEvent(null);
    }

    public PageStatus withResetExpense() {
        return withNewExpense(null);
    }


    public PageStatus withResetCustomisedCurrency() {
        return withNewCustomisedCurrency(null);
    }

    /**
     * Constructs a new {@link PageStatus} from the provided {@code diaryEntry}.
     *
     * @param diaryEntry {@link DiaryEntry} to use.
     * @return The new {@link PageStatus} with the provided {@code diaryEntry}.
     */
    public PageStatus withNewDiaryEntry(DiaryEntry diaryEntry) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), diaryEntry, getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Constructs a new {@link PageStatus} from the provided {@code diaryEntry}.
     *
     * @param customisedCurrency {@link CustomisedCurrency} to use.
     * @return The new {@link PageStatus} with the provided {@code diaryEntry}.
     */
    public PageStatus withNewCustomisedCurrency(CustomisedCurrency customisedCurrency) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                customisedCurrency, getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editTripDescriptor editTripDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditTripDescriptor(EditTripDescriptor editTripDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), editTripDescriptor, getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editDayDescriptor editDayDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditDayDescriptor(EditDayFieldCommand.EditDayDescriptor editDayDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), editDayDescriptor,
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());

    }

    /**
     * Immutable chained constructor.
     *
     * @param editEventDescriptor editEventDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditEventDescriptor(EditEventFieldCommand.EditEventDescriptor editEventDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                editEventDescriptor, getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editCurrencyDescriptor editEventDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditCurrencyDescriptor(
            EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                editCurrencyDescriptor, getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editExpenseDescriptor editExpenseDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditExpenseDescriptor(EditExpenseFieldCommand.EditExpenseDescriptor
                                                               editExpenseDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), editExpenseDescriptor, getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), getSortedOccurrencesList());

    }

    /**
     * Immutable chained constructor.
     *
     * @param sortedOccurrencesList {@link SortedList} to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewSortedOccurrencesList(SortedList sortedOccurrencesList) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), getEditDiaryEntryDescriptor(),
                getEditCurrencyDescriptor(), sortedOccurrencesList);

    }

    public PageStatus withResetEditTripDescriptor() {
        return withNewEditTripDescriptor(null);
    }

    public PageStatus withResetEditDayDescriptor() {
        return withNewEditDayDescriptor(null);
    }

    public PageStatus withResetEditEventDescriptor() {
        return withNewEditEventDescriptor(null);
    }

    public PageStatus withResetEditExpenseDescriptor() {
        return withNewEditExpenseDescriptor(null);
    }

    public PageStatus withResetSortedOccurrencesList() {
        return withNewSortedOccurrencesList(null);
    }

    /**
     * Constructs a new {@link PageStatus} from the provided {@code editDiaryEntryDescriptor}.
     *
     * @param editDiaryEntryDescriptor {@link EditDiaryEntryDescriptor} to use.
     * @return The new {@link PageStatus} with the provided {@code editDiaryEntryDescriptor}.
     */
    public PageStatus withNewEditDiaryEntryDescriptor(EditDiaryEntryDescriptor editDiaryEntryDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpense(),
                getCustomisedCurrency(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenseDescriptor(), editDiaryEntryDescriptor,
                getEditCurrencyDescriptor(), sortedOccurrencesList);
    }

    public PageStatus withResetEditCurrencyDescriptor() {
        return withNewEditCurrencyDescriptor(null);
    }
    public PageType getPageType() {
        return pageType;
    }

    public Trip getTrip() {
        return trip;
    }

    public Day getDay() {
        return day;
    }

    public Event getEvent() {
        return event;
    }

    public Expense getExpense() {
        return expense;
    }

    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public CustomisedCurrency getCustomisedCurrency() {
        return customisedCurrency;
    }

    public EditTripDescriptor getEditTripDescriptor() {
        return editTripDescriptor;
    }

    public EditDayFieldCommand.EditDayDescriptor getEditDayDescriptor() {
        return editDayDescriptor;
    }

    public EditEventFieldCommand.EditEventDescriptor getEditEventDescriptor() {
        return editEventDescriptor;
    }

    public EditExpenseFieldCommand.EditExpenseDescriptor getEditExpenseDescriptor() {
        return editExpenseDescriptor;
    }

    public EditDiaryEntryDescriptor getEditDiaryEntryDescriptor() {
        return editDiaryEntryDescriptor;
    }

    public SortedList getSortedOccurrencesList() {
        return sortedOccurrencesList;
    }

    //------------------------Diary accessors------------------------

    public Diary getCurrentTripDiary() {
        requireNonNull(trip);

        return trip.getDiary();
    }

    public DiaryEntryList getCurrentTripDiaryEntryList() {
        Diary currentTripDiary = getCurrentTripDiary();
        requireNonNull(currentTripDiary);

        return currentTripDiary.getDiaryEntryList();
    }

    //----------------------Diary accessors end----------------------

    public EditCurrencyFieldCommand.EditCurrencyDescriptor getEditCurrencyDescriptor() {
        return editCurrencyDescriptor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PageStatus)) {
            return false;
        }

        PageStatus otherPage = (PageStatus) obj;

        return isBothNullOrEqual(pageType, otherPage.pageType)
                && isBothNullOrEqual(trip, otherPage.trip)
                && isBothNullOrEqual(day, otherPage.day)
                && isBothNullOrEqual(event, otherPage.event)
                && isBothNullOrEqual(expense, otherPage.expense)
                && isBothNullOrEqual(diaryEntry, otherPage.diaryEntry)
                && isBothNullOrEqual(customisedCurrency, otherPage.customisedCurrency)
                && isBothNullOrEqual(editTripDescriptor, otherPage.editTripDescriptor)
                && isBothNullOrEqual(editDayDescriptor, otherPage.editDayDescriptor)
                && isBothNullOrEqual(editEventDescriptor, otherPage.editEventDescriptor)
                && isBothNullOrEqual(editExpenseDescriptor, otherPage.editExpenseDescriptor)
                && isBothNullOrEqual(editCurrencyDescriptor, otherPage.editCurrencyDescriptor)
                && isBothNullOrEqual(editDiaryEntryDescriptor, otherPage.editDiaryEntryDescriptor)
                && isBothNullOrEqual(sortedOccurrencesList, otherPage.getSortedOccurrencesList());
    }


}
