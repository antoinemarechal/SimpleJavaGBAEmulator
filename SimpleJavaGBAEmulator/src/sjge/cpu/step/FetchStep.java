package sjge.cpu.step;

import model.CPUBuffer;

public class FetchStep extends Thread
{	
	private CPUBuffer outputBuffer;
	
	public FetchStep(CPUBuffer firstBuffer) 
	{
		this.outputBuffer = firstBuffer;
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			// TODO : Concrete fetch from memory
			
			outputBuffer.write(null);
		}
	}
}
