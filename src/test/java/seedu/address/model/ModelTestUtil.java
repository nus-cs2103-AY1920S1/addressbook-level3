package seedu.address.model;

import static seedu.address.logic.parser.ParserDateUtil.DATE_FORMATTER;
import static seedu.address.logic.parser.ParserDateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.expense.DayNumber;
import seedu.address.model.expense.Expense;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.Event;
import seedu.address.model.itinerary.event.EventList;
import seedu.address.testutil.CustomisedCurrencyBuilder;
import seedu.address.testutil.DayBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Utility class for {@link Model}.
 */
public class ModelTestUtil {
    // Trip fields
    public static final String VALID_NAME_AFRICA = "Africa Trip";
    public static final String VALID_NAME_BALI = "Bali Trip";
    public static final String VALID_STARTDATE_AFRICA_1 = "01/01/2019";
    public static final String VALID_STARTDATE_AFRICA_2 = "01/01/2019 1200";
    public static final String VALID_STARTDATE_AFRICA_3 = "1200";
    public static final String VALID_STARTDATE_BALI_1 = "02/02/2018";
    public static final String VALID_STARTDATE_BALI_2 = "02/02/2018 1300";
    public static final String VALID_STARTDATE_BALI_3 = "1300";
    public static final String VALID_ENDDATE_AFRICA_1 = "01/02/2019";
    public static final String VALID_ENDDATE_AFRICA_2 = "01/11/2019 1200";
    public static final String VALID_ENDDATE_AFRICA_3 = "1500";
    public static final String VALID_ENDDATE_BALI_1 = "05/05/2018";
    public static final String VALID_ENDDATE_BALI_2 = "07/07/2018 1300";
    public static final String VALID_ENDDATE_BALI_3 = "2100";
    public static final String VALID_DESTINATION_AFRICA = "Africa";
    public static final String VALID_DESTINATION_BALI = "Bali";
    public static final String VALID_TOTAL_BUDGET_AFRICA = "120.00";
    public static final String VALID_TOTAL_BUDGET_BALI = "3100.00";

    // Day fields
    public static final String VALID_NAME_DAY_1 = "Arrival Day";
    public static final String VALID_NAME_DAY_2 = "Best Day";
    public static final String VALID_STARTDATE_DAY_1_1 = "01/01/2019";
    public static final String CLASHING_STARTDATE_DAY_1_1 = "01/01/2018";
    public static final String CLASHING_STARTDATE_DAY_1_2 = "02/01/2019";
    public static final String VALID_STARTDATE_DAY_1_2 = "01/01/2019 1200";
    public static final String VALID_STARTDATE_DAY_1_3 = "1200";
    public static final String VALID_STARTDATE_DAY_2_1 = "02/02/2018";
    public static final String VALID_STARTDATE_DAY_2_2 = "02/02/2018 1300";
    public static final String VALID_STARTDATE_DAY_2_3 = "1300";
    public static final String VALID_ENDDATE_DAY_1_1 = "01/02/2019";
    public static final String CLASHING_ENDDATE_DAY_1_1 = "31/01/2019";
    public static final String CLASHING_ENDDATE_DAY_1_2 = "02/02/2019";
    public static final String VALID_ENDDATE_DAY_1_2 = "01/11/2019 1200";
    public static final String VALID_ENDDATE_DAY_1_3 = "1500";
    public static final String VALID_ENDDATE_DAY_2_1 = "05/05/2018";
    public static final String VALID_ENDDATE_DAY_2_2 = "07/07/2018 1300";
    public static final String VALID_ENDDATE_DAY_2_3 = "2100";
    public static final String VALID_DESTINATION_DAY_1 = "Gambia";
    public static final String VALID_DESTINATION_DAY_2 = "Bali";
    public static final String VALID_TOTAL_BUDGET_DAY_1 = "120.00";
    public static final String VALID_TOTAL_BUDGET_DAY_2 = "3100.00";
    public static final String VALID_DESCRIPTION_DAY_1 = "This is the day of arrival";
    public static final String VALID_DESCRIPTION_DAY_2 = "This is the second day of my trip";


    // Event Fields
    public static final String VALID_NAME_EVENT_1 = "Arrival";
    public static final String VALID_NAME_EVENT_2 = "Boating";
    public static final String VALID_STARTDATE_EVENT_1_1 = "01/01/2019";
    public static final String VALID_STARTDATE_EVENT_1_2 = "01/01/2019 1200";
    public static final String CLASHING_STARTDATE_EVENT_1_1 = "01/01/2019 1100";
    public static final String CLASHING_STARTDATE_EVENT_1_2 = "01/01/2019 1300";
    public static final String VALID_STARTDATE_EVENT_1_3 = "1200";
    public static final String VALID_STARTDATE_EVENT_2_1 = "02/02/2018";
    public static final String VALID_STARTDATE_EVENT_2_2 = "02/02/2018 1300";
    public static final String VALID_STARTDATE_EVENT_2_3 = "1300";
    public static final String VALID_ENDDATE_EVENT_1_1 = "01/01/2019";
    public static final String VALID_ENDDATE_EVENT_1_2 = "01/01/2019 2000";
    public static final String CLASHING_ENDDATE_EVENT_1_1 = "01/01/2019 1900";
    public static final String CLASHING_ENDDATE_EVENT_1_2 = "01/01/2019 2100";
    public static final String VALID_ENDDATE_EVENT_1_3 = "1500";
    public static final String VALID_ENDDATE_EVENT_2_1 = "05/05/2018";
    public static final String VALID_ENDDATE_EVENT_2_2 = "02/02/2018 1400";
    public static final String VALID_ENDDATE_EVENT_2_3 = "2100";
    public static final String VALID_DESTINATION_EVENT_1 = "Gambia";
    public static final String VALID_DESTINATION_EVENT_2 = "Bali";
    public static final String VALID_TOTAL_BUDGET_EVENT_1 = "120.00";
    public static final String VALID_TOTAL_BUDGET_EVENT_2 = "3100.00";
    public static final String VALID_DESCRIPTION_EVENT_1 = "This is the EVENT of arrival";
    public static final String VALID_DESCRIPTION_EVENT_2 = "This is the second EVENT of my trip";

    // Expense fields
    public static final String VALID_NAME_EXPENSE_1 = "Taxi";
    public static final String VALID_NAME_EXPENSE_2 = "Laundry";
    public static final String VALID_AMOUNT_EXPENSE_1 = "20.50";
    public static final String VALID_AMOUNT_EXPENSE_2 = "1.75";
    public static final String VALID_DAY_NUMBER_EXPENSE_1 = "2";
    public static final String VALID_DAY_NUMBER_EXPENSE_2 = "20";
    public static final String VALID_TYPE_EXPENSE_1 = "misc";
    public static final String VALID_TYPE_EXPENSE_2 = "planned";

    // Currency fields
    public static final String VALID_NAME_CURRENCY_1 = "JPY";
    public static final String VALID_NAME_CURRENCY_2 = "Turkish Lira";
    public static final String VALID_SYMBOL_CURRENCY_1 = "¥";
    public static final String VALID_SYMBOL_CURRENCY_2 = "TL";
    public static final String VALID_RATE_CURRENCY_1 = "80";
    public static final String VALID_RATE_CURRENCY_2 = "4.25";

    //Inventory fields
    public static final int VALID_INTEGER_1 = 10;
    public static final int VALID_INTEGER_LARGE = 1000000000;

    //Valid days
    public static final Day VALID_DAY_1 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
            .setStartDate(LocalDate.parse(VALID_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(VALID_ENDDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_1))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
            .setEventList(new EventList(LocalDate.parse(VALID_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay()))
            .build();
    public static final Day CLASHING_DAY_1 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
            .setStartDate(LocalDate.parse(VALID_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(CLASHING_ENDDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_1))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
            .setEventList(new EventList(LocalDate.parse(VALID_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay()))
            .build();

    public static final Day CLASHING_DAY_2 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
            .setStartDate(LocalDate.parse(CLASHING_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(VALID_ENDDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_1))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
            .setEventList(new EventList(LocalDate.parse(CLASHING_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay()))
            .build();

    public static final Day CLASHING_DAY_3 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
            .setStartDate(LocalDate.parse(CLASHING_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(CLASHING_ENDDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_1))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
            .setEventList(new EventList(LocalDate.parse(CLASHING_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay()))
            .build();

    public static final Day CLASHING_DAY_4 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
            .setStartDate(LocalDate.parse(CLASHING_STARTDATE_DAY_1_2, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(CLASHING_ENDDATE_DAY_1_2, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_1))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
            .setEventList(new EventList(LocalDate.parse(CLASHING_STARTDATE_DAY_1_2, DATE_FORMATTER).atStartOfDay()))
            .build();

    public static final Day CLASHING_DAY_5 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
            .setStartDate(LocalDate.parse(CLASHING_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(CLASHING_ENDDATE_DAY_1_2, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_1))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
            .setEventList(new EventList(LocalDate.parse(CLASHING_STARTDATE_DAY_1_1, DATE_FORMATTER).atStartOfDay()))
            .build();

    public static final Day VALID_DAY_2 = DayBuilder.newInstance()
            .setDescription(new Description(VALID_DESCRIPTION_DAY_2))
            .setStartDate(LocalDate.parse(VALID_STARTDATE_DAY_2_1, DATE_FORMATTER).atStartOfDay())
            .setEndDate(LocalDate.parse(VALID_ENDDATE_DAY_2_1, DATE_FORMATTER).atStartOfDay())
            .setLocation(new Location(VALID_DESTINATION_DAY_2))
            .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_2))
            .setEventList(new EventList(LocalDate.parse(VALID_STARTDATE_DAY_2_1, DATE_FORMATTER).atStartOfDay()))
            .build();


    //Valid events
    public static final Event VALID_EVENT_1 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_1))
            .setStartDate(LocalDateTime.parse(VALID_STARTDATE_EVENT_1_2, DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(VALID_ENDDATE_EVENT_1_2, DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_1))
            .build();

    public static final Event VALID_EVENT_2 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_2))
            .setStartDate(LocalDateTime.parse(VALID_STARTDATE_EVENT_2_2, DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(VALID_ENDDATE_EVENT_2_2, DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_2))
            .build();

    public static final Event VALID_EVENT_3 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_2 + "1234"))
            .setStartDate(LocalDateTime.parse(VALID_STARTDATE_EVENT_1_1 + " 2100", DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(VALID_ENDDATE_EVENT_1_1 + " 2200", DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_1))
            .setDescription(new Description(VALID_DESCRIPTION_DAY_2))
            .build();


    public static final Event CLASHING_EVENT_1 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_1))
            .setStartDate(LocalDateTime.parse(VALID_STARTDATE_EVENT_1_2, DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(CLASHING_ENDDATE_EVENT_1_1, DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_1))
            .build();

    public static final Event CLASHING_EVENT_2 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_1))
            .setStartDate(LocalDateTime.parse(CLASHING_STARTDATE_EVENT_1_1, DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(VALID_ENDDATE_EVENT_1_2, DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_1))
            .build();

    public static final Event CLASHING_EVENT_3 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_1))
            .setStartDate(LocalDateTime.parse(CLASHING_STARTDATE_EVENT_1_1, DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(CLASHING_ENDDATE_EVENT_1_1, DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_1))
            .build();

    public static final Event CLASHING_EVENT_4 = EventBuilder.newInstance()
            .setName(new Name(VALID_NAME_DAY_1))
            .setStartDate(LocalDateTime.parse(CLASHING_STARTDATE_EVENT_1_2, DATE_TIME_FORMATTER))
            .setEndDate(LocalDateTime.parse(CLASHING_ENDDATE_EVENT_1_2, DATE_TIME_FORMATTER))
            .setLocation(new Location(VALID_DESTINATION_EVENT_1))
            .build();

    // Valid Expenses
    public static final Expense VALID_EXPENSE_1 = ExpenseBuilder.newInstance().setName(new Name(VALID_NAME_EXPENSE_1))
            .setBudget(new Budget(VALID_AMOUNT_EXPENSE_1))
            .setDayNumber(new DayNumber(VALID_DAY_NUMBER_EXPENSE_1))
            .setType(VALID_TYPE_EXPENSE_1)
            .build();
    public static final Expense VALID_EXPENSE_2 = ExpenseBuilder.newInstance().setName(new Name(VALID_NAME_EXPENSE_2))
            .setBudget(new Budget(VALID_AMOUNT_EXPENSE_2))
            .setDayNumber(new DayNumber(VALID_DAY_NUMBER_EXPENSE_2))
            .setType(VALID_TYPE_EXPENSE_2)
            .build();

    // Valid Currency
    public static final CustomisedCurrency VALID_CURRENCY_1 = CustomisedCurrencyBuilder.newInstance()
            .setName(new Name(VALID_NAME_CURRENCY_1))
            .setSymbol(new Symbol(VALID_SYMBOL_CURRENCY_1))
            .setRate(new Rate(VALID_RATE_CURRENCY_1))
            .build();
    public static final CustomisedCurrency VALID_CURRENCY_2 = CustomisedCurrencyBuilder.newInstance()
            .setName(new Name(VALID_NAME_CURRENCY_2))
            .setSymbol(new Symbol(VALID_SYMBOL_CURRENCY_2))
            .setRate(new Rate(VALID_RATE_CURRENCY_2))
            .build();

}
