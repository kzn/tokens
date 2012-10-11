package name.kazennikov.annotations;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface AnnotationXmlLoader {
	/**
	 * Load annotation from xml stream. Assumes that stream is at start of annotation tag
	 * @param type parsed annotation type
	 * @param start start offset
	 * @param end offset
	 * @param reader
	 * @return
	 */
	public Annotation load(XMLStreamReader reader) throws XMLStreamException;
	
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
		
		public abstract void parseFeatures(XMLStreamReader reader, Annotation a) throws XMLStreamException;

	}
	
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
