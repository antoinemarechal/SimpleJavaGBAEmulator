package sjge.cpu.step;

import model.CPUBuffer;

public class DecodeStep extends Thread 
{
	private CPUBuffer inputBuffer;
	private CPUBuffer outputBuffer;
	
	public DecodeStep(CPUBuffer firstBuffer, CPUBuffer secondBuffer) 
	{
		this.inputBuffer = firstBuffer;
		this.outputBuffer = secondBuffer;
	}

	@Override
	public void run() 
	{
		while(true)
		{
			inputBuffer.read();
			
			// TODO : Concrete decoding
			outputBuffer.write(null);
		}
	}
}
