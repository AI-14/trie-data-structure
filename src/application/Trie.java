/**
 * @author Amaan Izhar
 * CLASS - TRIE:
 * Instance variables - [root] and [count].
 * Methods (excluding constructors, getters, and setters) - incCount(), insert(), delete(), contains(), isPrefix(), allWordsPrefix(),
 *                                                          print(), size(), countNodes(), isEmpty(), clear(), toString(), and equals().
 */

package application;

import java.util.ArrayList;
import java.util.Collections;


public class Trie {
    private TrieNode root;
    private int count;

    public Trie() {
        root = new TrieNode();
        count = 0;
    }
    
    public TrieNode getRoot() {
        return root;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setRoot(TrieNode root) {
        this.root = root;
    }
    
    public void setCount(int count) {
        this.count = count;
    }

    private void incCount(int c) {
        this.count += c;
    }
    
    /**
     * Functionality: Inserts [s] into the trie.
     * Algorithm:
     * 1. First we convert [s] to upperCase (as a precaution) and point [temp] to the [root] of the trie.
     * 2. Then we traverse through the string and check the following:
     *    If the character at the ith position is already present in the hashMap of the trieNode,
     *    we move to the next trieNode pointed by that [ch] of the hashMap, otherwise, we create a new trieNode
     *    and put into the hashMap ([ch] and [node]) and make the [temp] point to that newly created node.
     * 3. After the traversal is complete, we have formed a word. Hence, we assign [true] value in the leafNode of the trie.
     */
    public void insert(String s) {
        String str = s.toUpperCase();
        TrieNode temp = getRoot();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(temp.getNodeMap().containsKey(ch)) {
                temp = temp.getNodeMap().get(ch);
            }
            else {
                TrieNode node = new TrieNode();
                temp.getNodeMap().put(ch, node);
                temp = temp.getNodeMap().get(ch);
                temp.setLetter(ch + "");
            }
        }
        temp.setIsWord(true);
    }

    /**
     * Functionality: Deletes [s] from the trie.
     * Algorithm:
     * 1. First we convert [s] to upperCase (as a precaution).
     * 2. We point [temp] to root and keep a [tracker] to track the node to be deleted if it does not have any other subset of the word within.
     * 3. We traverse through [str] and if we find a node whose [isWord] is [true] and it has more than one key in its hashMap, we mark it as
     *    [deleteNodesBelow] and also mark the character to be removed from the hashMap. Otherwise, we point [temp] to [tracker].
     * 4. Finally based on the conditions, we either delete the whole node or change only the status of [isWord].
     */
    public void delete(String s) {
        String str = s.toUpperCase();
        TrieNode temp = getRoot(), deleteNodesBelow = null;
        char deleteChar = ' ';
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            TrieNode tracker = temp.getNodeMap().get(ch);
            if(temp.getNodeMap().size() > 1 || temp.getIsWord()) {
                deleteNodesBelow = temp;
                deleteChar = ch;
            }
            temp = tracker;
        }
        if(deleteNodesBelow == null) {
            deleteNodesBelow = getRoot();
            deleteNodesBelow.getNodeMap().remove(s.charAt(0));
        }
        else if(temp.getNodeMap().isEmpty())
            deleteNodesBelow.getNodeMap().remove(deleteChar);
        else
            temp.setIsWord(false);
    }

    /**
     * Functionality: Checks if [s] is in the trie or not.
     * Algorithm:
     * 1. First we convert [s] to upperCase (as a precaution).
     * 2. We make [temp] and point it to the [root] of the trie.
     * 3. We traverse through the string and check the following:
     *    If [ch] is in keySet, then point the [temp] node to the next node that corresponds to that [ch] and store into [result] the status of
     *    the that nodes's [isWord], otherwise, make the [result] as [false] and break;
     * 4. Finally just return the [result].
     */
    public boolean contains(String s) {
        String str = s.toUpperCase();
        boolean result = false;
        TrieNode temp = getRoot();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(temp.getNodeMap().containsKey(ch)) {
                temp = temp.getNodeMap().get(ch);
                result = temp.getIsWord();
            }
            else {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Functionality: Checks is [p] is a prefix of a word in the trie or not.
     * Algorithm:
     * 1. First we convert [p] to upperCase (as a precaution).
     * 2. We make a [temp] variable and point it to the [root] of the trie.
     * 3. We traverse through the string and we check if the [ch] is present in the list or not and accordingly return [result].
     */
    public boolean isPrefix(String p) {
        String str = p.toUpperCase();
        TrieNode temp = getRoot();
        boolean result = true;
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(!temp.getNodeMap().containsKey(ch)) {
                result = false;
                break;
            }
            else
                temp = temp.getNodeMap().get(ch);

        }
        return result;
    }

    /**
     * Functionality: Returns all the words that start with the prefix [p].
     * Algorithm:
     * 1. First we convert [p] to upperCase (as a precaution) and point [temp] to the root node.
     * 2. We create an array of strings (default size = 10) and also an arrayList of [keys].
     * 3. We start traversing through the string [p].
     * 4. We create a list [prefixWordList] to store the words starting from the prefix [p] and then pass the current node, a sub-string and a list to a recursive
     *    method. This method will take care of finding all the words and keep adding them to the list.
     * 5. Finally we make the size of the array as the size of the list and sort the list. Then we copy the elements from the list to the array and return the array.
S     */
    public String[] allWordsPrefix(String p) {
        String str = p.toUpperCase();
        String[] allWordsArray = new String[10];
        TrieNode temp = getRoot();
        for(int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            temp = temp.getNodeMap().get(ch);
        }
        ArrayList<String> prefixWordList = new ArrayList<>();
        VisitAllNodesRec(temp, str.substring(0, str.length() - 1), prefixWordList);
        Collections.sort(prefixWordList);
        allWordsArray = new String[prefixWordList.size()];
        for(int i = 0; i < allWordsArray.length; i++)
            allWordsArray[i] = prefixWordList.get(i);
        return allWordsArray;
    }

    /**
     * Functionality: Prints all the words of the trie.
     * Algorithm:
     * 1. We point a [temp] variable to the [root] of the trie.
     * 2. Then we create a list of all the keys present in the hashMap of the [root].
     * 3. We iterate through the arrayList, and pass every node in the root's hashMap, an empty string and a list to a recursive method.
     * 4. Now, our recursive method will store all the words.
     * 5. We sort the list and return it (we are returning because it is used in GUI for listView).
     */
    public ArrayList<String> print() {
        ArrayList<String> allWords = new ArrayList<>();
        TrieNode temp = getRoot();
        ArrayList<Character> initialKeys = new ArrayList<>(temp.getNodeMap().keySet());
        for (Character key : initialKeys)
            VisitAllNodesRec(temp.getNodeMap().get(key), "", allWords);
        Collections.sort(allWords);
        return allWords;
    }

    /**
     * Functionality: Helper method for print() and allWordsPrefix().
     * Algorithm:
     * 1. In this method, we use DFS traversal for printing all the node's content.
     * 2. For every node, we first add the [letter] of the node to a string and then store all the keys of the node's hashMap into an arrayList.
     * 3. Then recursively follow the step 2 till the following conditions are met:
     *    a. If node has formed a word i.e. [true] but its hashMap is not empty, in this case we follow the recursive printing.
     *    b. If node has formed a word and its hashMap is empty, we add the word to the list, otherwise recursive approach is executed.
     */
    private void VisitAllNodesRec(TrieNode node, String s, ArrayList<String> allWords) {
        String str = s;
        str += node.getLetter();
        if(node.getIsWord() && node.getNodeMap() != null) {
            ArrayList<Character> keys = new ArrayList<>(node.getNodeMap().keySet());
            for(Character key : keys)
                VisitAllNodesRec(node.getNodeMap().get(key), str, allWords);
        }
        if(node.getIsWord())
            allWords.add(str);
        else {
            ArrayList<Character> keys = new ArrayList<>(node.getNodeMap().keySet());
            for(Character key : keys)
                VisitAllNodesRec(node.getNodeMap().get(key), str, allWords);
        }
    }

    /**
     * Functionality: Returns the size of the trie.
     * Algorithm:
     * 1. First return -1 if trie is empty.
     * 2. Otherwise, point [temp] to the root node and store its keys in a list. Then add the list's size to the [count].
     * 3. Then, for every [ch], pass it to the recursive method and that method will execute a similar approach for counting the nodes (that have a valid letter in them).
     * 4. Finally, we return the [count].
     */
    public int size() {
        if(isEmpty())
            return -1;
        else {
            setCount(0);
            TrieNode temp = getRoot();
            ArrayList<Character> initialKeys = new ArrayList<>(temp.getNodeMap().keySet());
            incCount(initialKeys.size());
            for(Character ch : initialKeys) {
                 countNodes(temp.getNodeMap().get(ch));
            }
            return getCount();
        }
    }

    /**
     * Functionality: Helper method for size().
     * Algorithm:
     * 1. This method's approach and execution is same as size().
     */
    private void countNodes(TrieNode node) {
        if(node.getNodeMap().isEmpty() && node.getIsWord())
            incCount(0);
        else if(node.getNodeMap().isEmpty() && !node.getIsWord()) {
            setCount(0);
        }
        else {
            ArrayList<Character> keys = new ArrayList<>(node.getNodeMap().keySet());
            incCount(keys.size());
            for(Character ch : keys) {
               countNodes(node.getNodeMap().get(ch));
            }
        }
    }

    /**
     * Functionality: Checks if the trie is empty or not.
     * Algorithm:
     * 1. Check if the size is 0 or not.
     */
    public boolean isEmpty() {
        return getRoot().getNodeMap().isEmpty();
    }

    /**
     * Functionality: Clears the trie.
     * Algorithm:
     * 1. Set root node to null.
     */
    public void clear() {
        setRoot(null);
    }

    @Override
    public String toString() {
        return "[Root exists?: " + !isEmpty() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        else if(this.getClass() != obj.getClass())
            return false;
        else {
            Trie trieObj = (Trie) obj;
            return this.getRoot() == trieObj.getRoot() && this.getCount() == trieObj.getCount();
        }
    }
}
