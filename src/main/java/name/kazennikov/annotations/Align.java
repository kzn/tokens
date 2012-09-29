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
            Annotation leftAnn = left.get(leftPos);
            Annotation rightAnn = right.get(rightPos);

            if(leftAnn.getStart() == rightAnn.getStart()) {
                leftStart = leftPos;
                rightStart = rightPos;
            }

            // aligned
            if(leftAnn.getEnd() == rightAnn.getEnd()) {
                leftAligned.add(new ArrayList<Annotation>(left.subList(leftStart, leftPos + 1)));
                rightAligned.add(new ArrayList<Annotation>(right.subList(rightStart, rightPos + 1)));

                leftPos++;
                rightPos++;

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
