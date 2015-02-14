import java.util.*;

public class Lru
{
	boolean lru[][];
	int mK;
	/*
	  Constructor that creates a K x K matrix of booleans.
	*/
	Lru(int k)
	{
		lru = new boolean[k][k];
		int i, j;
		for(i = 0; i < k; i++)
		{
			for(j = 0; j < k; j++)
			{
				lru[i][j] = false;
			}
		}
		mK = k;
	}
	/*
	  	 Returns the index of the least recently used
	     cache line in a set, by counting the number of
	     zeroes in a row and returning the index of the
	     row that has K zeroes.
	*/
	int getLeastRecentlyUsed()
	{
		int numberOfZeroes = 0;
		int i, j;
		for(i = 0; i < mK ; i++)
		{
			for(j = 0; j < mK; j++)
			{
				if(lru[i][j] == false)
				{
					numberOfZeroes++;
				}
			}
			if(numberOfZeroes == mK) return i;
			numberOfZeroes = 0;
		}
		return -1;
	}
	/*
	   Give a parameter, this method implements the 
	   real LRU described in the slides, with a matrix.
	*/
	void leastRecentlyUsedMethod(int i)
	{
		int j;
		for(j = 0; j < mK;j++)
		{
			lru[i][j] = true;
		}
		for(j = 0; j < mK;j++)
		{
			lru[j][i] = false;
		}
	}
	/*
	   A simple method to print out the LRU matrix.
	*/
	void printLRU()
	{
		int i, j;
		String toPrint = "";
		for(i = 0; i < mK; i++)
		{
			for(j = 0; j < mK;j++)
			{
				toPrint = (!lru[i][j])?toPrint+"0 ":toPrint+"1 ";
			}
			toPrint = toPrint + "\n";
		}
		System.out.println(toPrint);
	}
}
