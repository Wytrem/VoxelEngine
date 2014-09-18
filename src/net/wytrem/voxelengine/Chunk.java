package net.wytrem.voxelengine;

import java.util.Random;

public class Chunk
{
	static final Random rand = new Random();
	
	/** Position du chunk en coordonnées chunk (ortho >> 4). **/
	public final int x, z;
	
	public int[][][] tiles = new int[16][16][16];
	
	public Chunk(int x, int z)
	{
		this.x = x;
		this.z = z;
		
		for (int i = 0; i < 16; i++)
		{
			for (int j = 0; j < 16; j++)
			{
				for (int k = 0; k < 16; k++)
				{
					this.tiles[i][j][k] = rand.nextInt(5) == 0 ? (rand.nextBoolean() ? Tile.stone.id : (rand.nextBoolean() ? Tile.grass.id : Tile.dirt.id)) : 0;
				}
			}
		}
	}

	public boolean isPosInChunk(int x, int y, int z)
	{
		return x >> 4 == this.x && z >> 4 == this.z;
	}

	public int getTileIdAt(int x, int y, int z)
	{
		return this.tiles[x - (this.x << 4)][y][z - (this.z << 4)];
	}
}
