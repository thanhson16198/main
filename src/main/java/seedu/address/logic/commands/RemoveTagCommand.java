package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove_tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the tag from all persons in address book\n"
            + "Parameters: TAG_NAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Removed: %1$s";

    private final Tag toRemove;

    public RemoveTagCommand(Tag toRemove) {
        this.toRemove = toRemove;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        try {
            model.removeTag(toRemove);
        } catch (DuplicatePersonException dpe) {

        } catch (PersonNotFoundException pnfe) {

        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, toRemove.toString()));
    }
}

