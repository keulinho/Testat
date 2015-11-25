package core.exception;

import java.util.Stack;

import model.LagerModel;
import model.LagerVerwaltungsModel;

public class AnteilCommand extends Command {
	LagerVerwaltungsModel lagerVM;
	LagerModel lager;
	int anteil;

	/**
	 * 
	 * @param commandStack hier muss der RedoStack übergeben werden
	 * @param lagerVM 	das LAgerVerwaltungsModel
	 * @param lager		das Lager, das einen Anteil erhält
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
		lagerVM.anteilHinzugegen(lager, anteil);
	}

	@Override
	public void undo() {
		lagerVM.anteilloeschen(lager, anteil);
	}

}
