/**
 * 
 */

package hw2;

import java.nio.channels.IllegalSelectorException;
import java.util.*;

public class MyLinkedList<E> extends AbstractList<E> {
  
  private int nelems;
  private Node head;
  private Node tail;
  
  protected class Node {
    E data;
    Node next;
    Node prev;
    
    /** Constructor to create singleton Node */
    public Node(E element)
    {
		this.data = element;
		this.next = null;
		this.prev = null;
    }
    /** Constructor to create singleton link it between previous and next 
      *   @param element Element to add, can be null
      *   @param prevNode predecessor Node, can be null
      *   @param nextNode successor Node, can be null 
      */
    public Node(E element, Node prevNode, Node nextNode)
    {
    	this.data = element;
		this.next = nextNode;
		this.prev = prevNode;
    }
    /** Remove this node from the list. Update previous and next nodes */
    public void remove()
    {
		this.prev.next = this.next;
		this.next.prev = this.prev;		
    }
    /** Set the previous node in the list
      *  @param p new previous node
      */
    public void setPrev(Node p)
    {
		this.prev = p;
    } 
	
    /** Set the next node in the list
      *  @param n new next node
      */
    public void setNext(Node n)
    {
		this.next = n;
    }
    
    /** Set the element 
      *  @param e new element 
      */
    public void setElement(E e)
    {
		this.data = e;
    }
    /** Accessor to get the next Node in the list */
    public Node getNext()
    {
    	return this.next; 
    }
    /** Accessor to get the prev Node in the list */
    public Node getPrev()
    {
      return this.prev; 
    } 
    /** Accessor to get the Nodes Element */
    public E getElement()
    {
      return this.data; 
    } 
  }
  
  /** ListIterator implementation */ 
  protected class MyListIterator implements ListIterator<E> {
    
    private boolean forward;
    private boolean canRemove;
    private Node left,right; // Cursor sits between these two nodes
    private int idx;        // Tracks current position. what next() would
    // return 
    /**
     *  Constructor to initialize iterator
     */
    public MyListIterator()
    {
		forward = true;
		canRemove = false;
		left = head;
		right = head.next;
		idx=0;
    }
    /**
     * Inserts the specified element into the list. The element is inserted immediately before the next element that would be returned by next, if any, and after the next element that would be returned by previous, if any. The new element is inserted before the implicit cursor.
     * @param e Data to be added to the new node
     * @throws IllegalArgumentException If the parameter is null
     */
    @Override
    public void add(E e) throws  IllegalArgumentException
    {
		if(e!=null)
		{
			idx++;
			nelems++;
			Node newNode = new Node(e);
			newNode.next = right;
			newNode.prev = left;
			right.prev = newNode;
			left.next = newNode;
			canRemove = false;		
		}
		else
		{
			throw new IllegalArgumentException();
		}
    }
    /**
     * Method to check if the linkedlist has more elements when traversing forward.
     */
    @Override
    public boolean hasNext()
    {
    		//if(right!=tail)
    		if(idx<nelems)
    		{
    			return true;
    		}
    		else
    		{
    			return false;
    		}
    		    		
    	
    }
    /**
     * Method to check if the linkedlist has more elements when traversing forward.
     */    
    @Override
    public boolean hasPrevious()
    {
    	if(idx>=1)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    		    	
    }
    /**
     * Return the next element in the list while going forward i.e. while moving the cursor forward
     * @throws NoSuchElementException If the list does not have more elements when traversing forward
     */
    @Override
    public E next() throws NoSuchElementException
    {
    	if(hasNext())
    	{
    		left = right;
    		right = right.next;
    		forward = true;
    		canRemove = true;
    		idx++;
    		return left.data;
    	}
    	else
    	{
    		throw new NoSuchElementException();
    	}
    }
    /**
     * Returns the value of Index next to the cursor or if the cursor is at the end of the linkedlist then returns the size of the list.
     */
    @Override
    public int nextIndex()
    {
    	if(idx<nelems)
    	{
    		return idx;
    	}
    	else
    	{
    		return nelems;
    	}
    	
    }
    /**
     * Return the previous element in the list while going backward i.e. while moving the cursor backward
     * @throws NoSuchElementException If the list does not have more elements when traversing backward
     */
    @Override
    public E previous() throws NoSuchElementException
    {
    	if(hasPrevious())
    	{
    		right = left;
    		left = left.prev;
    		forward = false;
    		canRemove = true;
    		idx--;
    		
    		return right.data;
    	}
    	else
    	
    	{
    		throw new NoSuchElementException();
    	}
    }
    /**
     * Returns the value of Index previous to the cursor or if the cursor is at the beginning of the linkedlist then returns -1.
     */
    @Override
    public int previousIndex()
    {
    	if(idx>0)
    	{
    		return idx-1;
    	}
    	else
    	{
    		return -1;
    	}
    	
    }
    /**
     * Remove the last element returned by the most recent call to either next/previous
     * @throws IllegalStateException if neither next nor previous were called or if add or remove has been called since the most recent next/previous
     */
    @Override
    public void remove() throws IllegalStateException
    {
    	if(canRemove)
    	{
    		if(forward)
        	{
        		left = left.prev;
        		left.next = right;
        		right.prev = left;
        		idx--;
        		nelems--;
        		canRemove = false;
        	}
    		else
    		{
    			right = right.next;
    			right.prev = left;
    			left.next = right;
    			nelems--;
    			canRemove = false;
    		}
    	}
    	else
    	{
    		throw new IllegalStateException();
    	}
    	
    }
    /**
     * Replaces the last element returned by next or previous with the specified element. 
     * @param e the element with which to replace the last element returned by next or previous.
     * @throws IllegalArgumentException if some aspect of the specified element prevents it from being added to this list.
     * @throws IllegalStateException if neither next nor previous have been called, or remove or add have been called after the last call to next or previous.
     */
    @Override
    public void set(E e) 
      throws IllegalArgumentException,IllegalStateException
    {
		if(canRemove==false)
		{
			throw new IllegalStateException();
		}
		else if(e==null)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			if(forward)
			{
				left.data = e;
				canRemove = false;
			}
			else
			{
				right.data = e;
				canRemove = false;
			}
		}
    }
    
  }
  
  
  //  Implementation of the MyLinkedList Class
  
  /**
   * Constructor that initializes an empty doubly linkedlist. 
   */
  public MyLinkedList()
  {
	  head = new Node(null);
	  tail = new Node(null);
	  head.next=tail;
	  head.prev=null;
	  head.data=null;
	  tail.next=null;
	  tail.prev=head;
	  tail.data=null;
	  
	  nelems=0;
	  
  }
  @Override
  public int size()
  {
	  return nelems;
  }
  /**
   * To return an elements from the list at the given index
   * @param index Index of the element to be returned
   * @throws IndexOutOfBoundsException If the index is not valid for the given list.
   */
  @Override
  public E get(int index) throws IndexOutOfBoundsException
  {
	  if(index<0 || index>=this.size())
	  {
		  throw new IndexOutOfBoundsException();
	  }
	  else
	  {
		  Node current = head;
		  for(int i=0; i<=index ; i++)
		  {
			  current = current.next;
		  }
		  return current.data;
	  }
	  
  }
  
  @Override
  /** Add an element to the list 
    * @param index  where in the list to add
    * @param data data to add
    * @throws IndexOutOfBoundsException
    * @throws IllegalArgumentException
    */ 
    public void add(int index, E data) 
    throws IndexOutOfBoundsException,IllegalArgumentException
  {
	  if(index<0 || index>this.size())
	  {
		  throw new IndexOutOfBoundsException();
	  }
	  else if(data==null)
	  {
		  throw new IllegalArgumentException();
	  }
	  else
	  {
		  Node curr = head;
		  for(int i=0; i<index ; i++)
		  {
			  curr=curr.next;
		  }
		  Node newNode = new Node(data);
		  newNode.next = curr.next;
		  newNode.prev = curr;
		  curr.next = newNode;
		  
		  curr=newNode.next;
		  curr.prev = newNode;
		  
		  nelems++;
	  }
  }
  /** Add an element to the end of the list 
    * @param data data to add
    * @throws IllegalArgumentException
    */ 
  public boolean add(E data) throws IllegalArgumentException
  {
	  if(data==null)
	  {
		  throw new IllegalArgumentException();
	  }
	  else
	  {
		  Node curr = head;
		  for(int i=0; i<nelems; i++)
		  {
			  curr = curr.next;
		  }
		  Node newNode = new Node(data);
		  curr.next = newNode;
		  newNode.prev = curr;
	 	  newNode.next = tail;
		  tail.prev = newNode; 
		  nelems++;
		  
	  }
	  return true; 
  }
  
  /** Set the element at an index in the list 
    * @param index  where in the list to add
    * @param data data to add
    * @return element that was previously at this index.
    * @throws IndexOutOfBoundsException
    * @throws IllegalArgumentException
    * @throws IllegalStateExceptoin
    */ 
  public E set(int index, E data) 
    throws IndexOutOfBoundsException,IllegalArgumentException
  {
	  if(index<0 || index>this.size())
	  {
		  throw new IndexOutOfBoundsException();
	  }
	  else if(data==null)
	  {
		  throw new IllegalArgumentException();
	  }
	  else
	  {
		  Node curr = head;
		  for(int i=0; i<index; i++)
		  {
			  curr = curr.next;
		  }
		  Node newNode = curr.next;
		  curr.next.data = data;
		  return newNode.data;		  
	  }
  }
  
  /** Remove the element at an index in the list 
    * @param index where in the list to add
    * @return element the data found
    * @throws IndexOutOfBoundsException
    * 
    */ 
  public E remove(int index) throws IndexOutOfBoundsException
  {
	  if(index<0 || index>this.size() || this.size()==0)
	  {
		  throw new IndexOutOfBoundsException();
	  }
	  else
	  {
		  Node curr = head;
		  for(int i=0; i<=index; i++)
		  {
			  curr = curr.next;
		  }
		  Node newNode = curr;
		  curr.prev.next = curr.next;
		  curr.next.prev = curr.prev;
		  nelems--;
		  return newNode.data;		
	  }
	  
  }
  
  /** Clear the linked list */
  public void clear()
  {
	  while(!isEmpty())
	  {
		  remove(0);
	  }
  }
  
  /** Determine if the list empty 
    *  @return true if empty, false otherwise */
  public boolean isEmpty()
  {
	  if(nelems==0)
	  {
		  return true;
	  }
	  else
	  {
		  return false;
	  }
  }
  
  public Iterator<E> iterator()
  {
	  return new MyListIterator();
  }
  public ListIterator<E> listIterator()
  {
	  return new MyListIterator();
  }
  
  // Helper method to get the Node at the Nth index
  private Node getNth(int index) 
  {
    
	  Node curr = head;
	  for(int i=0; i<=index; i++)
	  {
		  curr = curr.next;
	  }
	  
	  return curr;  
  }
  
  
  
  
  /*  UNCOMMENT the following when you believe your MyListIterator class is
   functioning correctly
   public Iterator<E> iterator()
   {
	   return new MyListIterator();
   }
   public ListIterator<E> listIterator()
   {
	   return new MyListIterator();
   }
   */
}

// VIM: set the tabstop and shiftwidth to 4 
// vim:tw=78:ts=4:et:sw=4

