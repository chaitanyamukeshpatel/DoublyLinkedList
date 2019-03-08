package hw2;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 *  Title: class LinkedListTester
 *  Description: JUnit test class for LinkedList class
 *  @author Chaitanya Mukesh Patel
 *  @version 3.0 05-April-2015
 *  CSE12 
 * */

/*
 * You should modify the information above to add your names and CSE12 accounts.
 * 
 * We have provided 7 tests to help you get familiar. 
 *
 * As a part of the Milestone submission, you have to add 10 new tests.
 * These tests will ONLY be graded against the correct implementation of MyLinkedList.
 * However, you will also be given feedback on how your tests behave on the 
 * buggy implementation of MyLinkedList. 
 *
 * After the Milestone submission, you will be able to view each other's tests which you
 * can use to add or improve your tests for the final submission. At the final submission,
 * if you are using someone else's tests, we expect you to give the required attribution.
 * We also expect you to write a README file that explains in greater detail why you used
 * the test. 
 * 
 * For the Final Submission, your tests will be graded against both the correct and
 * buggy implementation of MyLinkedList. At the time of final submission, you will be
 * given feedback on how your tests behave against few but not all, buggy implementations.
 *
 * Finally, when your tester is complete, you will rename it MyLinkedListTester.java 
 * (renaming LinkedList to MyLinkedList everywhere in the file) so that you can
 * use it to test your MyLinkedList and MyListIterator classes.
 */


public class MyLinkedListTester
{
  private MyLinkedList<Integer> empty ;
  private MyLinkedList<Integer> one ;
  private MyLinkedList<Integer> several ;
  private MyLinkedList<String>  slist ;
  static final int DIM = 5;
  static final int FIBMAX = 30;
  
  /**
   * Standard Test Fixture. An empty list, a list with one entry (0) and 
   * a list with several entries (0,1,2)
   */ 
  @Before
  public void setUp()
  {
    empty = new MyLinkedList<Integer>();
    one = new MyLinkedList<Integer>();
    one.add(0,new Integer(0));
    several = new MyLinkedList<Integer>() ;
    // List: 1,2,3,...,Dim
    for (int i = DIM; i > 0; i--)
      several.add(0,new Integer(i));
    
    // List: "First","Last"
    slist = new MyLinkedList<String>();
    slist.add(0,"First");
    slist.add(1,"Last");
  }
  /** Test if heads of the lists are correct */
  @Test
  public void testGet()
  {
    assertEquals("Check 0",new Integer(0),one.get(0));
    assertEquals("Check 0",new Integer(3),several.get(2));
    try
    {
    	assertEquals("Check out of bounds", new Integer(6), several.get(5));
    	fail("Should throw a IndexOutOfBoundsException");
    }
    catch(IndexOutOfBoundsException e)
    {
    	//Empty
    }
    
  }
  
  /** Test if size of lists are correct */
  @Test
  public void testListSize()
  {
    assertEquals("Check Empty Size",0,empty.size()) ;
    assertEquals("Check One Size",1,one.size()) ;
    assertEquals("Check Several Size",DIM,several.size()) ;
  }
  
  /** Test setting a specific entry */
  @Test
  public void testSet()
  {
    slist.set(1,"Final");
    assertEquals("Setting specific value", "Final",slist.get(1));
    try
    {
    	several.set(1, null);
    	fail("Should throw IllegalArgumentException");
    }
    catch(IllegalArgumentException e)
    {
    	//Empty
    }
    try
    {
    	empty.set(2, 1);
    	fail("Should throw IndexOutOfBoundsException");
    }
    catch(IndexOutOfBoundsException e)
    {
    	//Empty
    }
    
  }
  
  /** Test isEmpty */
  @Test
  public void testisEmpty()
  {
    assertTrue("empty is empty",empty.isEmpty()) ;
    assertTrue("one is not empty",!one.isEmpty()) ;
    assertTrue("several is not empty",!several.isEmpty()) ;
  }

  /** Test out of bounds exception on get */
  @Test
  public void testGetException()
  {
    try 
    {
      empty.get(0);
      // This is how you can test when an exception is supposed 
      // to be thrown
      fail("Should have generated an exception");  
    }
    catch(IndexOutOfBoundsException e)
    {
      //  normal
    }
  }

  
  /** Test iterator on empty list and several list */
  @Test
  public void testIterator()
  {
	  int counter = 0 ;
	    ListIterator<Integer> iter;
	    for (iter = empty.listIterator() ; iter.hasNext(); )
	    {
	      fail("Iterating empty list and found element") ;
	    }
	    counter = 0 ;
	    for (iter = several.listIterator() ; iter.hasNext(); iter.next())
	    {
	    	counter++;
	    }
	    assertEquals("Iterator several count", DIM, counter);
	    
  }
  
  
  /** test Iterator Fibonacci.
    * This is a more holistic test for the iterator.  You should add
    * several unit tests that do more targeted testing of the individual
    * iterator methods.  */
  @Test
  public void testIteratorFibonacci()
  {
    
    LinkedList<Integer> fib  = new LinkedList<Integer>();
    ListIterator<Integer> iter;
    // List: 0 1 1 2 3 5 8 13 ... 
    // Build the list with integers 1 .. FIBMAX
    int t, p = 0, q = 1;
    fib.add(0,p);
    fib.add(1,q);
    for (int k = 2; k <= FIBMAX; k++)
    {
      t = p+q;
      fib.add(k,t);
      p = q; q = t; 
    }
    // Now iterate through the list to near the middle, read the
    // previous two entries and verify the sum.
    iter = fib.listIterator();
    int sum = 0;
    for (int j = 1; j < FIBMAX/2; j++)
      sum = iter.next();
    iter.previous();
    assertEquals(iter.previous() + iter.previous(),sum);
    // Go forward with the list iterator
    assertEquals(iter.next() + iter.next(),sum);
  }
  
  /*A test to check a method that adds elements to the end of the list*/
  @Test
  public void testAdd()
  {
	//assertEquals("Adding to one", new Integer(0) , one.get(0));

	empty.add(3);  
	assertEquals("Adding to empty list", new Integer(3) , empty.get(empty.size()-1));
	slist.add("Three");
	assertEquals("Adding to slist", "Three" , slist.get(slist.size()-1));
	one.add(1,3);
	assertEquals("Adding at the end of the list with index", new Integer(3) , one.get(1));
	several.add(2,11);
	assertEquals("Adding in between the list", new Integer(11) , several.get(2));
	assertEquals("Checking the element that got moved from index 2", new Integer(3), several.get(3));
	
	try
	{
		empty.add(null);
		fail("Should throw IllegalArgumentException");
	}
	catch(IllegalArgumentException e)
	{
		//Empty
	}
	
	try
	{
		one.add(3,3);
		fail("Should throw an IndexOutOfBoundsException");
	}
	catch(IndexOutOfBoundsException e)
	{
		//Empty
	}
	
	try
	{
		one.add(2, null);
		fail("Should throw an IllegalArgumentException");
	}
	catch(IllegalArgumentException e)
	{
		//Empty
	}
  }
  
  /*A test to check a method that clears the entire list. Used isEmpty() check if the list was empty after the command.*/
  @Test
  public void testClear()
  {
	
	one.clear();
	assertTrue("one is empty", one.isEmpty());
	several.clear();
	assertTrue("several is not empty" , several.isEmpty());
	slist.clear();
	assertTrue("slist is empty", slist.isEmpty());
  }
  
  @Test
  public void testRemove()
  {
	  
	  one.remove(0);
	  assertTrue("One is Empty", one.isEmpty());
	  several.remove(DIM-1);
	  assertEquals("Check the last element", new Integer(4), several.get(DIM-2));
	  try
	  {
		  several.remove(6);
		  fail("Should throw an IndexOutOfBoundsException");
	  }
	  catch(IndexOutOfBoundsException e)
	  {
		  //Empty
	  }
	  
  }
   
  @Test 
  public void testAddbyIndex()
  {
	  one.add(1, 5);
	  assertEquals("Checking for a particular index", new Integer(5), one.get(1));
	  several.add(1, 5);
	  assertEquals("Checking for a particular index as adding moves it further", new Integer(2), several.get(2));	  
	  
  }
  @Test
  public void testNext()
  {
	  ListIterator<Integer> iter;
	  iter = one.listIterator();
	  int testVal = iter.next();
	  assertEquals(testVal, 0);
	  try
	  {
		  iter.next();
		  fail("Should throw a NoSuchElementException");
	  }
	  catch(NoSuchElementException e)
	  {
		  //Empty
	  }
  }
  @Test 
  public void testNextIndex()
  {
	  
	  ListIterator<Integer> iter;
	  for(iter = several.listIterator(); iter.hasNext(); iter.next())
	  {

	  }
	  assertEquals("As several is indexed from 0 to 4, and the for loop continues to next until the list has a next entry, the nextIndex at the end should be 5 as it will return the size", several.size() , iter.nextIndex());
  }
  @Test
  public void testhasNext()
  {
	  ListIterator<Integer> iter;
	  iter = empty.listIterator();
	  assertFalse("If the list is empty, the hasNext should return false boolean", iter.hasNext());
	  iter = one.listIterator();
	  iter.next();
	  assertFalse("The hasNext should return false boolean", iter.hasNext());
  }
  @Test
  public void testpreviousIndex()
  {
	  
	  ListIterator<Integer> iter;
	  for(iter = empty.listIterator(); iter.hasNext(); iter.next())
	  {

	  }
	  assertEquals("As empty does not have any values, previousindex should return -1 as iterator will still be at the start of the list", -1 , iter.previousIndex());
  }
  @Test
  public void testhasPrevious()
  {
	  ListIterator<Integer> iter;
	  iter = empty.listIterator();
	  assertFalse("If the list is empty, the hasPrevious should return false boolean", iter.hasPrevious());
  } 
 @Test
  public void testIteratorAdd()
  {
	  ListIterator<Integer> iter;
	  iter = several.listIterator();
	  iter.next();
	  iter.next();
	  iter.add(6); 
	  assertEquals("The element of index 2 should now be 6, as it was added after two calls to next", new Integer(6) , several.get(2));
	  iter = several.listIterator();
	  iter.add(11);
	  assertEquals("The first element of the list should now be 11", new Integer(11), several.get(0));
	  try
	  {
		  iter.add(null);
		  fail("Should generate a illegal argument exception");
	  }
	  catch(IllegalArgumentException e)
	  {
		  //empty
	  }
  }
  @Test
  public void testIteratorRemove()
 	{
 		System.out.println("Checking Interator next(), previous() and remove()");
 		ListIterator<Integer> iter;
 		iter = several.listIterator();
        iter.next();
        iter.remove();
 		iter = several.listIterator();
        int testVal = iter.next();
        assertEquals(testVal,2);
        iter = several.listIterator();
        iter.next();
        iter.next();
        iter.next();
        int testVal2 = iter.previous();
        iter.remove();
        assertEquals(testVal2, 4);
        try
        {
        	iter.remove();
        	fail("Should generate an IllegalStateException exception");
        }
        catch(IllegalStateException e)
        {
        	
        }
        
     }
  @Test
  public void testRemoveIndexException()
	{
		System.out.println("Checking remove index exception");
		try 
		{
			several.remove(-1);
			fail("Should have generated an exception");
		}
		catch(IndexOutOfBoundsException e)
		{
			//	normal
		}
	}
  @Test
  public void testPrevious()
  {
	  ListIterator<Integer> iter;
	  iter = one.listIterator();
	  iter.next();
	  int testVal = iter.previous();
	  assertEquals(testVal, 0);
	  try
	  {
		  iter.previous();
		  fail("Should throw a NoSuchElementException");
	  }
	  catch(NoSuchElementException e)
	  {
		  //Empty
	  }
  }
  @Test
  public void testIteratorSet()
  {
	  ListIterator<Integer> iter;
	  iter = several.listIterator();
	  iter.next();
	  iter.next();
	  iter.set(11);
	  assertEquals(new Integer(11), several.get(1));
	  try
	  {
		  iter.set(11);
		  fail("Should throw IllegalStateException");
	  }
	  catch(IllegalStateException e)
	  {
		  //Empty
	  }
	  try
	  {
		 iter.next();
		 iter.set(null);
		 fail("Should throw an IllegalArgumentException");
	  }
	  catch(IllegalArgumentException e)
	  {
		  //Empty
	  }

  }

}