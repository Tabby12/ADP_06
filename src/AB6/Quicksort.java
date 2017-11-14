package AB6;
public class Quicksort
{
	public void quicksort(Container[] array, int links, int rechts, Pivotsuchverfahren v) throws Exception
	{

		if (array == null)
		{
			throw new Exception();
		}
		// Abbruch bei weniger als zwei Elementen
		if (rechts - links > 0)
		{
			
			// Init
			int[] pq = new int[2];
			int p, q;

			// Sortiere grob in zwei Partitionen
			pq = partitioniere(array, links, rechts, v);
			p = pq[0];
			q = pq[1];

			// sortiere die Partitionen rekursiv
			quicksort(array, links, p, v);
			quicksort(array, q, rechts, v);
		}
	}

	private static int[] partitioniere(Container[] array, int links, int rechts, Pivotsuchverfahren v) {

		int[] ji = new int[2];
		int i = links;
		int j = rechts;

		// Pivotelement
		Container p = pivotauswahl(array, links, rechts, v);

		while (i <= j) {
			// suche Tauschkandidaten von links
			while (array[i].getKey() < p.getKey()) {
				i++;
			}

			// suche Tauschkandidaten von rechts
			while (array[j].getKey() > p.getKey()) {
				j--;
			}

			// tausche, falls noch nicht fertig sortiert
			if (i <= j) {
				Container tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
				i++;
				j--;
			}
		}
		
		ji[0] = j;
		ji[1] = i;
		return ji;
	}

	private static Container pivotauswahl(Container[] array, int links, int rechts, Pivotsuchverfahren v)
	{
		Container pivot = null;
		switch (v)
		{
		case RECHTS:
			pivot = array[rechts];
			break;
		case MEDIAN:
			pivot = median(array[links], array[(links + rechts) / 2], array[rechts]);
			break;
		case ZUFAELLIG:
			pivot = array[(int) (Math.random() * (rechts - links + 1) + links)];
			break;
		default:
			// nicht m�glich
			break;
		}
		return pivot;
	}

	private static Container median(Container links, Container mitte, Container rechts)
	{
		// ueberprueft, ob mitte der Median ist
		if (mitte.getKey() > rechts.getKey() ^ mitte.getKey() > links.getKey())
		{
			return mitte;
		}

		// �ueberprueft, ob rechts der Median ist
		if (rechts.getKey() > mitte.getKey() ^ rechts.getKey() > links.getKey())
		{
			return rechts;
		}
		// sonst muss es links sein
		return links;
	}
}
