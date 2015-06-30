import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
public class ImageEdit
{
	public File imageFile;
	public BufferedImage image;	
	/**
	* Creates a new ImageEdit containing an image with the specified width and height.
	* @param w The width in pixels of the new Image. 
	* @param h The height in pixels of the new Image. 
	*/
	public ImageEdit(int w, int h)
	{
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	}
	/**
	* Creates a new ImageEdit using the image in the specified file.
	* @param file The file for the image.
	*/
	public ImageEdit(File file) 
	{
		try
		{
			imageFile = file;
			BufferedImage tempImage = ImageIO.read(file);
			image = new BufferedImage(tempImage.getWidth(), tempImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			image.getGraphics().drawImage(tempImage, 0, 0, null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	/**
	* @return The width of the image.
	*/
	public int getWidth()
	{
		return image.getWidth();
	}
	/**
	* @return The height of the image.
	*/
	public int getHeight()
	{
		return image.getHeight();
	}
	/**
	* @param x The x coordinate of the pixel.
	* @param y The y coordinate of the pixel.
	* @return The RGB value of the specified pixel.
	*/
	public int getPixel(int x,int y)
	{
		return image.getRGB(x,y);
	}
	/**
	* @param x The x coordinate of the top left corner of the rectangle.
	* @param y The y coordinate of the top left corner of the rectangle.
	* @param w The width if the rectangle.
	* @param h The height if the rectangle.
	* @return A 2D array of RGB values of the rectangle.
	*/
	public int[][] getPixel(int x,int y,int w,int h)
	{
		if (x+w>getWidth())
			w = getWidth() - x;
		if (y+h>getHeight())
			h = getHeight() - y;
		int[][] ret = new int[h][w];
		int k = 0;
		int l = 0;
		for (int i=x;i<x+w;i++)
		{
			l = 0;
			for (int j=y;j<y+h;j++)
			{
				ret[l][k] = image.getRGB(i,j);
				l++;
			}
			k++;
		}
		return ret;
	}
	/**
	* @return The file assosiated with the image. 
	*/
	public File getFile()
	{
		return imageFile;
	}
	/**
	* Sets the RGB value at the specified pixel.
	* @param x The x coordinate of the pixel.
	* @param y The y coordinate of the pixel.
	* @param rgb The RBG value to set the pixel to.
	*/
	public void setPixel(int x, int y, int rgb)
	{
		if (x>getWidth()-1 || y>getHeight()-1)
			return;
		image.setRGB(x,y,rgb);
	}
	/**
	* Sets the RGB value at the specified pixel and with possible the surrounding pixels.
	* @param x The x coordinate of the pixel.
	* @param y The y coordinate of the pixel.
	* @param t The thickness of the pixels to be set. This value must be odd.
	* @param rgb The RBG value to set the pixel to.
	*/
	public void setPixel(int x, int y, int t, int rgb)
	{
		if (t%2 == 0)
			return;
		int dist = (t - 1) / 2;
		for (int i=x-dist;i<=x+dist;i++)
		{
			for (int j=y-dist;j<=y+dist;j++)
			{
				setPixel(i,j,rgb);
			}
		}
	}
	/**
	* Sets the value of line of pixels between the specified coordinates.
	* @param x1 The x value of the first coordinate.
	* @param y1 The y value of the first coordinate.
	* @param x2 The x value of the second coordinate.
	* @param y2 The y value of the second coordinate.
	* @param rgb The RBG value to set the pixels to.
	*/
	public void setPixelLine(int x1, int y1, int x2, int y2, int rgb)
	{
		if(x1>x2)
		{
			x1 ^= x2;
			x2 ^= x1;
			x1 ^= x2;
			y1 ^= y2;
			y2 ^= y1;
			y1 ^= y2;
		}
		int dx = x2 - x1;
		int dy = y2 - y1;
		
		int diff = 2 * dy - dx;
		image.setRGB(x1,y1,rgb);
		
		int y = y1;
		int x = x1;
		if (dx == 0)
		{
			for (;y<y2;y++)
			{
				setPixel(x,y,rgb);
			}
			return;
		}
		for (;x<=x2;x++)
		{
			if (diff>=0)
			{
				y++;
				image.setRGB(x,y,rgb);
				diff += 2 * dy - 2 * dx;
			}
			else
			{
				image.setRGB(x,y,rgb);
				diff += 2 * dy;
			}
		}
	}
	/**
	* Sets the value of line of pixels between the specified coordinates.
	* @param x1 The x value of the first coordinate.
	* @param y1 The y value of the first coordinate.
	* @param x2 The x value of the second coordinate.
	* @param y2 The y value of the second coordinate.
	* @param t The thickness of the pixels to be set. This value must be odd.
	* @param rgb The RBG value to set the pixels to.
	*/
	public void setPixelLine(int x1, int y1, int x2, int y2, int t, int rgb)
	{
		if(x1>x2)
		{
			x1 ^= x2;
			x2 ^= x1;
			x1 ^= x2;
			y1 ^= y2;
			y2 ^= y1;
			y1 ^= y2;
		}
		int dx = x2 - x1;
		int dy = y2 - y1;
		
		int diff = 2 * dy - dx;
		setPixel(x1,y1,t,rgb);
		
		int y = y1;
		int x = x1;
		if (dx == 0)
		{
			for (;y<y2;y++)
			{
				setPixel(x,y,t,rgb);
			}
			return;
		}
		for (;x<=x2;x++)
		{
			if (diff>=0)
			{
				y++;
				setPixel(x,y,t,rgb);
				diff += 2 * dy - 2 * dx;
			}
			else
			{
				setPixel(x,y,t,rgb);
				diff += 2 * dy;
			}
		}
	}
	/**
	* Sets the value of  rectangle of pixels leaving the inside untouched. 
	* @param x1 The x coordinate of the top-left corner of the rectangle.
	* @param y1 The y coordinate of the top-left corner of the rectangle.
	* @param x2 The x coordinate of the bottom-right corner of the rectangle.
	* @param y2 The y coordinate of the bottom-right corner of the rectangle.
	* @param t The thickness of the pixels to be set. This value must be odd.
	* @param rgb The RBG value to set the pixels to.
	*/
	public void setPixelRect(int x1, int y1, int x2, int y2, int t, int rgb)
	{
		int x, y, w, h;
		if (x1<x2)
		{
			x = x1;
			w = x2 - x1;
		}
		else
		{
			x = x2;
			w = x1 - x2;
		}
		if (y1<y2)
		{
			y = y1;
			h = y2 - y1;
		}
		else
		{
			y = y2;
			h = y1 - y2;
		}
		setPixelLine(x,y,x+w,y,t,rgb);
		setPixelLine(x,y,x,y+h,t,rgb);
		setPixelLine(x+w,y,x+w,y+h,t,rgb);
		setPixelLine(x,y+h,x+w,y+h,t,rgb);
	}
	/**
	* Sets the value of the specified pixel and any attached pixels of the same color to an RGB value.
	* @param x The x coordinate of the pixel.
	* @param y The y coordinate of the pixel.
	* @return The RGB value of the specified pixel.
	*/
	public void fillPixel(int x, int y, int rgb)
	{
		if (x>getWidth()-1 || y>getHeight()-1)
			return;
		int original = getPixel(x,y);
		setPixel(x,y,rgb);
		if (y-1>=0) 
		{
			if(getPixel(x,y-1)== original)
			{
				fillPixel(x,y-1, rgb);
			}
		}
		if (y+1<getHeight())
		{
			if(getPixel(x,y+1) == original)
				fillPixel(x,y+1, rgb);
		}
		if (x-1>=0)
		{
			if(getPixel(x-1,y) == original)	
				fillPixel(x-1,y, rgb);
		}
		if (x+1<getWidth())
		{
			if(getPixel(x+1,y) == original)
				fillPixel(x+1,y, rgb);
		} 
	}
	/**
	* Sets the value of a rectangle of pixels and fills the inside.
	* @param x The x coordinate of the top-left corner of the rectangle.
	* @param y The y coordinate of the top-left corner of the rectangle.
	* @param w The width if the rectangle.
	* @param h The height if the rectangle.
	* @param rgb The RBG value to set the pixels to.
	*/
	public void fillPixelRect(int x, int y, int w, int h, int rgb)
	{
		if (x>getWidth()-1 || y>getHeight()-1)
			return;
		if (x+w>getWidth())
			w = getWidth() - x;
		if (y+h>getHeight())
			h = getHeight() - y;
		for (int i=x;i<x+w;i++)
		{
			for (int j=y;j<y+h;j++)
			{ 
				image.setRGB(i,j,rgb);
			}
		}
	}
	/**
	* Sets the value of a rectangle of pixels and fills the inside. 
	* @param x1 The x coordinate of the top-left corner of the rectangle.
	* @param y1 The y coordinate of the top-left corner of the rectangle.
	* @param x2 The x coordinate of the bottom-right corner of the rectangle.
	* @param y2 The y coordinate of the bottom-right corner of the rectangle.
	* @param t The thickness of the pixels to be set. This value must be odd.
	* @param rgb The RBG value to set the pixels to.
	*/
	public void fillPixelRect(int x1, int y1, int x2, int y2, int t, int rgb)
	{
		int x, y, w, h;
		if (x1<x2)
		{
			x = x1;
			w = x2 - x1;
		}
		else
		{
			x = x2;
			w = x1 - x2;
		}
		if (y1<y2)
		{
			y = y1;
			h = y2 - y1;
		}
		else
		{
			y = y2;
			h = y1 - y2;
		}
		fillPixelRect(x,y,w,h,rgb);
	}
	/**
	* Sets all pixels to white.
	*/
	public void clearPixel()
	{
		for (int i=0;i<getWidth();i++)
		{
			for (int j=0;j<getHeight();j++)
			{
				image.setRGB(i,j,-1);
			}
		}
	}
	/**
	* Sets the file associated with the image.
	* @param file The location of the file.
	*/
	public void setFile(String file) throws IOException
	{
		imageFile = new File(file);
	}
	/**
	* Sets the file associated with the image.
	* @param file The file associated witht he image.
	*/
	public void setFile(File file)
	{
		imageFile = file;
	}
	/**
	* Sets the new dimensions of the image.
	* @param w The new width.
	* @param h The new height.
	*/
	public void setDimensions(int w, int h)
	{
		BufferedImage tempImage = image;
		image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(tempImage, 0, 0, null);
	}
	/**
	* @return The image stored in this ImageEdit.
	*/
	public BufferedImage buildImage()
	{
		return image;
	}
	/**
	* Writes the image to the associated file.
	*/
	public void write() throws IOException
	{
		ImageIO.write(image, getExtension(imageFile), imageFile);
	}
	/**
	* @param file The file.
	* @return The files extension.
	*/
	public static String getExtension(File file)
	{
		String name = file.getName();
		int pos = name.lastIndexOf('.');
		return name.substring(pos+1);
	}
}
