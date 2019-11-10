package seedu.address.model.appstatus;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.isBothNullOrEqual;

import javafx.collections.transformation.SortedList;

import seedu.address.logic.commands.bookings.edit.EditBookingsFieldCommand;
import seedu.address.logic.commands.currency.EditCurrencyFieldCommand;
import seedu.address.logic.commands.expenditure.edit.EditExpenditureFieldCommand;
import seedu.address.logic.commands.itinerary.days.edit.EditDayFieldCommand;
import seedu.address.logic.commands.itinerary.events.edit.EditEventFieldCommand;
import seedu.address.logic.commands.trips.edit.EditTripFieldCommand.EditTripDescriptor;
import seedu.address.model.booking.Booking;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.DiaryEntryList;
import seedu.address.model.diary.EditDiaryEntryDescriptor;
import seedu.address.model.expenditure.Expenditure;
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
    private final Expenditure expenditure;
    private final DiaryEntry diaryEntry;
    private final Booking booking;
    private final CustomisedCurrency customisedCurrency;

    private final EditTripDescriptor editTripDescriptor;
    private final EditDayFieldCommand.EditDayDescriptor editDayDescriptor;
    private final EditEventFieldCommand.EditEventDescriptor editEventDescriptor;
    private final EditExpenditureFieldCommand.EditExpenditureDescriptor editExpenditureDescriptor;
    private final EditDiaryEntryDescriptor editDiaryEntryDescriptor;
    private final EditBookingsFieldCommand.EditBookingsDescriptor editBookingsDescriptor;
    private final EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor;
    private final SortedList sortedOccurrencesList;

    public PageStatus(PageType pageType, Trip trip, Day day, Event event, DiaryEntry diaryEntry,
                      Expenditure expenditure, CustomisedCurrency customisedCurrency, Booking booking,
                      EditTripDescriptor editTripDescriptor,
                      EditDayFieldCommand.EditDayDescriptor editDayDescriptor,
                      EditEventFieldCommand.EditEventDescriptor editEventDescriptor,
                      EditExpenditureFieldCommand.EditExpenditureDescriptor editExpenditureDescriptor,
                      EditDiaryEntryDescriptor editDiaryEntryDescriptor,
                      EditBookingsFieldCommand.EditBookingsDescriptor editBookingsDescriptor,
                      EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor,
                      SortedList sortedOccurrencesList) {
        this.pageType = pageType;
        this.trip = trip;
        this.day = day;
        this.event = event;
        this.expenditure = expenditure;
        this.diaryEntry = diaryEntry;
        this.booking = booking;
        this.customisedCurrency = customisedCurrency;
        this.editTripDescriptor = editTripDescriptor;
        this.editDayDescriptor = editDayDescriptor;
        this.editEventDescriptor = editEventDescriptor;
        this.editExpenditureDescriptor = editExpenditureDescriptor;
        this.editDiaryEntryDescriptor = editDiaryEntryDescriptor;
        this.editBookingsDescriptor = editBookingsDescriptor;
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
        return new PageStatus(pageType, getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param trip trip to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewTrip(Trip trip) {
        return new PageStatus(getPageType(), trip, getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param day day to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewDay(Day day) {
        return new PageStatus(getPageType(), getTrip(), day, getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param event event to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEvent(Event event) {
        return new PageStatus(getPageType(), getTrip(), getDay(), event, getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param expenditure expenditure to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewExpenditure(Expenditure expenditure) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), expenditure,
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param booking expenditure to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewBooking(Booking booking) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), booking, getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
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

    public PageStatus withResetExpenditure() {
        return withNewExpenditure(null);
    }

    public PageStatus withResetBooking() {
        return withNewBooking(null);
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
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), diaryEntry, getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Constructs a new {@link PageStatus} from the provided {@code diaryEntry}.
     *
     * @param customisedCurrency {@link CustomisedCurrency} to use.
     * @return The new {@link PageStatus} with the provided {@code diaryEntry}.
     */
    public PageStatus withNewCustomisedCurrency(CustomisedCurrency customisedCurrency) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                customisedCurrency, getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editTripDescriptor editTripDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditTripDescriptor(EditTripDescriptor editTripDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), editTripDescriptor, getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editDayDescriptor editDayDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditDayDescriptor(EditDayFieldCommand.EditDayDescriptor editDayDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), editDayDescriptor,
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());

    }

    /**
     * Immutable chained constructor.
     *
     * @param editEventDescriptor editEventDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditEventDescriptor(EditEventFieldCommand.EditEventDescriptor editEventDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                editEventDescriptor, getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editCurrencyDescriptor editEventDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditCurrencyDescriptor(
            EditCurrencyFieldCommand.EditCurrencyDescriptor editCurrencyDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), editCurrencyDescriptor, getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editExpenditureDescriptor editExpenditureDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditExpenditureDescriptor(EditExpenditureFieldCommand.EditExpenditureDescriptor
                                                               editExpenditureDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), editExpenditureDescriptor, getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     * Immutable chained constructor.
     *
     * @param editBookingsDescriptor editBookingsDescriptor to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewEditBookingsDescriptor(EditBookingsFieldCommand.EditBookingsDescriptor
                                                        editBookingsDescriptor) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                editBookingsDescriptor, getEditCurrencyDescriptor(), getSortedOccurrencesList());
    }

    /**
     *
     * @param sortedOccurrencesList {@link SortedList} to use.
     * @return The new PageStatus instance.
     */
    public PageStatus withNewSortedOccurrencesList(SortedList sortedOccurrencesList) {
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), getEditDiaryEntryDescriptor(),
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), sortedOccurrencesList);
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

    public PageStatus withResetEditExpenditureDescriptor() {
        return withNewEditExpenditureDescriptor(null);
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
        return new PageStatus(getPageType(), getTrip(), getDay(), getEvent(), getDiaryEntry(), getExpenditure(),
                getCustomisedCurrency(), getBooking(), getEditTripDescriptor(), getEditDayDescriptor(),
                getEditEventDescriptor(), getEditExpenditureDescriptor(), editDiaryEntryDescriptor,
                getEditBookingsDescriptor(), getEditCurrencyDescriptor(), getSortedOccurrencesList());
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

    public Expenditure getExpenditure() {
        return expenditure;
    }

    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public Booking getBooking() {
        return booking;
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

    public EditExpenditureFieldCommand.EditExpenditureDescriptor getEditExpenditureDescriptor() {
        return editExpenditureDescriptor;
    }

    public EditDiaryEntryDescriptor getEditDiaryEntryDescriptor() {
        return editDiaryEntryDescriptor;
    }

    public EditBookingsFieldCommand.EditBookingsDescriptor getEditBookingsDescriptor() {
        return editBookingsDescriptor;
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
                && isBothNullOrEqual(expenditure, otherPage.expenditure)
                && isBothNullOrEqual(diaryEntry, otherPage.diaryEntry)
                && isBothNullOrEqual(customisedCurrency, otherPage.customisedCurrency)
                && isBothNullOrEqual(booking, otherPage.booking)
                && isBothNullOrEqual(editTripDescriptor, otherPage.editTripDescriptor)
                && isBothNullOrEqual(editDayDescriptor, otherPage.editDayDescriptor)
                && isBothNullOrEqual(editEventDescriptor, otherPage.editEventDescriptor)
                && isBothNullOrEqual(editExpenditureDescriptor, otherPage.editExpenditureDescriptor)
                && isBothNullOrEqual(editCurrencyDescriptor, otherPage.editCurrencyDescriptor)
                && isBothNullOrEqual(editBookingsDescriptor, otherPage.editBookingsDescriptor)
                && isBothNullOrEqual(editDiaryEntryDescriptor, otherPage.editDiaryEntryDescriptor)
                && isBothNullOrEqual(sortedOccurrencesList, otherPage.getSortedOccurrencesList());
    }
}

