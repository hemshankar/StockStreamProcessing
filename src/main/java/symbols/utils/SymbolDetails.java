package symbols.utils;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SymbolDetails {

    Integer counter = 0;
    MyHashUtil myTrie = new MyHashUtil();
    public static void main(String[] args) {
        SymbolDetails details = new SymbolDetails();
        details.myTrie.add("abcd");
        details.myTrie.add("absd");
        details.myTrie.add("abdgd");
        details.myTrie.add("afdgshsfd");
        details.myTrie.add("add");
        details.myTrie.add("asdf");


        System.out.println(details.myTrie.getIndex("absd"));
        System.out.println(details.myTrie.getIndex("abdgd"));
        System.out.println(details.myTrie.getIndex("afdgshsfd"));
        System.out.println(details.myTrie.getIndex("add"));
        System.out.println(details.myTrie.getIndex("asdf"));
        System.out.println(details.myTrie.getIndex("abcdasdfdf"));


    }

}
