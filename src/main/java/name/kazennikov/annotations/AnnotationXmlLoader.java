package name.kazennikov.annotations;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Annotation loader. Helper interface to {@link DocumentStreamReader}
 * <p>
 * Reads and processes &lt;annotation&gt; tag and returns {@link Annotation} object.
 * <p>
 * Expects that annotation will contain at least following attributes:
 * <ul>
 * <li> type - annotation type
 * <li> start - start offset
 * <li> end - end offset
 * </ul> 
 * <p>
 * This loader is also responsible for parsing annotation features
 * 
 * 
 * @author Anton Kazennikov
 *
 */
public interface AnnotationXmlLoader {

	/**
	 * Load annotation from xml stream. Assumes that stream is positioned at start of annotation tag
	 * @param reader xml stream reader
	 * @return Annotation
	 */
	public Annotation load(XMLStreamReader reader) throws XMLStreamException;

	/**
	 * Abstract annotation loader. Expects that the annotation XML node contains this attributes:
	 * <ul>
	 * <li> type - annotation type
	 * <li> start - start offset
	 * <li> end - end offset
	 * </ul>
	 * @author kzn
	 *
	 */
	public static abstract class Abstract implements AnnotationXmlLoader {

		@Override
		public Annotation load(XMLStreamReader reader) throws XMLStreamException {
			String type = reader.getAttributeValue(null, "type");
			int start = Integer.parseInt(reader.getAttributeValue(null, "start"));
			int end = Integer.parseInt(reader.getAttributeValue(null, "end"));
			Annotation a = new Annotation(null, type, start, end);
			parseFeatures(reader, a);
			return a;
		}

		
		/**
		 * Parse features of this annotation
		 * @param reader xml reader
		 * @param a target annotation 
		 * @throws XMLStreamException
		 */
		public abstract void parseFeatures(XMLStreamReader reader, Annotation a) throws XMLStreamException;

	}
	
	
	/**
	 * Base annotation loader
	 * @author kzn
	 *
	 */
	public static class Base extends Abstract {
		
		@Override
		public void parseFeatures(XMLStreamReader reader, Annotation a) throws XMLStreamException {
			while(reader.hasNext()) {
				reader.next();
				if(reader.isEndElement() && reader.getLocalName().equals("annotation"))
					break;
				if(reader.isStartElement()) {
					String tag = reader.getLocalName();
					String value = reader.getElementText();
					
					if(tag.equals(Annotation.TYPE) || tag.equals(Annotation.KIND)) {
						value = value.intern();
					}
					
					a.setFeature(tag, value);
				}
			}
		}
	}
	
	

}
