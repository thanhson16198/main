# thanhson16198
###### \java\seedu\address\commons\events\ui\GotoRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to go to the list of places
 */
public class GotoRequestEvent extends BaseEvent {

    public final int targetIndex;

    public GotoRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\GotoCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.GotoRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.ui.MainWindow;

/**
 * Display the place identified using it's last displayed index from the address book.
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";
    public static final String COMMAND_WORD_ALIAS = "Goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display the place on Google Map identified by the index number used in the last place listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GOTO_SUCCESS = "Go to: %1$s";

    private final Index targetIndex;

    public GotoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPlace> lastShownList = model.getFilteredPlaceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
        }

        ReadOnlyPlace locationToGo = lastShownList.get(targetIndex.getZeroBased());

        //  Open the Google Maps on BrowserPanel
        MainWindow.loadUrl("https://www.google.com.sg/maps/place/"
                + locationToGo.getName().fullName.replaceAll(" ", "+") + "+"
                + locationToGo.getPostalCode().toString().replaceAll(" ", "+"));

        EventsCenter.getInstance().post(new GotoRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_GOTO_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((GotoCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\WeatherCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowWeatherRequestEvent;

/**
 *
 * Format full help instructions for every command for display.
 */
public class WeatherCommand extends Command {
    public static final String COMMAND_WORD = "weather";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows Weather.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_WEATHER_MESSAGE = "Opened Weather on the Browser.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowWeatherRequestEvent());
        return new CommandResult(SHOWING_WEATHER_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case GotoCommand.COMMAND_WORD:
        case GotoCommand.COMMAND_WORD_ALIAS:
            return new GotoCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case WeatherCommand.COMMAND_WORD:
            return new WeatherCommand();
```
###### \java\seedu\address\logic\parser\GotoCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GotoCommand object
 */
public class GotoCommandParser implements Parser<GotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GotoCommand
     * and returns an GotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GotoCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new GotoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    public static void loadUrl(String url) {
        browserPanel.loadPage(url);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the Weather on browser.
     */
    @FXML
    public void handleWeather() {
        logger.info("Open a weather forecast for today on BrowerPanel.");
        browserPanel.loadPage("https://www.accuweather.com/en/sg/singapore/300597/hourly-weather-forecast/300597");
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowWeatherEvent(ShowWeatherRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleWeather();
    }
```
###### \java\seedu\address\ui\PlaceCard.java
``` java
    private static String[] colors = {"silver", "gray", "maroon", "red", "purple", "fuchsia", "green", "lime",
        "olive", "yellow", "navy", "blue", "teal", "aqua"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
```
