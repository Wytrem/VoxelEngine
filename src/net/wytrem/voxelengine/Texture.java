package net.wytrem.voxelengine;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class Texture
{
	public int id;
	public int width;
	public int height;

	private Texture(int id, int width, int height)
	{
		this.id = id;
		this.width = width;
		this.height = height;
	}

	public static Texture loadTexture(String name)
	{
		BufferedImage bimg = null;
		try
		{
			bimg = ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(name));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load Texture: " + name);
			e.printStackTrace();
		}

		int[] pixels = new int[bimg.getWidth() * bimg.getHeight()];
		bimg.getRGB(0, 0, bimg.getWidth(), bimg.getHeight(), pixels, 0, bimg.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(bimg.getWidth() * bimg.getHeight() * 4);

		for (int y = 0; y < bimg.getHeight(); y++)
		{
			for (int x = 0; x < bimg.getWidth(); x++)
			{
				int pixel = pixels[(y * bimg.getWidth() + x)];

				buffer.put((byte) (pixel >> 16 & 0xFF));

				buffer.put((byte) (pixel >> 8 & 0xFF));

				buffer.put((byte) (pixel & 0xFF));

				buffer.put((byte) (pixel >> 24 & 0xFF));
			}
		}

		buffer.flip();

		int textureID = GL11.glGenTextures();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, bimg.getWidth(), bimg.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		return new Texture(textureID, bimg.getWidth(), bimg.getHeight());
	}

	public static Texture createEmptyTexture()
	{
		return new Texture(0, 0, 0);
	}

	public void bind()
	{
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
	}

	public void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void delete()
	{
		GL11.glDeleteTextures(this.id);
	}
}
