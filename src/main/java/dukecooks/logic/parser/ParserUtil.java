package dukecooks.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dukecooks.commons.core.index.Index;
import dukecooks.commons.util.StringUtil;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.Image;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;
import dukecooks.model.diary.components.DiaryName;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;
import dukecooks.model.health.components.Remark;
import dukecooks.model.health.components.Timestamp;
import dukecooks.model.health.components.Type;
import dukecooks.model.health.components.Value;
import dukecooks.model.mealplan.components.MealPlanName;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Weight;
import dukecooks.model.recipe.components.Calories;
import dukecooks.model.recipe.components.Carbs;
import dukecooks.model.recipe.components.Fats;
import dukecooks.model.recipe.components.Ingredient;
import dukecooks.model.recipe.components.Protein;
import dukecooks.model.recipe.components.RecipeName;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String filePath} into a {@code Image}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filePath} is invalid.
     */
    public static Image parseImage(String filePath) throws ParseException {
        requireNonNull(filePath);
        String trimmedFilePath = filePath.trim();

        if (!Image.isValidImage(trimmedFilePath)) {
            throw new ParseException(Image.MESSAGE_CONSTRAINTS);
        }

        return new Image(trimmedFilePath);
    }



    /**
     * Parses a {@code String name} into a {@code RecipeName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static RecipeName parseRecipeName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!RecipeName.isValidName(trimmedName)) {
            throw new ParseException(RecipeName.MESSAGE_CONSTRAINTS);
        }
        return new RecipeName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code MealPlanName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static MealPlanName parseMealPlanName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!MealPlanName.isValidName(trimmedName)) {
            throw new ParseException(MealPlanName.MESSAGE_CONSTRAINTS);
        }
        return new MealPlanName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code DashboardName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DashboardName} is invalid.
     */
    public static DashboardName parseDashboardName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new DashboardName(trimmedName);
    }

    /**
     * Parses a {@code String date} into a {@code TaskDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code TaskDate} is invalid.
     */
    public static TaskDate parseTaskDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!TaskDate.isValidTaskDate(trimmedDate)) {
            throw new ParseException(TaskDate.MESSAGE_CONSTRAINTS);
        }
        return new TaskDate(trimmedDate);
    }

    /**
     * Parses a {@code String name} into a {@code DiaryName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static DiaryName parseDiaryName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!DiaryName.isValidName(trimmedName)) {
            throw new ParseException(DiaryName.MESSAGE_CONSTRAINTS);
        }
        return new DiaryName(trimmedName);
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String description} into a {@code PageDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code PageDescription} is invalid.
     */
    public static PageDescription parsePageDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!PageDescription.isValidPageDescription(trimmedDescription)) {
            throw new ParseException(PageDescription.MESSAGE_CONSTRAINTS);
        }
        return new PageDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String pageType} into a {@code PageType}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pageType} is invalid.
     */
    public static PageType parsePageType(String pageType) throws ParseException {
        requireNonNull(pageType);
        String trimmedTitle = pageType.trim();
        if (!PageType.isValidPageType(trimmedTitle)) {
            throw new ParseException(PageType.MESSAGE_CONSTRAINTS);
        }
        return new PageType(trimmedTitle);
    }



    /**
     * Parses a {@code String dateOfBirth} into a {@code DoB}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static DoB parseDoB(String dateOfBirth) throws ParseException {
        requireNonNull(dateOfBirth);
        String trimmedDoB = dateOfBirth.trim();
        if (!DoB.isValidDate(trimmedDoB)) {
            throw new ParseException(DoB.MESSAGE_CONSTRAINTS);
        }
        return new DoB(dateOfBirth);
    }

    /**
     * Parses a {@code String value} into a {@code Value}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code value} is invalid.
     */
    public static Value parseValue(String value) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        if (!Value.isValidNumber(trimmedValue)) {
            throw new ParseException(Value.MESSAGE_CONSTRAINTS);
        }
        return new Value(value);
    }

    /**
     * Parses a {@code String remark} into a {@code remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemarkName(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses {@code Collection<String> remarks} into a {@code Set<Remark>}.
     */
    public static Set<Remark> parseRemarks(Collection<String> remarks) throws ParseException {
        requireNonNull(remarks);
        final Set<Remark> remarkSet = new HashSet<>();
        for (String remarkName : remarks) {
            remarkSet.add(parseRemark(remarkName));
        }
        return remarkSet;
    }

    /**
     * Parses a {@code String ingredient} into a {@code Ingredient}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ingredient} is invalid.
     */
    public static Ingredient parseIngredient(String ingredient) throws ParseException {
        requireNonNull(ingredient);
        String trimmedIngredient = ingredient.trim();
        if (!Ingredient.isValidIngredientName(trimmedIngredient)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS);
        }
        return new Ingredient(trimmedIngredient);
    }

    /**
     * Parses {@code Collection<String> ingredients} into a {@code Set<Ingredient>}.
     */
    public static Set<Ingredient> parseIngredients(Collection<String> ingredients) throws ParseException {
        requireNonNull(ingredients);
        final Set<Ingredient> ingredientSet = new HashSet<>();
        for (String ingredientName : ingredients) {
            ingredientSet.add(parseIngredient(ingredientName));
        }
        return ingredientSet;
    }

    /**
     * Parses a {@code String calories} into a {@code Calories}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code calories} is invalid.
     */
    public static Calories parseCalories(String calories) throws ParseException {
        requireNonNull(calories);
        String trimmedCalories = calories.trim();
        if (!Calories.isValidCalories(trimmedCalories)) {
            throw new ParseException(Calories.MESSAGE_CONSTRAINTS);
        }
        return new Calories(trimmedCalories);
    }

    /**
     * Parses a {@code String carbs} into a {@code Carbs}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code carbs} is invalid.
     */
    public static Carbs parseCarbs(String carbs) throws ParseException {
        requireNonNull(carbs);
        String trimmedCarbs = carbs.trim();
        if (!Carbs.isValidCarbs(trimmedCarbs)) {
            throw new ParseException(Carbs.MESSAGE_CONSTRAINTS);
        }
        return new Carbs(trimmedCarbs);
    }

    /**
     * Parses a {@code String fats} into a {@code Fats}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fats} is invalid.
     */
    public static Fats parseFats(String fats) throws ParseException {
        requireNonNull(fats);
        String trimmedFats = fats.trim();
        if (!Fats.isValidFats(trimmedFats)) {
            throw new ParseException(Fats.MESSAGE_CONSTRAINTS);
        }
        return new Fats(trimmedFats);
    }

    /**
     * Parses a {@code String protein} into a {@code Protein}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code protein} is invalid.
     */
    public static Protein parseProtein(String protein) throws ParseException {
        requireNonNull(protein);
        String trimmedProtein = protein.trim();
        if (!Protein.isValidProtein(trimmedProtein)) {
            throw new ParseException(Protein.MESSAGE_CONSTRAINTS);
        }
        return new Protein(trimmedProtein);
    }

    /**
     * Parses a {@code String timestamp} into a {@code Timestamp}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be in upper case.
     *
     * @throws ParseException if the given {@code timestamp} is invalid.
     */
    public static Timestamp parseTimestamp(String timestamp) throws ParseException {
        requireNonNull(timestamp);
        String trimmedTimestamp = timestamp.trim();
        String upperTimestamp = trimmedTimestamp.toUpperCase();
        if (!Timestamp.isValidDateTime(upperTimestamp)) {
            throw new ParseException(Timestamp.MESSAGE_CONSTRAINTS);
        }

        if (!Timestamp.isValidDateTimePeriod(upperTimestamp)) {
            throw new ParseException(Timestamp.MESSAGE_DATETIME_GREATER_THAN_NOW);
        }
        return new Timestamp(upperTimestamp);
    }

    /**
     * Parses a {@code String bloodGroup} into a {@code BloodType}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be in upper case.
     *
     * @throws ParseException if the given {@code bloodGroup} is invalid.
     */
    public static BloodType parseBloodType(String bloodGroup) throws ParseException {
        requireNonNull(bloodGroup);
        String trimmedBloodGroup = bloodGroup.trim();
        String upperBloodGroup = trimmedBloodGroup.toUpperCase();
        if (!BloodType.isValidBloodType(upperBloodGroup)) {
            throw new ParseException(BloodType.MESSAGE_CONSTRAINTS);
        }
        return new BloodType(upperBloodGroup);
    }

    /**
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     * String will be in lower case.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        String lowerGender = trimmedGender.toLowerCase();
        if (!Gender.isValidGender(lowerGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return new Gender(lowerGender);
    }

    /**
     * Parses a {@code String weight} into a {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws ParseException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!Weight.isValidNumber(trimmedWeight)) {
            throw new ParseException(Weight.MESSAGE_CONSTRAINTS);
        }

        if (!Weight.isValidWeight(Double.parseDouble(trimmedWeight))) {
            throw new ParseException(Weight.MESSAGE_INFLATED_WEIGHT);
        }
        return new Weight(trimmedWeight);
    }

    /**
     * Parses a {@code String height} into a {@code Height}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code height} is invalid.
     */
    public static Height parseHeight(String height) throws ParseException {
        requireNonNull(height);
        String trimmedHeight = height.trim();
        if (!Height.isValidNumber(trimmedHeight)) {
            throw new ParseException(Height.MESSAGE_CONSTRAINTS);
        }

        if (!Height.isValidHeight(Double.parseDouble(trimmedHeight))) {
            throw new ParseException(Height.MESSAGE_INFLATED_HEIGHT);
        }
        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code String medicalHistory} into a {@code MedicalHistory}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medicalHistory} is invalid.
     */
    public static MedicalHistory parseMedicalHistory(String medicalHistory) throws ParseException {
        requireNonNull(medicalHistory);
        String trimmedTag = medicalHistory.trim();
        if (!MedicalHistory.isValidMedicalHistoryName(trimmedTag)) {
            throw new ParseException(MedicalHistory.MESSAGE_CONSTRAINTS);
        }
        return new MedicalHistory(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> medicalHistories} into a {@code Set<MedicalHistory>}.
     */
    public static Set<MedicalHistory> parseMedicalHistories(Collection<String> medicalHistories) throws ParseException {
        requireNonNull(medicalHistories);
        final Set<MedicalHistory> medicalHistorySet = new HashSet<>();
        for (String tagName : medicalHistories) {
            medicalHistorySet.add(parseMedicalHistory(tagName));
        }
        return medicalHistorySet;
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!Type.isValidType(trimmedType)) {
            throw new ParseException(Type.messageConstraints());
        }
        String formattedType = StringUtil.capitalizeFirstLetterOnly(trimmedType);
        return Type.valueOf(formattedType);
    }

    /**
     * Parses a {@code String recipe} into a {@code RecipeName} for addition into a meal plan.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code recipe} is invalid.
     */
    public static RecipeName parseRecipe(String recipe) throws ParseException {
        requireNonNull(recipe);
        String trimmedRecipe = recipe.trim();
        if (!RecipeName.isValidName(trimmedRecipe)) {
            throw new ParseException(RecipeName.MESSAGE_CONSTRAINTS);
        }

        return new RecipeName(trimmedRecipe);
    }

    /**
     * Parses {@code Collection<String> recipes} into a {@code List<RecipeName>}.
     */
    public static List<RecipeName> parseRecipes(Collection<String> recipes) throws ParseException {
        requireNonNull(recipes);
        final List<RecipeName> recipeList = new ArrayList<>();
        for (String recipeName : recipes) {
            recipeList.add(parseRecipe(recipeName));
        }
        return recipeList;
    }
}
