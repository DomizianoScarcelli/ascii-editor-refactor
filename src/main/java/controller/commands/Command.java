package controller.commands;


public interface Command {
    void execute();
    void undo();
}
