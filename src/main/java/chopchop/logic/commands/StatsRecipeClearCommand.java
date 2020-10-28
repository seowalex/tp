package chopchop.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import chopchop.logic.commands.exceptions.CommandException;
import chopchop.logic.history.HistoryManager;
import chopchop.model.Model;
import chopchop.model.UsageList;
import chopchop.model.usage.RecipeUsage;

public class StatsRecipeClearCommand extends Command implements Undoable {
    public static final String COMMAND_WORD = "stats recipe clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : deletes all saved"
        + "records of recipe usages from ChopChop. It can be undone but cannot be restored.";

    private UsageList<RecipeUsage> usages = new UsageList<>();

    /**
     * Executes the command and returns the result message.
     *
     * @param model          {@code Model} which the command should operate on.
     * @param historyManager {@code History} which the command should record to.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, HistoryManager historyManager) throws CommandException {
        requireNonNull(model);
        try {
            this.usages = model.getRecipeUsageList();
            model.setRecipeUsageList(new UsageList<>());
        } catch (Exception e) {
            return CommandResult.error("Unable to clear records of recipes made");
        }
        return CommandResult.statsMessage(new ArrayList<>(), "All records of recipes made cleared!");
    }

    /**
     * Undo the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.setRecipeUsageList(this.usages);
            this.usages.setAll(new UsageList<>()); //don't think need to clear but just in case
        } catch (Exception e) {
            return CommandResult.error("Unable to restore records of recipes made");
        }
        return CommandResult.message("Undo: restored records of recipes made");
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof StatsRecipeClearCommand
            && this.usages.equals(((StatsRecipeClearCommand) other).usages));
    }

    @Override
    public String toString() {
        return String.format("StatsRecipeClearCommand");
    }
}