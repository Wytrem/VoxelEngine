package net.wytrem.voxelengine;


import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera
{
	private Vector3f position;
	private float yaw = 0;
	private float pitch = 0;
	private World world;

	public Camera(float x, float y, float z, World world)
	{
		this.position = new Vector3f(x, y, z);
		this.world = world;
	}

	public void yaw(float amount)
	{
		this.yaw += amount;
	}

	public void pitch(float amount)
	{
		this.pitch += amount;
	}

	public void walkForward(float distance)
	{
		float paddingX = distance * (float) Math.sin(Math.toRadians(this.yaw));
		float paddingZ = distance * (float) Math.cos(Math.toRadians(this.yaw));
		
		Tile tile = this.world.getTileAt((int) (this.position.x - paddingX), (int) (this.position.y), (int) (this.position.z + paddingZ));
		System.out.println(tile);
		if (tile == null || tile.isSolid())
		{
			this.position.x -= paddingX;
			this.position.z += paddingZ;
		}
		
	}

	public void walkBackwards(float distance)
	{
		this.position.x += distance * (float) Math.sin(Math.toRadians(this.yaw));
		this.position.z -= distance * (float) Math.cos(Math.toRadians(this.yaw));
	}

	public void strafeLeft(float distance)
	{
		this.position.x -= distance * (float) Math.sin(Math.toRadians(this.yaw - 90));
		this.position.z += distance * (float) Math.cos(Math.toRadians(this.yaw - 90));
	}
	
	public void strafeRight(float distance)
	{
		this.position.x -= distance * (float) Math.sin(Math.toRadians(this.yaw + 90));
		this.position.z += distance * (float) Math.cos(Math.toRadians(this.yaw + 90));
	}
	
	public void jump(float distance)
	{
		this.position.y += distance;
	}
	
	public void lookThrough()
	{
		GL11.glRotatef(this.pitch, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(this.yaw, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(this.position.x, this.position.y, this.position.z);
	}

	public Vector3f getPosition()
	{
		return this.position;
	}

}