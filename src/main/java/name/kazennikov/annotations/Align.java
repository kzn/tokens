package name.kazennikov.annotations;

import java.util.ArrayList;
import java.util.List;


/**
 * Alignement methods
 */
public class Align {


    /**
     * Get aligned groups from given list of annotations
     * @param left
     * @param right
     * @param leftAligned
     * @param rightAligned
     */
    public static void getAligned(List<Annotation> left, List<Annotation> right,
                                        List<List<Annotation>> leftAligned, List<List<Annotation>> rightAligned) {

        leftAligned.clear();
        rightAligned.clear();

        int leftPos = 0;
        int rightPos = 0;
        int leftStart = 0;
        int rightStart = 0;

        while(leftPos < left.size() && rightPos < right.size()) {
            Annotation leftStartAnn = left.get(leftStart);
            Annotation rightStartAnn = right.get(rightStart);

        	
            Annotation leftAnn = left.get(leftPos);
            Annotation rightAnn = right.get(rightPos);

            
            if(leftAnn.getStart() == rightAnn.getStart()) {
            	// check for phantom annotation
            	if(leftAnn.getStart() != leftStartAnn.getStart())
            		leftStart = leftPos;
            	
            	// check for phantom annotation
            	if(rightAnn.getStart() != rightStartAnn.getStart())
            		rightStart = rightPos;
            }

            // aligned
            if(leftAnn.getEnd() == rightAnn.getEnd()) {
                // continue iteration while the left annotations continue to have same end offset as right annotation
                if(leftPos + 1 < left.size() && left.get(leftPos + 1).getEnd() == rightAnn.getEnd()) {
                	leftPos++;
                	continue;
                }
                
                // continue iteration while the right annotations continue to have same end offset as left annotation
                if(rightPos + 1 < right.size() && right.get(rightPos + 1).getEnd() == leftAnn.getEnd()) {
                	rightPos++;
                	continue;
                }

                
                

                leftPos++;
                rightPos++;
                
                leftAligned.add(new ArrayList<Annotation>(left.subList(leftStart, leftPos)));
                rightAligned.add(new ArrayList<Annotation>(right.subList(rightStart, rightPos)));


            } else if(leftAnn.getStart() > rightAnn.getStart() || leftAnn.getEnd() > rightAnn.getEnd()) {
                rightPos++;
            } else if(leftAnn.getStart() < rightAnn.getStart() || rightAnn.getEnd() > leftAnn.getEnd()) {
                leftPos++;
            } else {
                throw new IllegalStateException();
            }
        }

    }
}
