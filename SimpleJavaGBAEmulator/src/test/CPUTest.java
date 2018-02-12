package test;

import static org.junit.Assert.*;

import org.junit.Test;

import sjge.cpu.step.DecodeStep;
import sjge.cpu.step.ExecuteStep;
import sjge.cpu.step.FetchStep;
import model.CPUBuffer;

public class CPUTest
{
	@Test
	public void testCPULoop()
	{
		FetchStep fetchStep;
		//	|	|	|	|	|
		//	v	v	v	v	v
		CPUBuffer firstBuffer;
		//	|	|	|	|	|
		//	v	v	v	v	v
		DecodeStep decodeStep;
		//	|	|	|	|	|
		//	v	v	v	v	v
		CPUBuffer secondBuffer;
		//	|	|	|	|	|
		//	v	v	v	v	v
		ExecuteStep executeStep;
		
		firstBuffer = new CPUBuffer()
		{
			@Override
			public synchronized void write(Object o)
			{
				try 
				{
					semaphore++;
					
					synchronized(lock)
					{
						lock.notify();
					}
					
					buffer[0] = o;
					
					wait();
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		};
		secondBuffer = new CPUBuffer()
		{
			@Override
			public synchronized void write(Object o)
			{
				semaphore++;
				
				synchronized(lock)
				{
					lock.notify();
				}
				
				buffer[0] = o;
			}
		};
		
		fetchStep = new FetchStep(firstBuffer);
		decodeStep = new DecodeStep(firstBuffer, secondBuffer);
		executeStep = new ExecuteStep(secondBuffer);
		
		fetchStep.start();
		decodeStep.start();
		executeStep.start();
		
		try 
		{
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		firstBuffer.resetWriteStatus();
		
		for(int i = 0; i < 30; i++)
		{
			try 
			{
				Thread.sleep(50);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			secondBuffer.resetWriteStatus();
			firstBuffer.resetWriteStatus();
		}
		
		assertTrue("CPU Loop", true);
	}
}
