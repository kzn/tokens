package name.kazennikov.annotations;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Predicate;

import junit.framework.TestCase;

public class SimpleAnnotationTests extends TestCase {
	
	@Test
	public void testTokenizer() {
		Document d = new Document("doc", "foo bar");
		UnicodeTokenizer tokenizer = new UnicodeTokenizer();
		tokenizer.annotate(d);
		assertEquals(3, d.get(Annotation.TOKEN).size());
	}

    @Test
    public void testTokenizerSeps() {
        Document d = new Document("doc", "foo=bar");
        UnicodeTokenizer tokenizer = new UnicodeTokenizer();
        tokenizer.annotate(d);
        assertEquals(3, d.get(Annotation.TOKEN).size());

    }
	
	@Test
	public void testSentSplitter() {
		Document d = new Document("doc", "foo bar. Bar foo.");
		UnicodeTokenizer tokenizer = new UnicodeTokenizer();
		tokenizer.annotate(d);
		SentenceSplitter ss = new SentenceSplitter(new HashSet<String>(), false);
		ss.annotate(d);
		
		assertEquals(2, d.get(SentenceSplitter.SENT).size());
	}
	
	@Test
	public void testContrainedRetrieval() {
		Document d = new Document("doc", "foo bar. Bar foo baz.");
		UnicodeTokenizer tokenizer = new UnicodeTokenizer();
		tokenizer.annotate(d);
		SentenceSplitter ss = new SentenceSplitter(new HashSet<String>(), false);
		ss.annotate(d);
		Annotation sent = d.get(SentenceSplitter.SENT).get(1);
		List<Annotation> l = d.getAnnotationsWithin(sent, new Predicate<Annotation>() {
			
			@Override
			public boolean apply(Annotation input) {
				return true;
			}
		});
		
		
		assertEquals(6, l.size());

	}

	
	
	

}