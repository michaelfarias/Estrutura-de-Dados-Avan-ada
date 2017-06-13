package br.ufc.crateus.eda.st.hashing;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import br.ufc.crateus.eda.st.ST;

public class SeparateChainingHashingST<K, V> implements ST<K, V> {

	private Node[] array;
	private int m;
	private int size;

	private static class Node {
		Object key;
		Object value;
		Node next;

		Node(Object key, Object value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	public SeparateChainingHashingST(int m) {
		this.array = new Node[m];
		this.m = m;
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		int i = hash(key);
		for (Node n = array[i]; n != null; n = n.next)
			if (key.equals(n.key))
				return (V) n.value;
		return null;
	}

	@Override
	public void put(K key, V value) {
		if ((size / m) >= 8)
			rehashing(2 * m);

		int i = hash(key);
		// for (Node n = array[i]; n != null; n = n.next)
		// if (key.equals(n.key)) {
		// n.value = value;
		// return;
		// }
		array[i] = new Node(key, value, array[i]);
		size++;
	}

	@SuppressWarnings("unchecked")
	private void rehashing(int newM) {
		SeparateChainingHashingST<K, V> hash = new SeparateChainingHashingST<>(newM);
		for (Node h : array) {
			for (Node node = h; node != null; node = node.next)
				hash.put((K) node.key, (V) node.value);
		}
		array = hash.array;
		m = newM;
	}

	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(K key) {
		int i = hash(key);

		array[i] = delete(array[i], key);

		size--;

		if ((size / m) <= 4)
			rehashing(m / 2);

	}

	private Node delete(Node node, K key) {
		if (node == null)
			return null;

		if (node.key.equals(key))
			return node.next;

		Node n = node;
		n.next = delete(node.next, key);

		return n;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<K> keys() {
		return null;
	}

	public void PrintkeysValues() {
		Map<Integer, Integer> map = new TreeMap<>();

		for (int i = 0; i < m; i++)
			for (Node node = array[i]; node != null; node = node.next) {
				if (map.containsKey((K) node.key)) {
					map.put((Integer) node.key, (Integer) map.get(node.key) + (Integer) node.value);
				} else
					map.put((Integer) node.key, (Integer) node.value);
			}
		for (Entry entry : map.entrySet())
			System.out.println(entry.getValue() + "x^" + entry.getKey());
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}

	public void imprmir() {
		for (int i = 0; i < m; i++)
			for (Node n = array[i]; n != null; n = n.next)
				System.out.println(n.key + " " + n.value);
	}

	public static void main(String[] args) {
		SeparateChainingHashingST<String, Integer> st = new SeparateChainingHashingST<>(1);

		st.put("s", 4);
		st.put("e", 3);
		st.put("a", 1);
		st.put("k", 2);
		st.put("r", 2);

		st.delete("r");
		st.delete("k");
		st.delete("s");
		st.delete("a");
		st.delete("e");

		st.imprmir();

	}

}
