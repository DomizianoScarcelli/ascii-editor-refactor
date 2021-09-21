package model;

import controller.commands.Command;

import java.util.ArrayList;

public class CommandStack {
    /**
     * The stack
     */
    private ArrayList<Command> stack = new ArrayList<>();

    /**
     * Add the command to the stack.
     * If the stack size is greater than 20, it removes the last command and pushes the new command to the stack
     *
     * @param command the command that has to be inserted into the stack
     */
    public void push(Command command) {
        if (stack.size() >= 20) stack.remove(0);
        stack.add(command);
    }

    /**
     * Removes and returns the last inserted command from the stack
     *
     * @return the last inserted command
     */
    public Command pop() {
        try {
            return stack.remove(stack.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Pila vuota");
            return new Command() {
                @Override
                public void execute() {

                }

                @Override
                public void undo() {

                }
            };
        }
    }

    /**
     * Return without removing the last inserted command from the stack
     *
     * @return the last inserted command
     */
    public Command peek() {
        try {
            return stack.get(stack.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Pila vuota");
            return new Command() {
                @Override
                public void execute() {

                }

                @Override
                public void undo() {

                }
            };
        }
    }

    public int length() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        return "CommandStack{" +
                "stack=" + stack +
                '}';
    }

}
