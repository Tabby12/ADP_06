package AB6;

public class QuickThreadsort extends Thread {

	private Container[] array;

	private int links;

	private int rechts;

	private Pivotsuchverfahren v;

	public QuickThreadsort(Container[] array, int links, int rechts, Pivotsuchverfahren v) {
		this.array = array;
		this.links = links;
		this.rechts = rechts;
		this.v = v;
	}
	
	/**
	 * Zum Testen
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		int size = 10000;
		Container[] containerArr = erzeugeWerte(size);
		Container[] containerArr2 = new Container[size];
			for (int i = 0; i < size; i++)
			{
				containerArr2[i] = containerArr[i];
			}

		

			System.out.println("Quicksort:");
			Quicksort qsort = new Quicksort();
			
			long time = System.nanoTime();
			qsort.quicksort(containerArr, 0, containerArr.length - 1, Pivotsuchverfahren.MEDIAN);
			long time2 = System.nanoTime() - time;
			

//			for (int i = 0; i < containerArr.length; i++) {
//				System.out.print(containerArr[i].getKey() + " ");
//			}
			System.out.println();
			System.out.println("Die vergangene Zeit ist " + time2);
			
			//Zeitmessung f�r Quicksort ende
			
			//Zeitmessung f�r Ultisort 
			//Threadsort a = new Threadsort(containerArr2, 0, containerArr2.length - 1, Pivotsuchverfahren.MEDIAN);

			System.out.println("Ultisort:");
			
			time = System.nanoTime();
			ultisort(containerArr2, 0, containerArr2.length - 1, Pivotsuchverfahren.MEDIAN);
			time2 = System.nanoTime() - time;
			
//			for (int i = 0; i < containerArr2.length; i++) {
//				System.out.print(containerArr2[i].getKey() + " ");
//			}
			System.out.println();
			System.out.println("Die vergangene Zeit ist " + time2);
			//Zeitmessung f�r Quicksort ende
//		}
		}

	
	@Override
	public void run() {
		// Abbruch bei weniger als zwei Elementen
		if (rechts - links > 0) {

			// Init
			int[] pq = new int[2];
			int p, q;

			// Sortiere grob in zwei Partitionen
			pq = partitioniere(array, links, rechts, v);
			p = pq[0];
			q = pq[1];

			try {
				// Sortieren �ber rekursive Aufrufe
				ultiQuicksort(array, links, p, v);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			try {
				ultiQuicksort(array, q, rechts, v);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	}

	public static Container[] insertionsort(Container[] arr, int links, int rechts) {
		if (rechts - links > 0) {
			Container zusortieren;
			int j;
			for (int i = links + 1; i <= rechts; i++) {
				zusortieren = arr[i];
				j = i;
				
				while (j > 0 && arr[j - 1].getKey() > zusortieren.getKey()) 
				{
					arr[j] = arr[j - 1];
					arr[j - 1] = zusortieren;
					j--;
				}
			}
		}
		return arr;
	}

	/**
	 * @throws Exception
	 * 
	 */
	public static void ultisort(Container[] array, int links, int rechts, Pivotsuchverfahren v) throws Exception {
		if (array == null) {
			throw new Exception();
		}
		// Abbruch bei weniger als zwei Elementen
		if (rechts - links > 0) {
			
			if (rechts - links > 75000) {
				int[] pq = new int[2];
				int p, q;

				// Sortiere grob in zwei Partitionen
				pq = partitioniere(array, links, rechts, v);
				p = pq[0];
				q = pq[1];

				// Starte Threads um parallel zwei Partionen zu sortieren.
				QuickThreadsort thread = new QuickThreadsort(array, links, p, v);
				QuickThreadsort thread2 = new QuickThreadsort(array, q, rechts, v);
				thread.start();
				thread2.start();
				thread.join();
				thread2.join();
			}
			else
			{
				ultiQuicksort(array, links, rechts, v);
			}
		}
		

	}
	
	/**
	 * identisch zu quicksort
	 * 
	 * @param array
	 * @param links
	 * @param rechts
	 * @param v
	 * @throws Exception
	 */
	public static void ultiQuicksort(Container[] array, int links, int rechts, Pivotsuchverfahren v) throws Exception {

		if (array == null) {
			throw new Exception("array darf nicht null sein");
		}
		// Abbruch bei weniger als zwei Elementen
		if (rechts - links > 0) {
			//Insertionsort bei weniger als 31 Elementen
			if((rechts - links) > 30)
			{
				int[] pq = new int[2];
				int p, q;

				// Sortiere grob in zwei Partitionen
				pq = partitioniere(array, links, rechts, v);
				p = pq[0];
				q = pq[1];

				// sortiere die Partitionen rekursiv
				ultiQuicksort(array, links, p, v);
				ultiQuicksort(array, q, rechts, v);
			}
			else
			{
				insertionsort(array, links, rechts);
			}

		
		}
	}

	/**
	 * Sortiert grob in zwei Partionen.
	 * 
	 * @param array
	 *            Feld
	 * @param links
	 *            Bereichsgrenze
	 * @param rechts
	 *            Bereichsgrenze
	 * @param v
	 *            Pivotsuchverfahren
	 * @return Partionsgrenzen
	 */
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



	/**
	 * 
	 * @param array
	 *            Feld
	 * @param links
	 *            Bereichgrenze
	 * @param rechts
	 *            Bereichgrenze
	 * @param v
	 *            Pivotsuchverfahren
	 * @return pivotelement
	 */
	private static Container pivotauswahl(Container[] array, int links, int rechts, Pivotsuchverfahren v) {
		Container pivot = null;
		switch (v) {
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

	/**
	 * 
	 * @param links
	 *            Bereichsgrenze
	 * @param mitte
	 *            Mittelwert im Bereich
	 * @param rechts
	 *            Bereichsgrenze
	 * @return den Median der drei Werte
	 */
	private static Container median(Container links, Container mitte, Container rechts) {
		// ueberprueft, ob mitte der Median ist
		if (mitte.getKey() > rechts.getKey() ^ mitte.getKey() > links.getKey()) {
			return mitte;
		}

		// �ueberprueft, ob rechts der Median ist
		if (rechts.getKey() > mitte.getKey() ^ rechts.getKey() > links.getKey()) {
			return rechts;
		}
		// sonst muss es links sein
		return links;
	}



	public static Container[] erzeugeWerte(int size)
	{
		double wert;
		Container[] res = new Container[size];
		for(int i = 0; i < size; i++)
		{
			wert = Math.random() * 1000;
			res[i] = new Container("" + (int) wert, (int) wert );
		}
		return res;
	}
}
