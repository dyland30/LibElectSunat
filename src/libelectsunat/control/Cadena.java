/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package libelectsunat.control;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author dcastillo
 */
public class Cadena {

    public static String join(Collection<?> s, String delimiter) {


        StringBuilder builder = new StringBuilder();
        Iterator<?> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(delimiter);
        }
        return builder.toString();
    }

    public static String combine(String[] s, String glue) {
        int k = s.length;
        if (k == 0) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append(s[0]);
        for (int x = 1; x < k; ++x) {
            out.append(glue).append(s[x]);
        }
        return out.toString();
    }
}
