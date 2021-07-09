package model;

import controller.commands.Command;
import java.util.ArrayList;


public class CommandStack {

    private ArrayList<Command> stack = new ArrayList<>();

    public void push(Command command){
        if (stack.size() >= 20) stack.remove(0);
        stack.add(command);
    }

    public Command pop(){
        return stack.remove(stack.size()-1);
    }

    @Override
    public String toString() {
        return "CommandStack{" +
                "stack=" + stack +
                '}';
    }


}
