import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class CarWashDriver {

	//fuck you john 
	//fight me brandon
	
	public static long dayLength = 8000;
	public static int maxWashTime = 400;
	public static long minWashTime = 200;
	public final int washingMachineCount = 4;
	public final int averageCarsWashedPerHour = 19;
	
	public long timePassed = 0;//Use this instead of putting threads to sleep
	
	public static Object Registar = new Object();//if we want to have only one person pay at a time
	Semaphore washingMachines = new Semaphore(washingMachineCount);
	
	/*
	 * I am not sure what kind of Executor service we should use, 
	 * I think timeThreadService might work best if we use that for customer arrival 
	 */
	
	public static void main(String[] args)
	{
		
	}

}
