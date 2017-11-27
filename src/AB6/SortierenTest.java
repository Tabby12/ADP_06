package AB6;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SortierenTest {

	@Test
	public void pivotRechts() throws Exception {
		Container[] a = GetContainerArrayFromIntegerArray( new Integer[] { 20, 54, 28, 31, 5, 24, 39, 14, 1, 15 } );
		Container[] atest = GetContainerArrayFromIntegerArray( new Integer[] { 1, 5, 14, 15, 20, 24, 28, 31, 39, 54 } );
		
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.RECHTS);
		assertTrue("Pivotrechts Fehler", Arrays.equals(a, atest));

		Container[] b = GetContainerArrayFromIntegerArray( new Integer[] { 54, 20 } );
		Container[] btest = GetContainerArrayFromIntegerArray( new Integer[] { 20, 54 } );
		QuickThreadsort.sort(b, 0, b.length - 1, Pivotsuchverfahren.RECHTS);
		assertTrue("Pivotrechts Fehler", Arrays.equals(b, btest));

		Container[] c = GetContainerArrayFromIntegerArray( new Integer[] { 6, 20, 65, 100 } );
		Container[] ctest = GetContainerArrayFromIntegerArray( new Integer[] { 6, 20, 65, 100 } );
		QuickThreadsort.sort(c, 0, c.length - 1, Pivotsuchverfahren.RECHTS);
		assertTrue("Pivotrechts Fehler", Arrays.equals(c, ctest));

	}

	@Test
	public void pivotMedian() throws Exception {
		Container[] a = GetContainerArrayFromIntegerArray( new Integer[] { 20, 54, 28, 31, 5, 24, 39, 14, 1, 15 } );
		Container[] atest = GetContainerArrayFromIntegerArray( new Integer[] { 1, 5, 14, 15, 20, 24, 28, 31, 39, 54 } );
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.MEDIAN);
		assertTrue("Pivotmdeian Fehler", Arrays.equals(a, atest));

		Container[] b = GetContainerArrayFromIntegerArray( new Integer[] { 54, 20 } );
		Container[] btest = GetContainerArrayFromIntegerArray( new Integer[] { 20, 54 } );
		QuickThreadsort.sort(b, 0, b.length - 1, Pivotsuchverfahren.MEDIAN);
		assertTrue("Pivotmdeian Fehler", Arrays.equals(b, btest));

		Container[] c = GetContainerArrayFromIntegerArray( new Integer[] { 6, 20, 65, 100 } );
		Container[] ctest = GetContainerArrayFromIntegerArray( new Integer[] { 6, 20, 65, 100 } );
		QuickThreadsort.sort(c, 0, c.length - 1, Pivotsuchverfahren.MEDIAN);
		assertTrue("Pivotmdeian Fehler", Arrays.equals(c, ctest));
	}

	@Test
	public void pivotZufaellig() throws Exception {
		Container[] a = GetContainerArrayFromIntegerArray( new Integer[] { 20, 54, 28, 31, 5, 24, 39, 14, 1, 15 } );
		Container[] atest = GetContainerArrayFromIntegerArray( new Integer[] { 1, 5, 14, 15, 20, 24, 28, 31, 39, 54 } );
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.ZUFAELLIG);
		assertTrue("Pivotzufaellig Fehler", Arrays.equals(a, atest));

		Container[] b = GetContainerArrayFromIntegerArray( new Integer[] { 54, 20 } );
		Container[] btest = GetContainerArrayFromIntegerArray( new Integer[] { 20, 54 } );
		QuickThreadsort.sort(b, 0, b.length - 1, Pivotsuchverfahren.ZUFAELLIG);
		assertTrue("Pivotzufaellig Fehler", Arrays.equals(b, btest));

		Container[] c = GetContainerArrayFromIntegerArray( new Integer[] { 6, 20, 65, 100 } );
		Container[] ctest = GetContainerArrayFromIntegerArray( new Integer[] { 6, 20, 65, 100 } );
		QuickThreadsort.sort(c, 0, c.length - 1, Pivotsuchverfahren.ZUFAELLIG);
		assertTrue("Pivotzufaellig Fehler", Arrays.equals(c, ctest));
	}

	@Test
	public void einElement() throws Exception {
		Container[] a = GetContainerArrayFromIntegerArray( new Integer[] { 20 } );
		Container[] atest = GetContainerArrayFromIntegerArray( new Integer[] { 20 } );
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.ZUFAELLIG);
		assertTrue("ein Element Fehler zufall", Arrays.equals(a, atest));
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.MEDIAN);
		assertTrue("ein Element Fehler median", Arrays.equals(a, atest));
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.RECHTS);
		assertTrue("ein Element Fehler rechts", Arrays.equals(a, atest));
	}

	@Test
	public void keinElement() throws Exception {
		Container[] a = GetContainerArrayFromIntegerArray( new Integer[] {} );
		Container[] atest = GetContainerArrayFromIntegerArray( new Integer[] {} );
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.ZUFAELLIG);
		assertTrue("ein Element Fehler zufall", Arrays.equals(a, atest));
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.MEDIAN);
		assertTrue("ein Element Fehler median", Arrays.equals(a, atest));
		QuickThreadsort.sort(a, 0, a.length - 1, Pivotsuchverfahren.RECHTS);
		assertTrue("ein Element Fehler rechts", Arrays.equals(a, atest));
	}

	@Test
	public void leereListe() {
		try {
			QuickThreadsort.sort(null, 0, 0, Pivotsuchverfahren.ZUFAELLIG);
			assertTrue("Fehler: Es wurde keine Exception geworfen!", false);
		} catch (Exception e) {
			// Alles richtig
		}
	}
	
	private void reportPerformanceCounter(Pivotsuchverfahren pivot) throws Exception
	{
		int groesse = 100000;
		int[] testValues = {1, 10, 100, 1000, 10000, 100000};
		Container [] arr = new Container[groesse];
		for(int i = 0; i < groesse; i++)
		{
			arr[i] = new Container(i,i);
		}
		
		for(int i=0; i<testValues.length; i++)
		{
			try
			{
				QuickThreadsort.sort(arr,0,testValues[i]-1, pivot);
			}
			catch (StackOverflowError e)
			{
				System.err.println("Stack overflow error on 10000 elements");
			}
		}
		
		System.out.println();

		Set<Integer> sack = new HashSet<Integer>();
		for(int i = 0; i < groesse; i++)
		{
			int j;
			do
			{
				j = (int) (Math.random()*groesse);
			}while(!sack.add(j));
			arr[j] = new Container(i,i);
		}
		
		for(int i=0; i<testValues.length; i++)
		{
			try
			{
				QuickThreadsort.sort(arr,0,testValues[i]-1, pivot);
			}
			catch (StackOverflowError e)
			{
				System.err.println("Stack overflow error on 10000 elements");
			}
		}
	}
	
	@Test
	public void testRechts() throws Exception {
		System.out.println("Reporting performance counter for RECHTS");
		reportPerformanceCounter(Pivotsuchverfahren.RECHTS);
		assertTrue(true);
		System.out.println("End reporting performance counter for RECHTS\n");
	}
	
	@Test
	public void testMedian() throws Exception {
		System.out.println("Reporting performance counter for MEDIAN");
		reportPerformanceCounter(Pivotsuchverfahren.MEDIAN);
		assertTrue(true);
		System.out.println("End reporting performance counter for MEDIAN\n");
	}
	
	@Test
	public void testZufaellig() throws Exception {
		System.out.println("Reporting performance counter for ZUFAELLIG");
		reportPerformanceCounter(Pivotsuchverfahren.ZUFAELLIG);
		assertTrue(true);
		System.out.println("End reporting performance counter for ZUFAELLIG\n");
	}
	
	private Container[] GetContainerArrayFromIntegerArray(Integer[] intArray)
	{
		Container[] containerArray = new Container[intArray.length];
		for(int i=0; i<intArray.length; i++)
		{
			containerArray[i] = new Container(null, intArray[i]);
		}
		return containerArray;
	}

}
