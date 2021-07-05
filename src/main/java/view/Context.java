package view;

import controller.commands.Command;

public class Context{
    private Command command;

    private static Context instance;

    public static Context getInstance() {
        if (instance == null) instance = new Context();
        return instance;
    }

    private Context(){};

    public void setCommand(Command strategy) {
        this.command = strategy;

    }

    public void executeCommand(){
        this.command.execute();
    }

}
