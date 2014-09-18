package net.wytrem.voxelengine;

import java.util.ArrayList;

public class World
{
	ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
	
	public World()
	{
//		for (int i = -2; i < 2; i++)
//		{
//			for (int j = -2; j < 2; j++)
//			{
//				this.addChunkAt(i, j);
//			}
//		}
		this.addChunkAt(0, 0);
	}
	
	public void addChunkAt(int x, int z)
	{
		if (this.getChunkAt(x << 4, 0, z << 4) == null)
		{
			this.chunkList.add(new Chunk(x, z));
		}
	}
	
	public int getTileIdAt(int x, int y, int z)
	{
		Chunk chunk = this.getChunkAt(x, y, z);
		
		if (chunk != null)
		{
			return chunk.getTileIdAt(x, y, z);
		}
		else
		{
			return 0;
		}
	}
	
	public Tile getTileAt(int x, int y, int z)
	{
		int id = this.getTileIdAt(x, y, z);
		
		if (id <= 0 || id >= Tile.tiles.length)
		{
			return null;
		}
		
		return Tile.tiles[id];
	}
	
	public Chunk getChunkAt(int x, int y, int z)
	{
		Chunk chunk = null;
		
		for (int i = 0; i < this.chunkList.size(); i++)
		{
			chunk = this.chunkList.get(i);
			
			if (chunk != null && chunk.isPosInChunk(x, y, z))
			{
				return chunk;
			}
		}
		
		return null;
	}
}
