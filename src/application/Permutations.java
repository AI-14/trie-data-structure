/**
 * @author Amaan Izhar
 * CLASS - PERMUTATIONS:
 * Instance variables - [str] and [allPermutationList].
 * Methods (excluding constructors, getters, and setters) - permute(), permuteRec(), generateSubset(), toString(), and equals().
 */

package application;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;

public class Permutations {
    private String str;
    private ArrayList<String> permutationList;

    public Permutations() {
        str = "";
        permutationList = new ArrayList<>();
    }

    public Permutations(String str) {
        this.str = str;
        permutationList = new ArrayList<>();
    }

    public String getStr() {
        return str;
    }
    public ArrayList<String> getPermutationList() { return permutationList; }
    public void setStr(String str) {
        this.str = str;
    }
    public void setPermutationList(ArrayList<String> allPermutationList) { this.permutationList = allPermutationList; }

    /**
     * Functionality: Adds all the permutations of [str] to a list.
     * Algorithm:
     * 1. First we create a hashSet and a character array.
     * 2. Then we call a recursive method that generates all the permutations of [str].
     * 3. Then we add all the elements of the hashSet(containing the permutation of [str]).
     * 4. Then for every element in the list, we pass it to another helper method that adds all the subsets of the permuted element into the hashSet.
     * 5. Then we add the hashSet again to the list and sort it.
     */
    public void permute() {
        HashSet<String> setOfPermutations = new HashSet<>();
        char[] chArr = this.getStr().toCharArray();
        permuteRec(chArr, 0, chArr.length - 1, setOfPermutations);
        setPermutationList(new ArrayList<>(setOfPermutations));
        for(String el : this.getPermutationList()) {
            generateSubset(el, setOfPermutations);
        }
        setPermutationList(new ArrayList<>(setOfPermutations));
        Collections.sort(this.getPermutationList());
    }

    /**
     * Functionality: Helper method for permute().
     * Algorithm:
     * 1. There are 3 main parts - swapping initial characters, calling the permuteWords() recursively, and then swapping the characters again.
     * 2. For detailed explanation - search on Google or Youtube.
     */
    private void permuteRec(char[] chArr, int i, int n, HashSet<String> setOfPermutations) {
        if(i == n)
           setOfPermutations.add(String.valueOf(chArr));
       else {
           for (int j = i; j <= n; j++) {
               char temp = chArr[i];
               chArr[i] = chArr[j];
               chArr[j] = temp;

               this.permuteRec(chArr, i + 1, n, setOfPermutations);

               temp = chArr[i];
               chArr[i] = chArr[j];
               chArr[j] = temp;
           }
       }
    }

    /**
     * Functionality: Helper method for permute().
     * Algorithm:
     * 1. We traverse through the length of [el] and fix one character of [el] and
     *    then loop through the remaining characters which will generate all the subsets of [el].
     */
    private void generateSubset(String el, HashSet<String> setOfPermutations) {
        for(int i = 0; i < el.length(); i++) {
            for(int j = i + 1; j <= el.length(); j++)
                setOfPermutations.add(el.substring(i, j));
        }
    }

    @Override
    public String toString() {
        return "[String: " + getStr() + ", Permutation List: " + getPermutationList() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        else if(this.getClass() != obj.getClass())
            return false;
        else {
            Permutations pObj = (Permutations) obj;
            return this.getStr().equals(pObj.getStr()) && this.getPermutationList() == pObj.getPermutationList();
        }
    }
}
