import org.junit.*;

import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * A class that tests the methods of the DoubleLinkedList class.
 */
public class DoubleLinkedListTester {

	/**
	 * Tests the addToFront method of DoubleLinkedList.
	 */
	@Test
	public void testAddToFront() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		list.addToFront(3);
		list.addToFront(2);
		list.addToFront(1);
		DLNode<Integer> head = list.getFront();
		DLNode<Integer> tail = list.getBack();

		assertEquals("Testing first node of list", new Integer(1),
				head.getElement());
		assertEquals("Testing second node of list", new Integer(2), head
				.getNext().getElement());
		assertEquals("Testing third node of list", new Integer(3), head
				.getNext().getNext().getElement());
		assertEquals("Testing end of list", null, head.getNext().getNext()
				.getNext());

		assertEquals("Testing node at back of list", new Integer(3),
				tail.getElement());
		assertEquals("Testing next to last node", new Integer(2), tail
				.getPrevious().getElement());
		assertEquals("Testing third to last node", new Integer(1), tail
				.getPrevious().getPrevious().getElement());
		assertEquals("Testing front of list", null, tail.getPrevious()
				.getPrevious().getPrevious());
	}

	/**
	 * Tests the addToBack method of DoubleLinkedList.
	 */
	@Test
	public void testAddToBack() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		list.addToBack(1);
		list.addToBack(2);
		list.addToBack(3);
		DLNode<Integer> head = list.getFront();
		DLNode<Integer> tail = list.getBack();

		assertEquals("Testing last node of list", new Integer(3),
				tail.getElement());
		assertEquals("Testing next to last node", new Integer(2), tail
				.getPrevious().getElement());
		assertEquals("Testing third to last node", new Integer(1), tail
				.getPrevious().getPrevious().getElement());
		assertEquals("Testing front of list", null, tail.getPrevious()
				.getPrevious().getPrevious());

		assertEquals("Testing node at front of list", new Integer(1),
				head.getElement());
		assertEquals("Testing second node of list", new Integer(2), head
				.getNext().getElement());
		assertEquals("Testing third node of list", new Integer(3), head
				.getNext().getNext().getElement());
		assertEquals("Testing end of list", null, head.getNext().getNext()
				.getNext());
	}

	/**
	 * Tests the removeFromFront method of DoubleLinkedList.
	 */
	@Test
	public void testRemoveFromFront() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		list.addToFront(1);
		list.addToFront(2);
		list.addToFront(3);
		assertEquals("Removing element of list", new Integer(3),
				list.removeFromFront());
		assertEquals("Removing a second element", new Integer(2),
				list.removeFromFront());
		assertEquals("Removing a third element", new Integer(1),
				list.removeFromFront());
		assertEquals("Removed last element of list", true, list.isEmpty());
		try {
			list.removeFromFront();
			fail("Removing from empty list did not throw an exception.");
		}
		catch (java.util.NoSuchElementException e) {
			/* everything is good */
		}
		catch (Exception e) {
			fail("Removing from empty list threw the wrong type of exception.");
		}

		list.addToBack(6);
		list.addToBack(7);
		assertEquals("Removing element added to back of list", new Integer(6),
				list.removeFromFront());
		assertEquals("Removing second element added to back", new Integer(7),
				list.removeFromFront());
	}

	/**
	 * Tests the removeFromBack method of DoubleLinkedList.
	 */
	@Test
	public void testRemoveFromBack() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		list.addToBack(5);
		list.addToFront(4);
		list.addToBack(6);
		assertEquals("Removing element from back of list", new Integer(6),
				list.removeFromBack());
		assertEquals("Removing second element from back of list",
				new Integer(5), list.removeFromBack());
		assertEquals("Removing element from back that was added to front",
				new Integer(4), list.removeFromBack());
		assertEquals("Removing last element of list", true, list.isEmpty());
		try {
			list.removeFromBack();
			fail("Removing from empty list did not throw an exception.");
		}
		catch (java.util.NoSuchElementException e) {
			/* everything is good */
		}
		catch (Exception e) {
			fail("Removing from empty list threw the wrong type of exception.");
		}
	}

	/**
	 * Tests the linked list iterator.
	 */
	@Test
	public void testListIterator() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		for (int i = 5; i > 0; i--) {
			System.out.println("list.addToFront " + i);
			list.addToFront(i);
			System.out.println(list.getFront().getElement());
		}
		/* checking that we get out what we put it */
		int i = 1;
		for (Integer x : list) {
			System.out.println("Got from list " + x);
			assertEquals("Testing value returned by iterator",
					new Integer(i++), x);
		}
		if (i != 6)
			fail("The iterator did not run through all the elements of the list");
	}

	/**
	 * Tests the remove feature of the linked list iterator.
	 */
	@Test
	public void testListIteratorRemove() {
		DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
		for (int i = 5; i > 0; i--)
			list.addToFront(i);

		/* testing removing the first element through the iterator */
		Iterator<Integer> listIterator = list.iterator();
		listIterator.next();
		listIterator.remove();

		/*
		 * /* the list should now be 2 through 5
		 */
		int i = 2;
		for (Integer x : list)
			assertEquals("The iterator failed when removing the first element",
					new Integer(i++), x);
		if (i != 6)
			fail("The iterator failed when removing the first element");

		/* testing removing element 3 */
		listIterator = list.iterator();
		listIterator.next();
		listIterator.next();
		listIterator.remove();

		DLNode<Integer> head = list.getFront();
		DLNode<Integer> tail = list.getBack();

		assertEquals("Iterator failed to remove middle element",
				new Integer(2), head.getElement());
		assertEquals("Iterator failed to remove middle element",
				new Integer(4), head.getNext().getElement());
		assertEquals("Iterator failed to remove middle element",
				new Integer(5), head.getNext().getNext().getElement());
		assertEquals("Iterator failed to remove middle element", null, head
				.getNext().getNext().getNext());

		assertEquals("Iterator failed to remove middle element",
				new Integer(5), tail.getElement());
		assertEquals("Iterator failed to remove middle element",
				new Integer(4), tail.getPrevious().getElement());
		assertEquals("Iterator failed to remove middle element",
				new Integer(2), tail.getPrevious().getPrevious().getElement());
		assertEquals("Iterator failed to remove middle element", null, tail
				.getPrevious().getPrevious().getPrevious());

		/* testing removing the last element */
		while (listIterator.hasNext())
			listIterator.next();
		System.out.println("Remove last element \n");
		listIterator.remove();

		head = list.getFront();
		tail = list.getBack();

		assertEquals("Iterator failed to remove middle element",
				new Integer(2), head.getElement());
		assertEquals("Iterator failed to remove middle element",
				new Integer(4), head.getNext().getElement());
		assertEquals("Iterator failed to remove middle element", null, head
				.getNext().getNext());

		assertEquals("Iterator failed to remove middle element",
				new Integer(4), tail.getElement());
		assertEquals("Iterator failed to remove middle element",
				new Integer(2), tail.getPrevious().getElement());
		assertEquals("Iterator failed to remove middle element", null, tail
				.getPrevious().getPrevious());

		/* testing removing before calling next */
		listIterator = list.iterator();
		try {
			listIterator.remove();
			fail("Calling iterator's remove() before calling next() should throw an IllegalStateException");
		}
		catch (IllegalStateException e) {
			// all is good
		}
		catch (Exception e) {
			fail("The wrong exception thrown by the iterator remove() method.");
		}

	}

	@Test
	public void testListIteratorAdd() {
		DoubleLinkedList<Integer> list1 = new DoubleLinkedList<Integer>();
		for (int i = 2; i > 0; i--)
			list1.addToFront(i);

		/* testing removing the first element through the iterator */
		ListIterator<Integer> listIterator = list1.iterator();
		listIterator.add(3);
		for (Integer x : list1) {
			System.out.println("xxGot from list " + x);
		}

		/* testing removing the first element through the iterator */
		DoubleLinkedList<Integer> list2 = new DoubleLinkedList<Integer>();

		ListIterator<Integer> list2Iterator = list2.iterator();
		list2Iterator.add(1);
		for (Integer x : list2) {
			System.out.println("xxGot from list " + x);
		}

		/* testing removing the first element through the iterator */
		DoubleLinkedList<Integer> list3 = new DoubleLinkedList<Integer>();
		list3.addToFront(1);

		ListIterator<Integer> list3Iterator = list3.iterator();
		list3Iterator.add(2);
		for (Integer x : list3) {
			System.out.println("xxGot from list " + x);
		}
		
	}
}