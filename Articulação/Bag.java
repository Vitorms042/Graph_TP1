/**
 * A classe {@code Bag} representa um conjunto (multiset) de itens genéricos, 
 * permitindo a inserção e iteração sobre os elementos de forma arbitrária.
 * Esta implementação utiliza uma lista ligada simples com uma classe interna 
 * estática {@code Node}.
 *
 * Para mais detalhes, consulte a Seção 1.3 de 
 * <i>Algorithms, 4th Edition</i> por Robert Sedgewick e Kevin Wayne.
 *
 * @see <a href="https://algs4.cs.princeton.edu/13stacks">Seção 1.3</a> 
 * @see <i>Algorithms, 4th Edition</i> por Robert Sedgewick e Kevin Wayne
 * @param <Item> o tipo genérico de cada item na bag
 */



import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first; 
    private int n;            

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag() {
        first = null;
        n = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
