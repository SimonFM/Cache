import java.util.*;
import java.math.*;

public class Cache
{
	private int mL,mN,mK;
	private int[][] mSet;
	private int mOffset,mSetNum,mTagSize;
	private Lru mLruSet[];
	private int mHits, mMisses;
	
	/*
	   Creates an empty cache with parameters L, N and K
    */
	Cache(int lValue, int nValue, int kValue)
	{
		mL = lValue;
		mK = kValue;
		mN = nValue;
		
		mOffset = 4;
		mSetNum = (int)(Math.log(mN) / Math.log(2));
		mTagSize = mL - (mOffset - mSetNum);
		
		mSet = new int[mN][mK];
		mLruSet = new Lru[mN];
		int i,j;
		for(i = 0; i < mN; i++)
		{
			mLruSet[i] = new Lru(mK);
			for(j = 0 ; j < mK; j++)
			{
				mSet[i][j] = -1;
			}
		}
		mHits = 0;
		mMisses = 0;
	}
	
	/*
	  A method that extracts the tag from an address, by
	  shifting by a certain number of bits.
    */
	int andValuesSet(short address, int num)
	{
		int i;
		int mask = 0x0000;
		for(i = 0; i < num; i++)
		{
			mask = mask << 1;
			mask = mask | 1;
		}
		mask = mask << mOffset;
		mask = mask & address;
		mask = mask >> mOffset;
		return mask;
	}
	/*
	   A method that extracts the tag out of an address passed
	   in to the method.
	*/
	short andValuesTag(short address, int num)
	{
		int i;
		int mask = 0x0000;
		for(i = 0; i < num; i++)
		{
			mask = mask << 1;
			mask = mask | 1;
		}
		mask = (mask & address);
		mask = (mask >> (mOffset+mSetNum));
		return (short) mask;
	}
	/* 
	   There was no hit, the cache selects the least
	   recently used cache line and replaces the contents
	   with the new tag, and the number of misses is 
	   incremented.
	*/
	void addToCache(short address)
	{
		short tag;
		int i, setNum, index;
		setNum = andValuesSet(address,mSetNum); 
		tag = andValuesTag(address,mTagSize);

		boolean hit = false;
		for(i = 0; i < mK; i++)
		{
			if(mSet[setNum][i] == tag)
			{
				hit = true;
				mLruSet[setNum].leastRecentlyUsedMethod(i);
				mHits++;
			}
		}
		if(hit == false)
		{
			index = mLruSet[setNum].getLeastRecentlyUsed();
			mLruSet[setNum].leastRecentlyUsedMethod(index);
			mSet[setNum][index] = tag;
			mMisses++;
		}
	}
	
	// Prints out the cache and its contents.
	void printCache()
	{
		int i,j;
		String cache = "";
		System.out.println("Tag(s)");
		for( i = 0; i < mN; i++)
		{
			for( j = 0; j < mK; j++)
			{
				cache += " " +mSet[i][j];
			}
			cache += "\n";
		}
		System.out.println(cache);
	}
	
	// A simple method that prints out 
	// the number of hits and misses
	void printHitAndMisses()
	{
		System.out.println("Hits: "+mHits+" Misses: "+mMisses);
	}
	
	/*
	   A function that simulates adding to a cache for any
	   cache passed in to it.
	*/
	static void runCache(Cache a, String[] memS, short[] mem)
	{
		int i;
		for(i = 0; i < mem.length; i++)
		{
			System.out.println(memS[i]);
			a.addToCache(mem[i]);
			a.printCache();
		}
	}
	
	public static void main(String[] args)
	{
		// 32 memory addresses, 16 bits long.
		short mem[] = {0x0000,0x0004,0x000c,0x2200,
					   0x00d0,0x00e0,0x1130,0x0028,
					   0x113c,0x2204,0x0010,0x0020,
					   0x0004,0x0040,0x2208,0x0008,
					   0x00a0,0x0004,0x1104,0x0028,
					   0x000c,0x0084,0x000c,0x3390,
					   0x00b0,0x1100,0x0028,0x0064,
					   0x0070,0x00d0,0x0008,0x3394};
		
		String memS[] = {"0x0000","0x0004","0x000c","0x2200",
			             "0x00d0","0x00e0","0x1130","0x0028",
			             "0x113c","0x2204","0x0010","0x0020",
			             "0x0004","0x0040","0x2208","0x0008",
			             "0x00a0","0x0004","0x1104","0x0028",
			             "0x000c","0x0084","0x000c","0x3390",
			             "0x00b0","0x1100","0x0028","0x0064",
			             "0x0070","0x00d0","0x0008","0x3394"};
				
		// The four caches described in Q 1.
		Cache myCache1 = new Cache(16,8,1);
		Cache myCache2 = new Cache(16,4,2);
		Cache myCache3 = new Cache(16,2,4);
		Cache myCache4 = new Cache(16,1,8);
		
		runCache(myCache1,memS,mem);
		runCache(myCache2,memS,mem);
		runCache(myCache3,memS,mem);
		runCache(myCache4,memS,mem);
		
		// prints out the hits and misses in the caches
		System.out.println("L = 16, N = 8, K = 1");
		myCache1.printHitAndMisses();
		System.out.println("L = 16, N = 4, K = 2");
		myCache2.printHitAndMisses();
		System.out.println("L = 16, N = 2, K = 4");
		myCache3.printHitAndMisses();
		System.out.println("L = 16, N = 1, K = 8");
		myCache4.printHitAndMisses();
	}
}