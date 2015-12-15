package core.sortierer;

import java.util.List;

import model.BuchungsModel;
import model.LagerModel;

public class Sortierer {

	SortierStrategie sort;
	
	public Sortierer(SortierStrategie sort){
		this.sort = sort;
	}

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
	
	
	public SortierStrategie getSort() {
		return sort;
	}

	public void setSort(SortierStrategie sort) {
		this.sort = sort;
	}
	
	
}
