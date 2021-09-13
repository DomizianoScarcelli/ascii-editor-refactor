package controller.commands;

/**
 * A command is a class that does an action on the ascii panel and can undo the action
 */
public interface Command {
    /**
     * Execute the command
     */
    void execute();

    /**
     * Undoes the action executed
     */
    void undo();
}
