/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package li3;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Tiago
 */
public class CompareDecr implements Comparator<Map.Entry<Map.Entry<String, String>, Integer>>, Serializable{
    @Override
    public int compare(Entry<Entry<String, String>, Integer> o1, Entry<Entry<String, String>, Integer> o2) {
        return o2.getValue().compareTo(o1.getValue());
    }
}
