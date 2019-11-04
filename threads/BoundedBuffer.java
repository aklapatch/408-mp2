//class BoundedBuffer
//This class implements the synchronization methods to be used in 
//the bounded buffer problem 
import java.util.ArrayList;

public class BoundedBuffer
{
   //MP2 create any variables you need
   
   // TO THE GRADER:
   // I was somewhat forced to use a standard java arrayList because when I tried
   // to get the character back from the Nachos list, it was giving me a 
   // `ListElement cannot be cast to Character` error message
   // I was using the remove() method, wich returns an object, so I am not sure
   // what was going wrong.
    ArrayList<Character> Buffer;

    
    // I need 2 semaphore's, or one, 
    // I need one to indicate how full the semaphore is
    // and one to indicate how empty it is. 
    //
    // this semaphore locks when the list is empty
    Semaphore emptySem;
    // this semaphore locks when the list is full
    Semaphore fullSem;

    Lock changeLock;

   //BoundedBuffer
   //constructor:  initialize any variables that are needed for a bounded 
   //buffer of size "size"
   public BoundedBuffer(int size)
   {
       // this is empty to start, so it's not okay to insert
        emptySem = new Semaphore("emptySem", 0); 
        // its okay to insert up to the capacity of the buffer right now
        fullSem = new Semaphore("fullSem", size);
        Buffer = new ArrayList<Character>(size);
        changeLock = new Lock("varlock");
   }

   //produce()
   //produces a character c.  If the buffer is full, wait for an empty
   //slot
   public void produce(char c)
   {
     //MP2

    // go down on the semaphore to insert
    fullSem.P();

    
    changeLock.acquire();

    // insert here
    Buffer.add(c);

    changeLock.release();

    // release semaphore
     
    emptySem.V();
   }

   //consume()
   //consumes a character.  If the buffer is empty, wait for a producer.
   //use method SynchTest.addToOutputString(c) upon consuming a character. 
   //This is used to test your implementation.
   public void consume()
   {
     //MP2
     // go down on semaphore to read from list
    emptySem.P();

    changeLock.acquire();

	char output =  (Character)Buffer.remove(0);
     //make sure you change the following line accordingly
     SynchTest.addToOutputString(output);

     changeLock.release();

     fullSem.V();

   }

}
