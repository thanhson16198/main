# aungmyin23
###### \java\seedu\address\commons\events\ui\ShowMrtRequestEvent.java
``` java
/**
 * An event requesting to view the mrt page.
 */
public class ShowMrtRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\MrtCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class MrtCommand extends Command {

    public static final String COMMAND_WORD = "mrt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows MRT map.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_MRT_MESSAGE = "Opened mrt window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMrtRequestEvent());
        return new CommandResult(SHOWING_MRT_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
        Email email;
        Phone phone;
        Address address;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_POSTAL_CODE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_POSTAL_CODE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Optional<Phone> optionalPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE));
            if (optionalPhone.isPresent()) {
                phone = optionalPhone.get();
            } else {
                phone = new Phone (null);
            }
            Optional<Email> optionalEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL));
            if (optionalEmail.isPresent()) {
                email = optionalEmail.get();
            } else {
                email = new Email(null);
            }
            Optional<Address> optionalAddress = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS));
            if (optionalAddress.isPresent()) {
                address = optionalAddress.get();
            } else {
                address = new Address(null);
            }
            PostalCode postalcode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE)).get();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case MrtCommand.COMMAND_WORD:
            return new MrtCommand();
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java

        case ContactsCommand.COMMAND_WORD:
        case ContactsCommand.COMMAND_WORD_ALIAS:
            return new ContactsCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> postalcode} into an {@code Optional<PostalCode>} if {@code PostalCode}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<PostalCode> parsePostalCode(Optional<String> postalcode) throws IllegalValueException {
        requireNonNull(postalcode);
        return postalcode.isPresent() ? Optional.of(new PostalCode(postalcode.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\place\Email.java
``` java
        if (email == null) {
            this.value = EMAIL_UNKNOWN;
        } else {
            String trimmedEmail = email.trim();
            if (!isValidEmail(trimmedEmail)) {
                throw new IllegalValueException((MESSAGE_EMAIL_CONSTRAINTS));
            }
            this.value = trimmedEmail;
        }
    }

    /**
     * Returns if a given string is a valid place email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX) || test.equals(EMAIL_UNKNOWN);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\place\PostalCode.java
``` java
/**
 * Represents a place's postal code in the Tourist-book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {


    public static final String MESSAGE_POSTAL_CODE_CONSTRAINTS =
            "Singapore Postal Code can only contain numbers, and should be just 6 digits long";
    public static final String POSTAL_CODE_VALIDATION_REGEX = "\\d{6,6}";
    public final String value;

    /**
     * Validates given postal ccode.
     *
     * @throws IllegalValueException if given postal code string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid postal code of the place.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTAL_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\ui\MrtWindow.java
``` java
/**
 * Controller for a help page
 */
public class MrtWindow extends UiPart<Region> {

    public static final String MRT_FILE_PATH = "/images/MrtMap.jpg";

    private static final Logger logger = LogsCenter.getLogger(MrtWindow.class);
    private static final String ICON = "/images/mrt_logo.png";
    private static final String FXML = "MrtMapWindow.fxml";
    private static final String TITLE = "MRT Map";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public MrtWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true);
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String mrtMapUrl = getClass().getResource(MRT_FILE_PATH).toString();
        browser.getEngine().load(mrtMapUrl);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing MRT map of Singapore.");
        dialogStage.showAndWait();
    }
}
```
