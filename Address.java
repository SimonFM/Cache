
public class Address
{
	int[][][][] mAddress;
	int mSetNum, mOffset;
	Address(int offset)
	{
		mAddress = new int[1][2][2][2];
		mOffset = offset;
	}
	
	void setAddress(int a, int b, int c, int d)
	{
		mAddress[0][0][0][0] = a;
		mAddress[0][1][0][0] = b;
		mAddress[0][0][1][0] = c;
		mAddress[0][0][0][1] = d;
	}
	
	void setSetNum()
	{
		
	}
}
