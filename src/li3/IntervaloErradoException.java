/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package li3;

import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class IntervaloErradoException extends Exception implements Serializable {

  
    public IntervaloErradoException() {
    }

    public IntervaloErradoException(String msg) {
        super(msg);
    }
}
