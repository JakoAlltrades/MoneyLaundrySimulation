
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class CarWashDriver extends Thread{
	//int average = (-1) * math.log(1 - gen.nextDouble()) * averageCarWashPerHour;
	public static double timePassed = 0;
	public static int dayLength = 720;//9 hour work day or(60 *9)
	public static int averageWashTime = 10;//Was originally 12 (minutes)
	public final static int washLinesCount = 1;
	public static int timeTillNewArrivals = 15;//Was originally 10 minutes
	//Those averages produced an hour wait time before service which is impossible for a business to catch up to
	//With the new averages the average wait time is about 20 minutes
	public static int mostArrivals = 3;
	static Semaphore washingLines = new Semaphore(washLinesCount);//if we want to have only one person pay at a time
	static Math math;
	public static Random gen = new Random();
	static ExecutorService svc;
	public static ArrayList<Double> waitTimes = new ArrayList<Double>();
	
	/*
	 * I am not sure what kind of Executor service we should use, 
	 * I think timeThreadService might work best if we use that for customer arrival 
	 */
	
	public static void main(String[] args)
	{

		while(timePassed <= dayLength)
		{//while day is not over 
			//System.out.println("Average arrival: "+ math.ceil(average));
			int peopleArrived = (gen.nextInt(mostArrivals)+1);
			svc = Executors.newFixedThreadPool(peopleArrived);
			while(peopleArrived > 0)
			{
				double average = (((-1) * math.log(1 - gen.nextDouble())) * timeTillNewArrivals);
				double washTime = ((-1) * math.log(1 - gen.nextDouble()) * averageWashTime);
			svc.submit(() ->{
				try {
					washingLines.acquire();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException();
				}
				try
				{
					if(timePassed <= dayLength) {
					System.out.println("Car was washed for: " + math.ceil(washTime) + " Minutes and Waited for: " + math.ceil(average) +" Minutes, Customer Id: "+ Thread.currentThread().getId());
					timePassed +=math.ceil(washTime) + average;//I moved the timePassed change to right after the service is completed
					//I think this makes sense more logically since it only increment the clock if both the person had waited and then made it to were the service
					waitTimes.add(average);
					}
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
			peopleArrived--;
			//svc.shutdown();
			}
		}	
		System.out.println("Total amount of cars seen: "+ waitTimes.size() + ". The total time spent working was " + Math.floor(timePassed) + " Minutes");
		double totalWaitTime= 0, averageWaitTime;
		for(int j = 0; j < waitTimes.size(); j++)
		{
			totalWaitTime += waitTimes.get(j);
		}
		averageWaitTime = totalWaitTime / waitTimes.size();
		System.out.println("The average wait time is [" + math.ceil(totalWaitTime) +"] / [" + waitTimes.size() + "] = ["+ math.ceil(averageWaitTime) +"] minutes");

	}

}
