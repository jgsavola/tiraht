/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tiraht;

/**
 *
 * @author jgsavola
 */
public class Pair<First, Second> {
    First first;
    Second second;

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", '" + second + "')";
    }
}
