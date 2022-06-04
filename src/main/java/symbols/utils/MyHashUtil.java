package symbols.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHashUtil {

    MyTrie trie = new MyTrie();

    public void add(String str){
        trie.add(str);
    }

    public void addAll(List<String> strList){
        strList.forEach(x -> trie.add(x));
    }

    public int getIndex(String symbol){
        return trie.getIndex(symbol);
    }

    private class MyTrieNode{
        public Integer index = null;
        public Map<Character, MyTrieNode> children = null;
        public MyTrieNode(){}
    }

    private class MyTrie{
        MyTrieNode root = new MyTrieNode();
        int counter = 0;
        public void add(String str){
            char [] arr = str.toCharArray();
            MyTrieNode r = root;
            String locString = "";
            boolean found = true;

            for(char c: arr){
                if(r.children == null){
                    r.children = new HashMap<>();
                    found = false;
                }
                if(r.children.get(c) == null){
                    found = false;
                    r.children.put(c, new MyTrieNode());
                }
                r = r.children.get(c);
            }
            if(!found) {
                r.index = counter++;
            }
        }


        public Integer getIndex(String str){
            MyTrieNode r = root;
            char [] arr = str.toCharArray();
            try{
                for(char c: arr){
                    r = r.children.get(c);
                }
                return r.index;
            }catch(Exception e){
                return -1;
            }
        }
    }

}
