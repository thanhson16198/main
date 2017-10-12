package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ClearBookmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new ClearBookmarkCommand object
 */
public class ClearBookmarkCommandParser implements Parser<ClearBookmarkCommand> {

    private final String bookmarkTag = "Bookmarked";
    private final String emptyString = "";

    /**
     * Parses the given {@code String} of arguments in the context of the ClearBookmarkCommand
     * and returns an ClearBookmarkCommand object for execution.
     * @throws ParseException if the user provided any arguments
     */
    public ClearBookmarkCommand parse(String args) throws ParseException {

        Tag toRemove;

        if (!args.trim().equals(emptyString)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearBookmarkCommand.MESSAGE_USAGE));
        }

        try {
            toRemove = new Tag(bookmarkTag);

            return new ClearBookmarkCommand(toRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearBookmarkCommand.MESSAGE_USAGE));
        }
    }

}
