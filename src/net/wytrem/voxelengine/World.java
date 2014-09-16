package net.wytrem.voxelengine;

import java.util.ArrayList;

public class World
{
	ArrayList<Chunk> chunkList = new ArrayList<Chunk>();
	
	
	
	
	public Chunk getChunkAt(int x, int y, int z)
	{
		
		Chunk chunk = null;
		
		for (int i = 0; i < this.chunkList.size(); i++)
		{
			chunk = this.chunkList.get(i);
			
			if (chunk != null && chunk.isPosInChunk(x,y, z))
			{
				return chunk;
			}
		}
		
		return null;
	}
}
