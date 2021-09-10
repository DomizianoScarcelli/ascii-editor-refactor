package model;

import controller.commands.Command;

import java.util.ArrayList;


public class CommandStack {


    private ArrayList<Command> stack = new ArrayList<>();

    public void push(Command command) {
        if (stack.size() >= 20) stack.remove(0);
        stack.add(command);
    }

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
