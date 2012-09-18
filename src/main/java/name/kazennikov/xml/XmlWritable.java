package name.kazennikov.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface XmlWritable<T> {
	public void write(XMLStreamWriter stream, T object) throws XMLStreamException;
}
