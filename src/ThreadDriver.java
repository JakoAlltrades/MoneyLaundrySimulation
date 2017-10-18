import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;

public class ThreadDriver extends Thread{
	public static int NumberOfCustomers = 6;
	public static int x = 0;
	public static final int parkingSpaces = 5;
	public static final int employees = 1;
	static Semaphore parkingLotQueue = new Semaphore(parkingSpaces);
	static Semaphore employeeQueue = new Semaphore(employees);
	static Semaphore paymentQueue = new Semaphore(1);
	static Random gen = new Random();
	public static Object lock = new Object();
	public static ScheduledExecutorService svc = Executors.newScheduledThreadPool(6);
	public static void main(String[] args) throws InterruptedException {
		
		for(int i = 0; i < NumberOfCustomers; i++)
		{
			MyThread t = new MyThread();
			svc.submit(() -> {
				t.start();
			});
			
		}
		
	}

}
