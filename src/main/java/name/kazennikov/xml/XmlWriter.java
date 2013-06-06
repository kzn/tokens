package name.kazennikov.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.Document;

/**
 * User: kazennikov
 * Date: 11.09.12
 * Time: 15:27
 */
public interface XmlWriter {
    /**
     * Writes object to xml writer
     * @param writer
     * @return true, if writer succeded on writing this object to xml stream
     * @throws XMLStreamException
     */
    public boolean writeTo(XMLStreamWriter writer, XmlWriter rootWriter, Object object) throws XMLStreamException;
    public boolean isApplicable(Object o);

    public static class TrivialXmlWriter implements XmlWriter {

        @Override
        public boolean writeTo(XMLStreamWriter writer, XmlWriter rootWriter, Object object) throws XMLStreamException {
            writer.writeStartElement("value");
            writer.writeAttribute("class", object.getClass().getName());
            writer.writeCharacters(object.toString());
            writer.writeEndElement();
            return true;
        }

		@Override
		public boolean isApplicable(Object o) {
			return true;
		}
    }
    
    public static class DocumentWriter implements XmlWriter {

    	@Override
    	public boolean writeTo(XMLStreamWriter writer, XmlWriter rootWriter, Object object) throws XMLStreamException {
    		Document doc = (Document) object;

    		writer.writeStartElement("doc");
    		writer.writeAttribute("text", doc.getText());
    		writer.writeAttribute("root", doc.getType()); // get root annotation

    		for(Annotation a : doc.getAllAnnotations()) {
    			writer.writeStartElement("annotation");
    			writer.writeAttribute("type", a.getType());
    			writer.writeAttribute("start", Integer.toString(a.getStart()));
    			writer.writeAttribute("end", Integer.toString(a.getEnd()));

    			writer.writeStartElement("features");
    			for(Map.Entry<String, Object> e : a.getFeatureMap().entrySet()) {
    				if(e.getValue() != null) {
    					writer.writeStartElement("feat");
    					writer.writeAttribute("name", e.getKey());
    					rootWriter.writeTo(writer, rootWriter, e.getValue());
    					writer.writeEndElement();
    				}
    			}
    			writer.writeEndElement(); // features

    			writer.writeEndElement(); // annotation



    		}

    		writer.writeEndElement();

    		return true;
    	}

		@Override
		public boolean isApplicable(Object o) {
			return o instanceof Document;
		}

    }

    public static class DefaultXmlWriter implements XmlWriter {
    	Map<Class<?>, XmlWriter> writers = new HashMap<Class<?>, XmlWriter>();
    	TrivialXmlWriter defaultWriter = new TrivialXmlWriter();
    	
    	public void setWriter(Class<?> clazz, XmlWriter writer) {
    		writers.put(clazz, writer);
    	}
    	
    	public XmlWriter getWriter(Object o) {
    		Class<?> c = o.getClass();
    		
    		// probe superclasses
    		Class<?> sup = c;
    		
    		while(sup != null) {
    			if(writers.containsKey(sup))
    				return writers.get(sup);
    			
    			sup = sup.getSuperclass();
    		}
    		
    		
    		
    		for(Class<?> s : c.getInterfaces()) {
    			if(writers.containsKey(s))
    				return writers.get(s);
    		}
    		
    		return defaultWriter;
    	}

		@Override
		public boolean writeTo(XMLStreamWriter writer, XmlWriter xmlWriter, Object object) throws XMLStreamException {
			
			XmlWriter w = getWriter(object);
			
			if(w == null || !w.isApplicable(object) || !w.writeTo(writer, this, object)) {
				defaultWriter.writeTo(writer, this, object);
			}

			return true;
		}
		
		public boolean writeTo(XMLStreamWriter writer, Object object) throws XMLStreamException {
			return writeTo(writer, this, object);
		}

		@Override
		public boolean isApplicable(Object o) {
			return true;
		}
    }
    
    public static class ListWriter implements XmlWriter {

		@Override
		public boolean writeTo(XMLStreamWriter writer, XmlWriter xmlWriter, Object object) throws XMLStreamException {
			
			List<?> l = (List<?>) object;
			
			for(Object o : l) {
				xmlWriter.writeTo(writer, xmlWriter, o);
			}
			

			return true;
		}

		@Override
		public boolean isApplicable(Object o) {
			return o instanceof List<?>;
		}
    	
    }
}
