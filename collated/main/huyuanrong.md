# huyuanrong
###### \java\seedu\address\commons\events\ui\ShowContactsEvent.java
``` java
/**
 * An event requesting to view the contacts page.
 */
public class ShowContactsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\ContactsCommand.java
``` java
/**
 * Format useful contact instructions for every command for display.
 */
public class ContactsCommand extends Command {

    public static final String COMMAND_WORD = "emergency";
    public static final String COMMAND_WORD_ALIAS = "sos";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows useful contacts.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CONTACT_MESSAGE = "Opened contacts window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowContactsEvent());
        return new CommandResult(SHOWING_CONTACT_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String COMMAND_WORD_NAME_PREFIX = "n/";
    public static final String COMMAND_WORD_TAG_PREFIX = "t/";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all places whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + COMMAND_WORD_NAME_PREFIX + "Marina Bay Sands"
            + "Example: " + COMMAND_WORD + COMMAND_WORD_TAG_PREFIX + "[bookmark] [attractions]";
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ContactsCommand.COMMAND_WORD:
        case ContactsCommand.COMMAND_WORD_ALIAS:
            return new ContactsCommand();
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
        final Pattern prefixFormat = Pattern.compile("(?<prefix>\\w/)(?<arguments>.*)");
        final Matcher prefixMatcher = prefixFormat.matcher(trimmedArgs);

        if (!prefixMatcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        /* Group the prefixes together and the arguments together */
        final String prefix = prefixMatcher.group("prefix");
        final String arguments = prefixMatcher.group("arguments");

        String[] keywords = arguments.split("\\s+");

        switch (prefix) {

        case COMMAND_WORD_NAME_PREFIX:
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));

        case COMMAND_WORD_TAG_PREFIX:
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
```
###### \java\seedu\address\model\place\TagContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPlace}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<ReadOnlyPlace> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPlace place) {
        Set<Tag> tags = place.getTags();
        List<Tag> tagsList = tags.stream().collect(Collectors.toList());
        return keywords.stream()
                .anyMatch(keyword -> tagsList.stream().anyMatch(tag ->
                        keyword.contains(tag.tagName.toString())) & true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
                new Place(new Name("Marina Bay Sands"), new Phone("66888868"),
                        new Website("http://www.marinabaysands.com/"),
                    new Address("10 Bayfront Avenue"), new PostalCode("018956"),
                    getTagSet("attractions")),
                new Place(new Name("Singapore Flyer"), new Phone("6333311"),
                        new Website("http://www.singaporeflyer.com/"),
                    new Address("30 Raffles Ave"), new PostalCode("039803"),
                    getTagSet("attractions")),
                new Place(new Name("Singapore Zoo"), new Phone("62693411"),
                        new Website("http://www.wrs.com.sg/"),
                    new Address("80 Mandai Lake Rd"), new PostalCode("729826"),
                    getTagSet("attractions")),
                new Place(new Name("Beni Singapore"), new Phone("91593177"),
                        new Website("http://www.beni-sg.com/"),
                    new Address("333A Orchard Rd, #02-37"), new PostalCode("238897"),
                    getTagSet("onestar")),
                new Place(new Name("Odette"), new Phone("63850498"),
                        new Website("http://www.odetterestaurant.com/"),
                    new Address("1 Saint Andrew's Rd, #01-04, National Gallery"), new PostalCode("178957"),
                    getTagSet("twostars")),
                new Place(new Name("Joël Robuchon Restaurant"), new Phone("65777888"),
                        new Website("https://www.rwsentosa.com/en"),
                    new Address("26 Sentosa Gateway, Hotel Michael, #01-104 and 105"), new PostalCode("098138"),
                    getTagSet("threestars"))
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath + "-backup.xml");
    }
```
###### \java\seedu\address\ui\ContactWindow.java
``` java
/**
 * Controller for a Useful Contacts page
 */
public class ContactWindow extends UiPart<Region> {

    public static final String USERGUIDE_FILE_PATH = "/docs/UsefulContacts.html";

    private static final Logger logger = LogsCenter.getLogger(ContactWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "ContactWindow.fxml";
    private static final String TITLE = "Useful Numbers";

    @FXML
    private WebView browser;

    private final Stage dialogStage;

    public ContactWindow() {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        FxViewUtil.setStageIcon(dialogStage, ICON);

        String usefulNumbersUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(usefulNumbersUrl);
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
        logger.fine("Showing useful numbers to note in Singapore.");
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private MenuItem contactsMenuItem;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        setAccelerator(contactsMenuItem, KeyCombination.valueOf("F2"));
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the useful contacts window.
     */
    @FXML
    public void showNumbers() {
        ContactWindow contactWindow = new ContactWindow();
        contactWindow.show();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowContactsEvent(ShowContactsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showNumbers();
    }
```
###### \resources\view\ContactWindow.fxml
``` fxml

<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.web.WebView?>

<StackPane fx:id="contactWindowRoot" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser" />
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
      <MenuItem fx:id="contactsMenuItem" mnemonicParsing="false" onAction="#showNumbers" text="Useful Numbers" />
```
###### \touristbook.xml
``` xml
<touristbook>
    <places>
        <name>Marina Bay Sands</name>
        <phone>66888868</phone>
        <wesite>https://www.marinabaysands.com</wesite>
        <address>10 Bayfront Avenue</address>
        <postalcode>018956</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Singapore Flyer</name>
        <phone>63333311</phone>
        <website>https://www.singaporeflyer.com</website>
        <address>30 Raffles Ave</address>
        <postalcode>039803</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Gardens by the Bay</name>
        <phone>64206848</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>18 Marina Gardens Drive</address>
        <postalcode>018953</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Singapore Botanic Gardens</name>
        <phone>64717138</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>1 Cluny Rd</address>
        <postalcode>259569</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Singapore Zoo</name>
        <phone>62693411</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>80 Mandai Lake Rd</address>
        <postalcode>729826</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Universal Studios Singapore</name>
        <phone>65778888</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>8 Sentosa Gateway</address>
        <postalcode>098269</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Jurong Bird Park</name>
        <phone>62693411</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>2 Jurong Hill</address>
        <postalcode>628925</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>ArtScience Museum</name>
        <phone>66888868</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>6 Bayfront Ave</address>
        <postalcode>018974</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Science Centre Singapore</name>
        <phone>64252500</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>15 Science Centre Rd</address>
        <postalcode>609081</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Night Safari Singapore</name>
        <phone>62693411</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>80 Mandai Lake Rd</address>
        <postalcode>729826</postalcode>
        <tags>attractions</tags>
    </places>
    <places>
        <name>Alma by Juan Amador</name>
        <phone>67359937</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>22 Scotts Rd</address>
        <postalcode>228221</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Bacchanalia</name>
        <phone>91794552</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>39 Hongkong Street</address>
        <postalcode>059678</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Beni Singapore</name>
        <phone>91593177</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>333A Orchard Road, #02-37 Mandarin Gallery</address>
        <postalcode>238897</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Candlenut Kitchen</name>
        <phone>63041415</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>17A Dempsey Road</address>
        <postalcode>249676</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Cut by Wolfgang Puck</name>
        <phone>66888517</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>2 Bayfront Avenue, #B1-71, Galleria Level The Shoppes at Marina Bay Sands</address>
        <postalcode>018972</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Crystal Jade Golden Palace Restaurant</name>
        <phone>67346866</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>290 Orchard Rd, #05-22, Paragon Shopping Centre</address>
        <postalcode>238859</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Corner House</name>
        <phone>64691000</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>1 Cluny Road, E J H Corner House Singapore Botanic Gardens</address>
        <postalcode>259569</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Forest</name>
        <phone>65777788</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>16 Sentosa Gateway, Equarius Hotel, #01-521 and 522</address>
        <postalcode>098133</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>JAAN</name>
        <phone>68373322</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>2 Stomford Rd, Level 70, Equinox Complex</address>
        <postalcode>178882</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Hong Kong Soya Sauce Chicken Rice and Noodle</name>
        <phone>000</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>Blk 335, Smith Street #02-136, Chinatown Food Complex</address>
        <postalcode>050335</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Hill Street Tai Hwa Pork Noodles</name>
        <phone>000</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>Blk 466 Crawford Lane, Tai Hwa Eating House #01-12</address>
        <postalcode>190465</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Lei Garden</name>
        <phone>63393822</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>30 Victoria St</address>
        <postalcode>187996</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Osia Steak and Seafood Grill</name>
        <phone>65776560</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>26 Sentosa Gateway, Festive Walk, #02-140 and 141</address>
        <postalcode>098138</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>PUTIEN</name>
        <phone>62956358</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>127 Kitchener Rd</address>
        <postalcode>208514</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Rhubarb Le Restaurant</name>
        <phone>81275001</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>3 Duxton Hill</address>
        <postalcode>089589</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Shinji by Kanesaka</name>
        <phone>63386131</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address> 76 Bras Basah Rd, Lobby Floor, Carlton Hotel</address>
        <postalcode>189558</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Summer Pavilion</name>
        <phone>64345286</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>7 Raffles Ave, The Ritz-Carlton Millenia, Singapore</address>
        <postalcode>039799</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Ginza Sushi Ichi</name>
        <phone>62355514</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>320 Orchard Rd, #01-04 Singapore Marriott Tang Plaza Hotel</address>
        <postalcode>238865</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>terra Tokyo Italian</name>
        <phone>62215159</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>54 Tras St</address>
        <postalcode>078993</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>The Song Of India</name>
        <phone>68360055</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>33 Scotts Rd</address>
        <postalcode>228226</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Waku Ghin</name>
        <phone>66888507</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>2 Bayfront Avenue, Level 2 Dining, L2-01, The Shoppes at Marina Bay Sands</address>
        <postalcode>018956</postalcode>
        <tags>onestar</tags>
    </places>
    <places>
        <name>Restaurant André</name>
        <phone>65348880</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>41 Bukit Pasoh Rd</address>
        <postalcode>089855</postalcode>
        <tags>twostars</tags>
    </places>
    <places>
        <name>L Atelier de Joël Robuchon</name>
        <phone>65777888</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>8 Sentosa Gateway</address>
        <postalcode>098269</postalcode>
        <tags>twostars</tags>
    </places>
    <places>
        <name>Les Amis</name>
        <phone>67332225</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>1 Scotts Road, #01-16 Shaw Centre</address>
        <postalcode>228208</postalcode>
        <tags>twostars</tags>
    </places>
    <places>
        <name>Odette</name>
        <phone>63850498</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>1 Saint Andrew's Road, #01-04, National Gallery</address>
        <postalcode>178957</postalcode>
        <tags>twostars</tags>
    </places>
    <places>
        <name>Shisen Hanten</name>
        <phone>68316262</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>333 Orchard Rd, Mandarin Orchard Singapore</address>
        <postalcode>238867</postalcode>
        <tags>twostars</tags>
    </places>
    <places>
        <name>Shoukouwa Restaurant</name>
        <phone>64239939</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>1 Fullerton Rd, #02-02A, One Fullerton</address>
        <postalcode>238867</postalcode>
        <tags>twostars</tags>
    </places>
    <places>
        <name>Joël Robuchon Restaurant</name>
        <phone>65777888</phone>
        <website>https://www.gardensbythebay.com.sg</website>
        <address>26 Sentosa Gateway, Hotel Michael, #01-104 and 105</address>
        <postalcode>098138</postalcode>
        <tags>threestars</tags>
    </places>
    <tags>bookmarks</tags>
    <tags>onestar</tags>
    <tags>twostars</tags>
    <tags>threestars</tags>
    <tags>attractions</tags>
</touristbook>
```
