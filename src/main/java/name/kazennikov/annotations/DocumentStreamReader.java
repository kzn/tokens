package name.kazennikov.annotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Reader for document xml stream
 * User: kzn
 * Date: 07.10.12
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public class DocumentStreamReader {
    XMLStreamReader s;
    Map<String, AnnotationXmlLoader> anLoaders = new HashMap<String, AnnotationXmlLoader>();
    InputStream is;

    public DocumentStreamReader(XMLInputFactory factory, File fileName, Map<String, AnnotationXmlLoader> loaders)
            throws IOException, XMLStreamException {
        is = new FileInputStream(fileName);
        if(fileName.getName().endsWith(".gz"))
            is = new GZIPInputStream(is);

        s = factory.createXMLStreamReader(is);
        this.anLoaders = loaders;
    }

    public void close() throws XMLStreamException, IOException {
        s.close();
        is.close();
    }

    public Document readNext() throws XMLStreamException {

        while(s.hasNext()) {
            if(s.isStartElement() && s.getLocalName().equals(AnnotationConstants.DOCUMENT))
                return Document.read(s, anLoaders);
            s.next();
        }

        return null;
    }
}
