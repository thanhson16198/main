package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Clears all bookmark tags from all persons
 */
public class ClearBookmarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear_bookmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": clears all bookmarks in address book\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_CLEAR_BOOKMARK_SUCCESS = "Cleared bookmarks";

    private final Tag toRemove;

    public ClearBookmarkCommand(Tag toRemove) {
        this.toRemove = toRemove;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        try {
            model.removeAllTags(toRemove);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_CLEAR_BOOKMARK_SUCCESS, toRemove.toString()));
    }
}

