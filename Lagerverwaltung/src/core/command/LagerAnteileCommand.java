package core.command;

import java.util.Stack;
import java.util.Vector;

import model.AnteilModel;
import model.BuchungsModel;
import model.LagerModel;
import model.LagerVerwaltungsModel;

public class LagerAnteileCommand extends Command {
	Vector<AnteilModel> anteile;
	LagerVerwaltungsModel lagerVM;
	BuchungsModel laufendeBuchung;
	LagerModel lager;
	
	public LagerAnteileCommand(Stack<Command> commandStack, LagerVerwaltungsModel lagerVM, LagerModel lager) {
		super(commandStack);
		anteile = new Vector<AnteilModel>();
		this.lagerVM = lagerVM;
		this.laufendeBuchung = lagerVM.getLaufendeBuchung();
		this.lager = lager;
	}

	public void execute() {
		for (AnteilModel anteil : laufendeBuchung.getAnteile()) {
			if(anteil.getLager() == lager) {
				anteile.add(anteil);
			}
		}
		lagerVM.loeschenAnteile(lager);
	}

	public void undo() {
		for (AnteilModel anteil : anteile) {
			lagerVM.hinzugegenAnteil(anteil.getLager(), anteil.getAnteil());
		}
	}

}
