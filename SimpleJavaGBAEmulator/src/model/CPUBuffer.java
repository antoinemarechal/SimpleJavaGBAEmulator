package model;

public class CPUBuffer
{
	protected Object[] buffer;
	protected Object lock;
	protected Integer semaphore;
	
	public CPUBuffer()
	{
		this.buffer = new Object[2];
		this.lock = new Object();
		this.semaphore = 0;
	}
	
	public synchronized void write(Object o)
	{
		semaphore++;
		
		synchronized(lock)
		{
			lock.notify();
		}
		
		buffer[0] = o;
		
		try
		{
			wait();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace(); // TODO : Proper error logs
		}
	}
	
	public synchronized Object read()
	{
		semaphore++;
		
		synchronized(lock)
		{
			lock.notify();
		}
				
		try
		{
			wait();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace(); // TODO : Proper error logs
		}
		
		return buffer[1];
	}
	
	public synchronized void resetWriteStatus()
	{
		while(semaphore < 2)
		{
			try 
			{
				synchronized(lock)
				{
					lock.wait();
				}
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace(); // TODO : Proper error logs
			}
		}
			
		buffer[1] = buffer[0];
		buffer[0] = null;
		
		semaphore = 0;
		
		notifyAll();
	}
}
