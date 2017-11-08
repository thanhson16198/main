# huyuanrong
###### \java\seedu\address\logic\commands\ContactsCommandTest.java
``` java
public class ContactsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new ContactsCommand().execute();
        assertEquals(SHOWING_CONTACT_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowContactsEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_TAG_PREFIX
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\model\place\NameTest.java
``` java
        assertTrue(Name.isValidName("ëëëëéééé")); //Weird Characters
```
