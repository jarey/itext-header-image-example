package jarey.itext_header_image_example;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Example on how to add an image on header or footer (added on every page created) 
 * placed on an absolute position.
 * 
 * Example created with the indications found on the following SO thread:
 *  https://stackoverflow.com/questions/12942133/how-to-add-an-image-to-my-header-in-itext-generated-pdf
 * 
 */
public class PdfGenerator {

	public static void main(String[] args) throws DocumentException, IOException {
		  Document document = new Document(PageSize.A4, 36, 36, 154, 54);
		  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HeaderImage.pdf"));
		  HeaderFooter event = new HeaderFooter();
		  writer.setPageEvent(event);
		  document.open();
		  //Just creating content in order to generate multiple pages in the example pdf output.
		  for(int i=0;i<1000;i++) {
			  document.add(new Paragraph("Hello World! : "+i+" \n"));			  
		  }
		  document.close();
	}

	static class HeaderFooter extends PdfPageEventHelper {
		private Image img;
		
		/*
		 * In order to create the img object only once, and for example purposes, i instantiate the image here,
		 * with its scaling and its absolute positioning. If this is included in json-to-pdf and as a comment without any knowloedge of its
		 * implementation, i think it would be nice to instantiate the objects arguments as part of the header, once, 
		 * in a way similar to this (maybe not limited to the constructor of the class, but making sure they're only creating once and printed once per page)
		 * */
		public HeaderFooter() throws BadElementException, MalformedURLException, IOException {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("yogthos.png").getFile());
			this.img = Image.getInstance(file.getPath());
			this.img.scaleToFit(100, 100);
			this.img.setAbsolutePosition(25,700);
		}
		
		/**
		 * Manage the content of the header. On this example we simply add the image with the absolute positioning on the top of the page.
		 * One should be aware that, the only facility this method gives its that it will be called on every page start.
		 * The position of the image or content in general you manage here, it is not restricted to be inside the header positioning so
		 * user must be aware of that and refer to propper positioning in order to arrange the header right.
		 * @param writer
		 * @param document
		 */
		@Override
		public void onStartPage(PdfWriter writer,Document document) {
			try {
				writer.getDirectContent().addImage(this.img);
			} catch (Exception x) {
				x.printStackTrace();
			}
	    }

		/**
		 * Manage footer content (it can be really managed on the onStartPage too, but for simplicity and order,
		 * it would be recommended to arrange footer here).
		 * @param writer
		 * @param document
		 */
		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			try {
				//Since we're only illustrating header content, behaviour, we do not do anything here, but the behaviour should be the same as showed on the
				// onStartPage method with the footer content. 
			} catch (Exception x) {
				x.printStackTrace();
			}

		}

	}
}

