import java.util.Random;
import java.util.concurrent.Semaphore;

public class MyThread extends Thread{
	public static int x = 0;
	public static final int parkingSpaces = 5;
	public static final int employees = 1;
	private static Semaphore parkingLotQueue = new Semaphore(parkingSpaces);
	private static Semaphore employeeQueue = new Semaphore(employees);
	private static Semaphore paymentQueue = new Semaphore(1);
	static Random gen = new Random();
	public static Object lock = new Object();
	@Override
	public void run()
	{
		//synchronized can be uded on the method signiture.
		try {
			parkingLotQueue.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		try{
			System.out.println("Customer: "+ Thread.currentThread().getId()+" aquired a parking spot.");
			//System.out.println("Customer: "+ Thread.currentThread().getId()+" Is waiting to order");
			try {
				employeeQueue.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException();
			}
			try{
				System.out.println("The employee is now taking customer: "+Thread.currentThread().getId()+"'s order");
				int num = gen.nextInt(15000)+5000;
				pause(num);
				try {
					paymentQueue.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException();
				}
				try
				{
					System.out.println("The employee is now taking customer: "+Thread.currentThread().getId()+"'s payment.");
					int nextNum = gen.nextInt(15000)+5000;
					pause(nextNum);
				}finally 
				{
					paymentQueue.release();
				}
			}finally
			{
				employeeQueue.release();
			}
			int num = gen.nextInt(15000)+5000;
			pause(num);
			System.out.println("Food cooked for " + num);
			try{
				employeeQueue.acquire();
			}catch(InterruptedException e)
			{
				throw new RuntimeException();
			}
			try{
				System.out.println("Employee delivered "+"Customer: "+Thread.currentThread().getId()+" their meal.");
				int nextNum = gen.nextInt(15000)+5000;
				pause(nextNum);
			}finally{
				employeeQueue.release();
			}
			System.out.println("Customer: "+Thread.currentThread().getId()+" is enjoying their meal ");
			int nextNum = gen.nextInt(15000)+5000;
			pause(nextNum);
			System.out.println("Customer: "+Thread.currentThread().getId()+" has vacated a parking spot");
		}finally
		{
			parkingLotQueue.release();
		}
		
			
		//parkingLotQueue.release();
		
		
	}
	private void pause(long m){
		try{
			sleep(m);
		}catch(Exception e)
		{
			
		}
	}
}