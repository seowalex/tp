package chopchop.model;

import javafx.collections.ObservableList;
import chopchop.model.recipe.Recipe;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyRecipeBook {

    /**
     * Returns an unmodifiable view of the recipes list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getRecipeList();

}
