import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdRandom;

import java.util.HashMap;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private Synset[] synSETs;
    private HashMap<String, Bag<Integer>> hm;
    private SAP sap;
    private int size;
    
    private class Synset {
        private Bag<String> synonym;
//        private String definition;
        private Synset() {
            synonym = new Bag<String>();
        }
    }
    
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new NullPointerException();
        hm = new HashMap<String, Bag<Integer>>();
        synSETs = new Synset[2];
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            Synset synset = new Synset();
            String[] set = line[1].split(" ");
            for (int i = 0; i < set.length; i++) {
                synset.synonym.add(set[i]);
                Bag<Integer> newList = new Bag<Integer>();
                if (hm.containsKey(set[i])) {
                    newList = hm.get(set[i]);
                }
                newList.add(id);
                hm.put(set[i], newList);
            }
//            synset.definition = line[2];
            synSETs[id] = synset;
            size++;
            if (id == (synSETs.length - 1)) {
                resize(synSETs.length * 2);
            }
        }
        Digraph G = new Digraph(size);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] numbers = in.readLine().split(",");
            int id = Integer.parseInt(numbers[0]);
            for (int h = 1; h < numbers.length; h++) {
                G.addEdge(id, Integer.parseInt(numbers[h]));
            }
        }
        int rootCount = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                rootCount++;
                if (rootCount > 1)
                    throw new IllegalArgumentException();
            }
        }
        if (rootCount == 0) throw new IllegalArgumentException();
        this.sap = new SAP(G);
    }
    
    private void resize(int newSize) {
        Synset[] newSynSETs = new Synset[newSize];
        for (int i = 0; i < synSETs.length; i++) {
            newSynSETs[i] = synSETs[i];
        }
        synSETs = newSynSETs;
    }
//  
//  public int size() {
//      return size;
//  }
    
//  public void printAll() {
//      for (int i = 0; i < size; i++) {
//          System.out.print(i + "|");
//          for (String s : synSETs[i].synonym) {
//              System.out.print(s + "|");
//          }
//          System.out.println(synSETs[i].definition);
//      }
//  }
    
    public Iterable<String> nouns() {
        return hm.keySet();
    }
    
    public boolean isNoun(String word) {
        if (word == null) throw new NullPointerException();
        return hm.containsKey(word);
    }
    
    private Iterable<Integer> ids(String noun) {
        if (noun == null) throw new NullPointerException();
        return hm.get(noun);
    }
    
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new NullPointerException();
        if (!isNoun(nounA)) throw new IllegalArgumentException();
        if (!isNoun(nounB)) throw new IllegalArgumentException();
        return sap.length(ids(nounA), ids(nounB));
    }
    
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new NullPointerException();
        if (!isNoun(nounA)) throw new IllegalArgumentException();
        if (!isNoun(nounB)) throw new IllegalArgumentException();
        int root = sap.ancestor(ids(nounA), ids(nounB));
        if (root == -1)
            return null;
        StringBuilder str = new StringBuilder();
        for (String noun : synSETs[root].synonym) {
            str.insert(0, " ");
            str.insert(0, noun);
        }
        return str.toString();
    }

    public static void main(String[] args) {
      WordNet net = new WordNet(args[0], args[1]);
//      int rand1 = StdRandom.uniform(net.size()/2);
//      int rand2 = StdRandom.uniform(net.size());
//      int tempCounter = 0;
      String testNoun1 = "a";
      String testNoun2 = "b";
//      for (String noun : net.nouns()) {
//          if (tempCounter == rand1) {
//              testNoun1 = noun;
//              for (int id : net.ids(noun)) {
//                  System.out.print(id + " ");
//              }
//              System.out.println();
//          }
//          if (tempCounter == rand2) {
//              testNoun2 = noun;
//              System.out.print(noun + ":" + net.isNoun(noun) + ":");
//              for (int id : net.ids(noun)) {
//                  System.out.print(id + " ");
//              }
//              System.out.println();
//          }
//          tempCounter++;
//      }
      int dist = net.distance(testNoun1, testNoun2);
      System.out.println("words " + testNoun1 + " and " + testNoun2 
              + " are " + dist + " edges away");
      String root = net.sap(testNoun1, testNoun2);
      if (root == null) {
          root = "not exiting";
      }
      System.out.println("their closest common ancestor is " + root);
    }

}
