package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DirCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DirCommand object
 */
public class DirCommandParser implements Parser<DirCommand> {

    private final int firstIndex = 0;
    private final int secondIndex = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the DirCommand
     * and returns an DirCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DirCommand parse(String args) throws ParseException {

        //Checking if more than 2 indexes were passed in
        if (args.trim().length() > 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirCommand.MESSAGE_USAGE));
        }

        try {
            Index fromIndex = ParserUtil.parseIndexFromPosition(args, firstIndex);
            Index toIndex = ParserUtil.parseIndexFromPosition(args, secondIndex);
            return new DirCommand(fromIndex, toIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DirCommand.MESSAGE_USAGE));
        }
    }
}
