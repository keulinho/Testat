package core.utils;

import java.util.List;

import model.BuchungsModel;
import model.LagerModel;

public class Sortierer {

	SortierStrategie sort;
	
	/**
	 * erzeugt einen neuen Sortierer
	 * @param sort Sortierstrategie die angewendet werden soll
	 */
	public Sortierer(SortierStrategie sort){
		this.sort = sort;
	}
	/**
	 * sortiert die gegebene Liste anhand der aktuell gesetzten Sortiersatrategie
	 * @param buchungsListe Liste die sortiert werden soll
	 * @param lager Lager dessen Buchungen sortiert werden sollen
	 * @return sortierte Buchungsliste
	 */
	public List<BuchungsModel> sortiere(List<BuchungsModel> buchungsListe, LagerModel lager) {
		for (BuchungsModel buchung : buchungsListe) {
			for (int i = 0; i < buchung.getAnteile().size();i++) {
				if(!buchung.getAnteile().get(i).getLager().equals(lager)) {
					buchung.getAnteile().remove(buchung.getAnteile().get(i));
				}
			}		
		}
		for (int i = 0; i < buchungsListe.size()-1;i++) {
			int index=i;
			for (int n = i+1; n < buchungsListe.size();n++) {
				if (!sort.sortiere(buchungsListe.get(index), buchungsListe.get(n))) {
					index=n;
				}
			}
			if (index!=i) {
				BuchungsModel temp=buchungsListe.get(i);
				buchungsListe.set(i, buchungsListe.get(index));
				buchungsListe.set(index, temp);
			}
			
		}
		return buchungsListe;
	}
	
	/**
	 * gibt Sortierstrategie zurück
	 * @return momentane Sortierstrategie
	 */
	public SortierStrategie getSort() {
		return sort;
	}
	/**
	 * ändert die Sortierstrategie in die angegebene
	 * @param sort neue Sortierstrategie
	 */
	public void setSort(SortierStrategie sort) {
		this.sort = sort;
	}
	
	
}
