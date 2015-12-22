package core.command;

import java.util.Stack;
import java.util.Vector;

import model.AnteilModel;
import model.BuchungsModel;
import model.LagerVerwaltungsModel;

/**
 * Command für das Löschen aller Anteile einer Buchung
 *@author Marius Mamsch
 */
public class AlleAnteileCommand extends Command {
	Vector<AnteilModel> anteile;
	LagerVerwaltungsModel lagerVM;
	BuchungsModel laufendeBuchung;
	
	public AlleAnteileCommand(Stack<Command> commandStack, LagerVerwaltungsModel lagerVM) {
		super(commandStack);
		anteile = new Vector<AnteilModel>();
		this.lagerVM = lagerVM;
		this.laufendeBuchung = lagerVM.getLaufendeBuchung();
	}
	
	public void execute() {
		try {
			for (int i = 0; i < laufendeBuchung.getAnteile().size(); i++) {
				anteile.addElement(laufendeBuchung.getAnteile().get(i));
			}
			lagerVM.loeschenAlleAnteile();
		} catch (NullPointerException e) {
			//falls es keine laufende Buchung gibt dann kann das einfach übersprungen werden
		}
	}

	public void undo() {
		for (AnteilModel anteil : anteile) {
			lagerVM.hinzufuegenAnteil(anteil.getLager(), anteil.getAnteil());
		}
	}

}
