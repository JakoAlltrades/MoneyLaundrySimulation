
import java.util.concurrent.Semaphore;

public class CarWashDriver extends Thread{


	//fuck you john 
	// and Brandon
	//Also matt
	//fight me brandon
	
	public static long dayLength = 720;
	public static int maxWashTime = 10;
	public static long minWashTime = 5;
	public final int washLinesCount = 1;
	public int timeTillNewArrivals = 10;
	public int mostArrivals = 3;

	
	public static long timePassed = 0;//Use this instead of putting threads to sleep
	//-Ln(1-.78)(20) =
	Semaphore washingLines = new Semaphore(washLinesCount);//if we want to have only one person pay at a time
	
	
	/*
	 * I am not sure what kind of Executor service we should use, 
	 * I think timeThreadService might work best if we use that for customer arrival 
	 */

	public static void main(String[] args)
	{
		while(timePassed < dayLength)
		{//while day is not over 
			
		}
	}

}
