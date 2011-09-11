package adcowebsolutions.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
* This class is responsible for carrying functions related to
* re-scaling images.
*
* @author Russell Adcock
*/
public class ImageUtils {
	
	/**
	* This method is responsible for reducing an image size to a specified
	* width and height.
	*
	* @param unScaledImage - The image to be rescaled
	* @param width - The new width of the scaled image
	* @param height - The new height of the scaled image
	*
	* @return - The rescaled image
	*
	* @throws IOException
	*/
	public byte[] rescaleImage(byte[] unScaledImage, int width, int height, String mimeType) throws IOException {

		byte[] rescaledImageAsRawBytes = null;
	
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(unScaledImage));
		ResampleOp resampleOp = new ResampleOp(width, height);
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
		BufferedImage rescaledImage = resampleOp.filter(image, null);
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(rescaledImage, getMimeFormatName(mimeType), baos);
		baos.flush();
		rescaledImageAsRawBytes = baos.toByteArray();
		baos.close();
 
		return rescaledImageAsRawBytes;
	}
	
	public String getMimeFormatName(String mimeType) {
		int index = mimeType.indexOf("/");
		return mimeType.substring(index+1);
	}
	
	public static StreamedContent stream(byte[] image, String mimeType) {
		ByteArrayInputStream stream = new ByteArrayInputStream(image);
		return new DefaultStreamedContent(stream, mimeType);
	}
}
