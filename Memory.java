
public class Memory
{
	short[] mAddress;
	short mPointer;
	Memory(int off, int setNum)
	{
		mAddress = new short[32];	
		for (int i = 0; i < 32; i++)
		{
			mAddress[i] = 0;
		}
		mPointer = 0;
	}
	
	void addToMemory(short address)
	{
		mAddress[mPointer] = address;
		mPointer++;
	}
	
	void clear()
	{
		int i;
		for(i = 0; i < mAddress.length; i++)
		{
			mAddress[i] = 0;
		}
	}
}
