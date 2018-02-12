package sjge.cpu.step;

import model.CPUBuffer;

public class ExecuteStep extends Thread
{
	private CPUBuffer inputBuffer;
	
	public ExecuteStep(CPUBuffer secondBuffer) 
	{
		this.inputBuffer = secondBuffer;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			inputBuffer.read();
			
			// TODO : Concrete execution
		}
	}
}
