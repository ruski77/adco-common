package adcowebsolutions.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class ImageUtilsTest {

	private ImageUtils imageUtils;

	@Before
	public void setUp() {
		imageUtils = new ImageUtils();
	}

	@Test
	public void testGetMimeFormatName() {

		assertNotNull(imageUtils);
	
		assertEquals("Formatted name is not the same as in mime type", "jpeg", imageUtils.getMimeFormatName("image/jpeg"));
	
		assertEquals("Formatted name is not the same as in mime type", "jpg", imageUtils.getMimeFormatName("image/jpg"));
	
		assertEquals("Formatted name is not the same as in mime type", "png", imageUtils.getMimeFormatName("image/png"));
	
		assertEquals("Formatted name is not the same as in mime type", "gif", imageUtils.getMimeFormatName("image/gif"));
	}
}
