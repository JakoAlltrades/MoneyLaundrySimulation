
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class CarWashDriver extends Thread{
	//int average = (-1) * math.log(1 - gen.nextDouble()) * averageCarWashPerHour;
	public static double timePassed = 0;
	public static int dayLength = 720;
	public static int averageWashTime = 5;
	public final static int washLinesCount = 1;
	public static int timeTillNewArrivals = 10;
	public static int mostArrivals = 3;
	public static int amount_of_registers = 1;
	public static int totalCarsSeen = 0;
	public static double averagePayTime = 1.5;
	static Semaphore Register = new Semaphore(amount_of_registers);
	static Semaphore washingLines = new Semaphore(washLinesCount);//if we want to have only one person pay at a time
	static Math math;
	public static Random gen = new Random();
	public static int numAdded = 0;
	public static double overall = 0;
	static ExecutorService svc;
	
	/*
	 * I am not sure what kind of Executor service we should use, 
	 * I think timeThreadService might work best if we use that for customer arrival 
	 */
	
	public static void main(String[] args)
	{

		while(timePassed <= dayLength)
		{//while day is not over 
			double average = (((-1) * math.log(1 - gen.nextDouble())) * timeTillNewArrivals);
			//System.out.println("Average arrival: "+ math.ceil(average));
			int peopleArrived = (gen.nextInt(mostArrivals)+1);
			svc = Executors.newFixedThreadPool(peopleArrived);
			timePassed += average;
			overall +=average;
			numAdded++;
			if(timePassed <= dayLength) {
			svc.submit(() ->{
				try {
					totalCarsSeen++;
					washingLines.acquire();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException();
				}
				try
				{
					double washTime = ((-1) * math.log(1 - gen.nextDouble()) * averageWashTime);
					System.out.println("Car was washed for: " + math.ceil(washTime) + " Minutes, Customer Id: "+ Thread.currentThread().getId());
					timePassed +=math.ceil(washTime);
					/*try {
						Register.acquire();
					}
					catch(Exception e)
					{
						System.out.println(e.toString());
					}
					try {
						int payTime = (int) ((-1) * math.log(1 - gen.nextDouble()) * averagePayTime);
						System.out.println("Car that was washed has payed for their service. Time taken: "+payTime);
						timePassed+=payTime;
					}
					finally {
						Register.release();
					}*/
				}finally 
				{
					washingLines.release();
				}
			});
			
			svc.shutdown();
			}
		}
		System.out.println("Total amount of cars seen: "+ totalCarsSeen + ". The total time spent working was " + Math.floor(timePassed) + " Minutes");
		System.out.println("The average time to be seen is: " + (overall/numAdded));
		//math.log(1 - );

	}

}
