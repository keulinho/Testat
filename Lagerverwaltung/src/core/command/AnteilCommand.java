package core.command;

import java.util.Stack;

import model.LagerModel;
import model.LagerVerwaltungsModel;
/**@author Marius Mamsch
**/
public class AnteilCommand extends Command {
	LagerVerwaltungsModel lagerVM;
	LagerModel lager;
	int anteil;

	/**
	 * 
	 * @param commandStack hier muss der RedoStack �bergeben werden
	 * @param lagerVM 	das LAgerVerwaltungsModel
	 * @param lager		das Lager, das einen Anteil erh�lt
	 * @param anteil	der absolute Anteil an der Buchung
	 */
	public AnteilCommand(Stack<Command> commandStack, LagerVerwaltungsModel lagerVM, LagerModel lager, int anteil) {
		super(commandStack);
		this.lagerVM = lagerVM;
		this.lager = lager;
		this.anteil = anteil;
	}

	@Override
	public void execute() {
		lagerVM.hinzufuegenAnteil(lager, anteil);
	}

	@Override
	public void undo() {
		lagerVM.loeschenAnteil(lager, anteil);
	}

}
