# aungmyin23
###### \java\seedu\address\logic\commands\MrtCommandTest.java
``` java
public class MrtCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_mrt_success() {
        CommandResult result = new MrtCommand().execute();
        assertEquals(SHOWING_MRT_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowMrtRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\model\place\PostalCodeTest.java
``` java
public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid postal code
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("91")); // less than 3 numbers
        assertFalse(PostalCode.isValidPostalCode("phone")); // non-numeric
        assertFalse(PostalCode.isValidPostalCode("9011p041")); // alphabets within digits
        assertFalse(PostalCode.isValidPostalCode("9312 1534")); // spaces within digits
        assertFalse(PostalCode.isValidPostalCode("911")); // exactly 3 number

        // valid postal code
        assertTrue(PostalCode.isValidPostalCode("931215"));
    }
}
```
