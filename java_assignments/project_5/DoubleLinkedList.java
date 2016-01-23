import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked linked list.
 */
public class DoubleLinkedList<T> implements Iterable<T> {
	/** a reference to the first node of the double linked list */
	private DLNode<T> front;

	/** a reference to the last node of a double linked list */
	private DLNode<T> back;

	/** Create an empty double linked list. */
	public DoubleLinkedList() {
		front = back = null;
	}

	/**
	 * Returns true if the list is empty.
	 * 
	 * @return true if the list has no nodes
	 */
	public boolean isEmpty() {
		return (getFront() == null);
	}

	/**
	 * Returns the reference to the first node of the linked list.
	 * 
	 * @return the first node of the linked list
	 */
	protected DLNode<T> getFront() {
		return front;
	}

	/**
	 * Sets the first node of the linked list.
	 * 
	 * @param node
	 *            the node that will be the head of the linked list.
	 */
	protected void setFront(DLNode<T> node) {
		front = node;
	}

	/**
	 * Returns the reference to the last node of the linked list.
	 * 
	 * @return the last node of the linked list
	 */
	protected DLNode<T> getBack() {
		return back;
	}

	/**
	 * Sets the last node of the linked list.
	 * 
	 * @param node
	 *            the node that will be the last node of the linked list
	 */
	protected void setBack(DLNode<T> node) {
		back = node;
	}

	/**
	 * Add an element to the head of the linked list.
	 * 
	 * @param element
	 *            the element to add to the front of the linked list
	 */
	public void addToFront(T element) {
		if (isEmpty()) {
			DLNode<T> newNode = new DLNode<T>(element, null, null);
			this.setFront(newNode);
			this.setBack(newNode);
			return;
		}
		DLNode<T> nodeptr = this.getFront();
		DLNode<T> newNode = new DLNode<T>(element, nodeptr.getPrevious(),
				nodeptr);
		nodeptr.setPrevious(newNode);
		this.setFront(newNode);
	}

	/**
	 * Add an element to the tail of the linked list.
	 * 
	 * @param element
	 *            the element to add to the tail of the linked list
	 */
	public void addToBack(T element) {
		if (isEmpty()) {
			DLNode<T> newNode = new DLNode<T>(element, null, null);
			this.setBack(newNode);
			this.setFront(newNode);
			return;
		}
		DLNode<T> nodeptr = this.getBack();
		DLNode<T> newNode = new DLNode<T>(element, nodeptr, null);
		newNode.setPrevious(nodeptr);
		nodeptr.setNext(newNode);
		this.setBack(newNode);
	}

	/**
	 * Remove and return the element at the front of the linked list.
	 * 
	 * @return the element that was at the front of the linked list
	 * @throws NoSuchElementException
	 *             if attempting to remove from an empty list
	 */
	public T removeFromFront() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		DLNode<T> remNode = this.getFront();
		DLNode<T> newFront = remNode.getNext();
		if (newFront != null)
			newFront.setPrevious(null);
		this.setFront(newFront);
		if (remNode == this.getBack()) {
			this.setBack(null);
		}

		return remNode.getElement();
	}

	/**
	 * Remove and return the element at the back of the linked list.
	 * 
	 * @return the element that was at the back of the linked list
	 * @throws NoSuchElementException
	 *             if attempting to remove from an empty list
	 */
	public T removeFromBack() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		DLNode<T> remNode = this.getBack();
		DLNode<T> newBack = remNode.getPrevious();
		if (newBack != null) {
			newBack.setNext(null);
		}
		this.setBack(newBack);
		if (remNode == this.getFront()) {
			this.setFront(null);
		}

		return remNode.getElement();
	}

	/**
	 * Returns an iterator for the linked list that starts at the head of the
	 * list and runs to the tail.
	 * 
	 * @return the iterator that runs through the list from the head to the tail
	 */

	@Override
	public ListIterator<T> iterator() {
		return new _LocalListIterator();
	}

	private class _LocalListIterator implements ListIterator<T> {

		private DLNode<T> frontItr;
		private DLNode<T> lastReturnedItr;

		_LocalListIterator() {
			frontItr = front;
			lastReturnedItr = null;
		}

		@Override
		public boolean hasNext() {
			if (frontItr != null)
				return true;
			else
				return false;
		}

		@Override
		public T next() {
			if (frontItr == null) {
				lastReturnedItr = null;
				throw new NoSuchElementException();
			}
			T element = frontItr.getElement();
			lastReturnedItr = frontItr;
			frontItr = frontItr.getNext();
			return element;
		}

		@Override
		public void remove() {

			if (isEmpty())
				return;

			if (lastReturnedItr == null) {
				throw new IllegalStateException();
			}

			// If there is a previous element then we need to change next
			// to current next
			if (lastReturnedItr.getPrevious() != null) {
				lastReturnedItr.getPrevious()
						.setNext(lastReturnedItr.getNext());
			}
			else {
				// If there is no previous then we have to keep this as first element in the list
				setFront(lastReturnedItr.getNext());
			}

			// If this is not the last element then the previous element's next to current element's next
			if (lastReturnedItr.getNext() != null) {
				lastReturnedItr.getNext().setPrevious(
						lastReturnedItr.getPrevious());
			}
			else
				setBack(lastReturnedItr.getPrevious());
			lastReturnedItr = null; // Set to null, it is not legal to call
									// remove without calling next again
		}

		@Override
		public boolean hasPrevious() {
			if (frontItr.getPrevious() != null)
				return true;
			else
				return false;
		}

		@Override
		public T previous() {
			if (frontItr.getPrevious() == null) {
				lastReturnedItr = null;
				throw new NoSuchElementException();
			}
			T element = frontItr.getElement();
			lastReturnedItr = frontItr;
			frontItr = frontItr.getPrevious();
			return element;
		}
		
		@Override
		public
		void add(T element) {
			if (frontItr == null) {
				addToFront(element);
				frontItr = front;
				return;
			}
			
			DLNode<T> node = frontItr.getNext();
			DLNode<T> newNode = new DLNode<T>(element, frontItr, frontItr.getNext());
			if (frontItr.getNext() != null) {
				frontItr.setNext(newNode);
			}
			if (newNode.getNext() == null) {
				setBack(newNode);
			}
		}
		
		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(T arg0) {
			throw new UnsupportedOperationException();			
		}
	}
}