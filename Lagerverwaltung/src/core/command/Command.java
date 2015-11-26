package core.command;

import java.util.Stack;

public abstract class Command {
	public Command(Stack<Command> commandStack)
	{
		commandStack.clear();
	}
	public abstract void execute();
	public abstract void undo();
	
}
