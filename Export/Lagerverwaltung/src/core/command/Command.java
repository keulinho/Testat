package core.command;

import java.util.Stack;
/**@author Marius Mamsch
**/
public abstract class Command {
	public Command(Stack<Command> commandStack)
	{
		commandStack.clear();
	}
	public abstract void execute();
	public abstract void undo();
	
}
