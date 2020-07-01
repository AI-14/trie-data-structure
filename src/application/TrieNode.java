/**
 * @author Amaan Izhar
 * CLASS - TRIENODE:
 * Instance variables - [letter], [nodeMap] and [isWord]
 * Methods (excluding constructors, getters, and setters) - toString() and equals().
 */

package application;

import java.util.HashMap;


public class TrieNode {
    private String letter;
    private HashMap<Character, TrieNode> nodeMap;
    private boolean isWord;

    public TrieNode() {
        this.letter = "";
        this.nodeMap = new HashMap<>();
        this.isWord = false;
    }

    public TrieNode(String letter, HashMap<Character, TrieNode> nodeMap, boolean isWord) {
        this.letter = letter;
        this.nodeMap = nodeMap;
        this.isWord = isWord;
    }

    public String getLetter() {
        return letter;
    }

    public HashMap<Character, TrieNode> getNodeMap() {
        return nodeMap;
    }

    public boolean getIsWord() {
        return isWord;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setNodeMap(HashMap<Character, TrieNode> nodeMap) {
        this.nodeMap = nodeMap;
    }

    public void setIsWord(boolean word) {
        isWord = word;
    }

    @Override
    public String toString() {
        return "[Letter: " + getLetter() + ", NodeMap Keys: " + getNodeMap().keySet() + ", IsWord: " + getIsWord() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        else if(this.getClass() != obj.getClass())
            return false;
        else {
            TrieNode trieNodeObj = (TrieNode) obj;
            return this.getLetter().equals(trieNodeObj.getLetter()) && this.getNodeMap() == trieNodeObj.getNodeMap()
                    && this.getIsWord() == trieNodeObj.getIsWord();
        }
    }
}
