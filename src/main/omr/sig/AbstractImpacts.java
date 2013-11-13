//----------------------------------------------------------------------------//
//                                                                            //
//                        A b s t r a c t I m p a c t s                       //
//                                                                            //
//----------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//  Copyright © Herve Bitteur and others 2000-2013. All rights reserved.
//  This software is released under the GNU General Public License.
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.
//----------------------------------------------------------------------------//
// </editor-fold>
package omr.sig;

import omr.constant.Constant;
import omr.constant.ConstantSet;

/**
 * Class {@code AbstractImpacts} is an abstract implementation of
 * {@link GradeImpacts} interface
 *
 * @author Hervé Bitteur
 */
public abstract class AbstractImpacts
        implements GradeImpacts
{
    //~ Static fields/initializers ---------------------------------------------

    private static final Constants constants = new Constants();

    //~ Methods ----------------------------------------------------------------
    @Override
    public double getIntrinsicRatio ()
    {
        return constants.intrinsicRatio.getValue();
    }

    //----------//
    // toString //
    //----------//
    @Override
    public String toString ()
    {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getImpactCount(); i++) {
            if (sb.length() > 0) {
                sb.append(" ");
            }

            sb.append(String.format("%s:%.2f", getName(i), getImpact(i)));
        }

        return sb.toString();
    }

    //--------------//
    // computeGrade //
    //--------------//
    protected double computeGrade ()
    {
        double global = 1d;
        double totalWeight = 0d;

        for (int i = 0; i < getImpactCount(); i++) {
            double weight = getWeight(i);
            totalWeight += weight;

            if (weight != 0) {
                global *= Math.pow(getImpact(i), weight);
            }
        }

        return getIntrinsicRatio() * Math.pow(global, 1 / totalWeight);
    }

    //~ Inner Classes ----------------------------------------------------------
    //-----------//
    // Constants //
    //-----------//   
    private static final class Constants
            extends ConstantSet
    {
        //~ Instance fields ----------------------------------------------------

        Constant.Ratio intrinsicRatio = new Constant.Ratio(
                0.8,
                "Reduction ratio applied on any intrinsic grade");

        Constant.Ratio defaultMinGrade = new Constant.Ratio(
                0.1,
                "Default minimum acceptable grade");

    }
}
