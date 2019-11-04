//class Database
//This class implements the synchronization methods to be used in 
//the readers writers problem
public class Database
{
   //MP2 create any variables that you need for implementation of the methods
   //of this class

    //  locks when a writer goes through
    // for this to lock, it needs to be 0 when a writer goes through
    // that means that it needs to start with a value of one
    Semaphore mutex, db;
	Lock writermutex;
	Semaphore waitForWriting;

    int readersReading;
	int writersWaiting;
   //Database
   //Initializes Database variables
   public Database()
   {
     //MP2
     readersReading = 0;
	 writersWaiting = 0;

     mutex = new Semaphore("mutex", 1);
     db = new Semaphore("db", 1);
	 writermutex = new Lock("writer mut");
	 waitForWriting = new Semaphore("writing wait", 1);

   }

   //napping()
   //this is called when a reader or writer wants to go to sleep and when 
   //a reader or writer is doing its work.
   //Do not change for MP2
   public static void napping()
   {
      Alarm ac = new Alarm(20);  
   }

   //startRead
   //this function should block any reader that wants to read if there 
   //is a writer that is currently writing.
   //it returns the number of readers currently reading including the
   //new reader.
   public int startRead()
   {
      //MP2
	waitForWriting.P(); 

      mutex.P();
      readersReading +=1;
      if (readersReading == 1) db.P();
        mutex.V();

	waitForWriting.V();

		// check if you should wait for a writer to be done
      return readersReading;
   }

   //endRead()
   //This function is called by a reader that has finished reading from the 
   //database.  It returns the current number of readers excluding the one who
   //just finished.
   public int endRead()
   {
      //MP2
      mutex.P();
      readersReading -=1;
      if (readersReading==0) db.V();
      mutex.V();


      return readersReading;
   }

   //startWrite()
   //This function should allow only one writer at a time into the Database
   //and block the writer if anyone else is accessing the database for read 
   //or write.
   public void startWrite()
   {
      //MP2
      // give exclusive database access
	  
	  writermutex.acquire();
	  writersWaiting +=1;
	  if (writersWaiting == 1) waitForWriting.P();
	  writermutex.release();
      db.P();

   }
   
   //endWrite()
   //signal that a writer is done writing and the database can now be accessed
   //by someone who is waiting to read or write.
   public void endWrite()
   {
      //MP2
      db.V();
	  writermutex.acquire();
	  writersWaiting -=1;

	  if (writersWaiting == 0) waitForWriting.V();
	  writermutex.release();
   }
}
