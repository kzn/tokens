package name.kazennikov.annotations;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;


public abstract class AbstractAnnotationRender {
	
	public static class OffsetComparator implements Comparator<Annotation> {

		@Override
		public int compare(Annotation o1, Annotation o2) {
			int res = o1.getStart() - o2.getStart();
			if(res != 0)
				return res;

			return o2.getEnd() - o1.getEnd();
		}
	}
	
	public static final Comparator<Annotation> COMPARATOR = new OffsetComparator();
	
	public abstract void onStartContent() throws AnnotationRenderException;
	public abstract void onEndContent() throws AnnotationRenderException;
	
	public abstract void onAnnotationStart(Annotation annotation) throws AnnotationRenderException;
	public abstract void onAnnotationEnd(Annotation annotation) throws AnnotationRenderException;
	public abstract void onText(String text) throws AnnotationRenderException;
	
	
	public void render(List<Annotation> annotations) throws AnnotationRenderException {
		Collections.sort(annotations, COMPARATOR);
        Stack<Annotation> stack = new Stack<Annotation>();
        
        String content = annotations.get(0).getText();
        int offset = annotations.get(0).getStart();
        int charIndex = 0;
        int targetIndex = 0;
        
        onStartContent();
       
        while(charIndex < content.length()) {
            Annotation open = targetIndex < annotations.size()? annotations.get(targetIndex) : null;
            Annotation close = !stack.isEmpty()? stack.peek() : null;
           
            int openStart = open != null? open.getStart() - offset : content.length();
            int closeEnd = close != null? close.getEnd() - offset : content.length();
           
            if((int)closeEnd == charIndex && close != null) {
                onAnnotationEnd(close);
                stack.pop();
                continue;
            }
           
            if((int)openStart == charIndex && open != null) {
            	onAnnotationStart(open);
                stack.push(open);
                targetIndex++;
                continue;
            }
            int len = Math.min(closeEnd, openStart) - charIndex;
                        
            onText(content.substring(charIndex, charIndex + len));
           
            charIndex += len;
        }
       
        while(!stack.isEmpty()) {
            Annotation a = stack.pop();
            onAnnotationEnd(a);
        }
        
        onEndContent();
    }
}
