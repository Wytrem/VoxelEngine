package net.wytrem.voxelengine;

public class Chunk
{
	/** Position du chunk en coordonnées chunk (ortho >> 4). **/
	public final int x, z;
	
	public Chunk(int x, int z)
	{
		this.x = x;
		this.z = z;
	}

	public boolean isPosInChunk(int x, int y, int z)
	{
		return x >> 4 == this.x && z >> 4 == this.z;
	}
}
