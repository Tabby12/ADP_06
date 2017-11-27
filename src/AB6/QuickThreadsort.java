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
		int size = 100000;
		
		long minTimeOne = Long.MAX_VALUE;
		long minTimeTwo = Long.MAX_VALUE;

		long maxTimeOne = Long.MIN_VALUE;
		long maxTimeTwo = Long.MIN_VALUE;

		long averageTimeOneTotal = 0L;
		long averageTimeTwoTotal = 0L;

		int numberOfRuns = 100;

		for(int r=0; r<numberOfRuns; r++)
		{
			Container[] containerArr = erzeugeWerte(size);
			Container[] containerArr2 = new Container[size];
			for (int i = 0; i < size; i++)
			{
				containerArr2[i] = containerArr[i];
			}

			Quicksort qsort = new Quicksort();
			
			long time = System.nanoTime();
			qsort.quicksort(containerArr, 0, containerArr.length - 1, Pivotsuchverfahren.MEDIAN);
			long time2 = System.nanoTime() - time;
			
			if(time2 < minTimeOne)
			{
				minTimeOne = time2;
			}
			if(time2 > maxTimeOne)
			{
				maxTimeOne = time2;
			}
			averageTimeOneTotal += time2;
			
			time = System.nanoTime();
			
			sort(containerArr2, 0, containerArr2.length - 1, Pivotsuchverfahren.MEDIAN);
			
			time2 = System.nanoTime() - time;
			
			if(time2 < minTimeTwo)
			{
				minTimeTwo = time2;
			}
			if(time2 > maxTimeTwo)
			{
				maxTimeTwo = time2;
			}
			averageTimeTwoTotal += time2;
		}
		
		minTimeOne /= 1000f;
		minTimeTwo /= 1000f;
		maxTimeOne /= 1000f;
		maxTimeTwo /= 1000f;
		averageTimeOneTotal /= 1000f;
		averageTimeTwoTotal  /= 1000f;

		System.out.println("Quicksort");
		System.out.println("Min: " + minTimeOne + " microseconds");
		System.out.println("Max: " + maxTimeOne + " microseconds");
		long averageTimeOne = (long) (averageTimeOneTotal / (float)numberOfRuns);
		System.out.println("Average: " + averageTimeOne + " microseconds");

		System.out.println();

		System.out.println("QuickThreadsort");
		System.out.println("Min: " + minTimeTwo + " microseconds");
		System.out.println("Max: " + maxTimeTwo + " microseconds");
		long averageTimeTwo = (long) (averageTimeTwoTotal / (float)numberOfRuns);
		System.out.println("Average: " + averageTimeTwo + " microseconds");
		
		System.out.println();

		System.out.println("QuickThreadsort min takes " + (long) ((minTimeTwo / (float)minTimeOne) * 100f) + "% of quicksort time");
		System.out.println("QuickThreadsort max takes " + (long) ((maxTimeTwo / (float)maxTimeOne) * 100f) + "% of quicksort time");
		System.out.println("QuickThreadsort average takes " + (long) ((averageTimeTwo / (float)averageTimeOne) * 100f) + "% of quicksort time");
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
				insertionQuicksort(array, links, p, v);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
			try {
				insertionQuicksort(array, q, rechts, v);
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
	public static void sort(Container[] array, int links, int rechts, Pivotsuchverfahren v) throws Exception {
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
				insertionQuicksort(array, links, rechts, v);
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
	public static void insertionQuicksort(Container[] array, int links, int rechts, Pivotsuchverfahren v) throws Exception {

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
				insertionQuicksort(array, links, p, v);
				insertionQuicksort(array, q, rechts, v);
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
