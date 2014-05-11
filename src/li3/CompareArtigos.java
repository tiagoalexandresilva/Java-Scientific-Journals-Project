/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package li3;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Tiago
 */
public class CompareArtigos implements Comparator<Map.Entry<String, Integer>>, Serializable {
    
    @Override
    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
        if(e1.getValue()>e2.getValue()){
            return -1;
        }
        else if(e1.getValue()<e2.getValue()){
            return 1;
        }
        else{
            return 0;
        }
    }
    
}
