
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class CarWashDriver extends Thread{
	//Fuck You John; 
	//Brandon,
	//Matt,
	//and Chance
	//fight me Brandon
	//int average = (-1) * math.log(1 - gen.nextDouble()) * averageCarWashPerHour;
	public static double timePassed = 0;
	public static int dayLength = 720;
	public static int maxWashTime = 10;
	public static int minWashTime = 5;
	public final static int washLinesCount = 1;
	public static int timeTillNewArrivals = 10;
	public static int mostArrivals = 3;

	
	//Use this instead of putting threads to sleep
	//-Ln(1-.78)(20) =
	static Semaphore washingLines = new Semaphore(washLinesCount);//if we want to have only one person pay at a time
	
	
	static Math math;
	public static Random gen = new Random();
	static ExecutorService svc;
	
	/*
	 * I am not sure what kind of Executor service we should use, 
	 * I think timeThreadService might work best if we use that for customer arrival 
	 */
	
	public static void main(String[] args)
	{

		while(timePassed < dayLength)
		{//while day is not over 
			double average = ((-1) * math.log(1 - gen.nextDouble()) * timeTillNewArrivals);
			int peopleArrived = (gen.nextInt(mostArrivals)+1);
			svc = Executors.newFixedThreadPool(peopleArrived);
			svc.submit(() ->{
				try {
					washingLines.acquire();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException();
				}
				try
				{
					int washTime = (gen.nextInt(maxWashTime)+minWashTime);
					System.out.println("Car was washed for: " + washTime + "Minutes by lane: "+ Thread.currentThread().getId());
					timePassed+=washTime;
				}finally 
				{
					washingLines.release();
				}
			});
			timePassed += average;
			
		}

		//math.log(1 - );

	}

}
