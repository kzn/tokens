package name.kazennikov.annotations.gate;

import java.util.Map;

import gate.AnnotationSet;
import gate.FeatureMap;
import gate.util.GateException;
import gnu.trove.map.hash.TIntIntHashMap;
import name.kazennikov.annotations.Annotation;
import name.kazennikov.annotations.Document;

public class GateDocumentConvertor {
	/**
	 * Converts annotation document to GATE document. Converts given document in following steps:
	 * <ul>
	 * <li> create GATE document with annotation document content
	 * <li> copy all annotation document features to gate document features
	 * <li> copy all annotations with their features <b>N.B. doesn't copy data object of annotation</b>
	 * </ul>
	 * 
	 * <p> 
	 * 
	 * Currently, doesn't preserve annotations ids, but can generate a mapping annotation id -> GATE annotation id
	 * 
	 * @param doc source document
	 * @param targetAnnotationSet target annotation set name in GATE document
	 * @return converted GATE document
	 * @throws GateException 
	 */
	public static gate.Document convert(Document doc, String targetAnnotationSet, TIntIntHashMap idmap) throws GateException {
		gate.Document gateDoc = gate.Factory.newDocument(doc.getText());
		AnnotationSet as = gateDoc.getAnnotations(targetAnnotationSet);
		
		gateDoc.getFeatures().putAll(doc.getFeatureMap());
		
		for(Annotation a : doc.getAllAnnotations()) {
			FeatureMap fm = gate.Factory.newFeatureMap();
			fm.putAll(a.getFeatureMap());
			
			int newID = as.add((long) a.getStart(), (long) a.getEnd(), a.getType(), fm);
			
			if(idmap != null) {
				idmap.put(a.getId(), newID);
			}
		}
		
		
		
		return gateDoc;
	}
	
	/**
	 * Convert GATE document to annotation document
	 * @param gateDoc source GATE document
	 * @param documentAnnotationType target document annotation type
	 * @param idmap optional id annotation mapping 
	 * @param annotationSetNames source annotation set names for annotation conversion
	 * @return annotation document
	 * @throws GateException
	 */
	public static Document convert(gate.Document gateDoc, String documentAnnotationType, TIntIntHashMap idmap, String... annotationSetNames) throws GateException {
		Document doc = new Document(documentAnnotationType, gateDoc.getContent().toString());
		
		for(String annotationSetName : annotationSetNames) {
			for(gate.Annotation a : gateDoc.getAnnotations(annotationSetName)) {
				Annotation ann = new Annotation(doc, a.getType(),  a.getStartNode().getOffset().intValue(), a.getEndNode().getOffset().intValue());
				
				for(Map.Entry<Object, Object> e : a.getFeatures().entrySet()) {
					if(e.getKey() instanceof String) {
						ann.setFeature((String) e.getKey(), e.getValue());
					}
				}
				
				int newID = doc.addAnnotation(ann);
				
				if(idmap != null) {
					idmap.put(a.getId(), newID);
				}
			}
		}
		
		doc.sortAnnotations();

		return doc;
	}

}
