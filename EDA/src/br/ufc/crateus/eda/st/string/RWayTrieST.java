package br.ufc.crateus.eda.st.string;

import java.util.LinkedList;
import java.util.Queue;

public class RWayTrieST<V> implements StringST<V> {
	private static final int R = 256;
	private Node root = new Node();

	private static class Node {
		Object value;
		Node[] next = new Node[R];
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(String key) {
		Node node = get(root, key, 0);
		return (node != null) ? (V) node.value : null;
	}

	private Node get(Node r, String key, int i) {
		if (r == null)
			return null;
		if (i == key.length())
			return r;
		char ch = key.charAt(i);
		return get(r.next[ch], key, i + 1);
	}

	@Override
	public void put(String key, V value) {
		root = put(root, key, value, 0);
	}

	private Node put(Node r, String key, V value, int i) {
		if (r == null)
			r = new Node();
		if (i == key.length()) {
			r.value = value;
			return r;
		}
		char ch = key.charAt(i);
		r.next[ch] = put(r.next[ch], key, value, i + 1);
		return r;
	}

	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(String key) {
		delete(root, key, 0);
	}

	private Node delete(Node r, String key, int i) {
		if (r == null)
			return null;
		if (i == key.length())
			r.value = null;
		else {
			char ch = key.charAt(i);
			r.next[ch] = delete(r.next[ch], key, i + 1);
		}

		if (r.value != null)
			return r;
		for (Node n : r.next)
			if (n != null)
				return r;
		return null;
	}

	private void collect(Node r, String prefix, Queue<String> queue) {
		if (r != null) {
			if (r.value != null)
				queue.add(prefix);
			for (char ch = 0; ch < R; ch++)
				collect(r.next[ch], prefix + ch, queue);
		}
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
	public Iterable<String> keys() {
		Queue<String> queue = new LinkedList<>();
		collect(root, "", queue);
		return queue;
	}

	public Iterable<String> keysWithPrefix(String prefix) {
		Node node = get(root, prefix, 0);
		Queue<String> keys = new LinkedList<>();
		collect(node, prefix, keys);
		return keys;
	}

	public String longPrefixOf(String query) {
		int size = search(root, query, 0, 0);
		return query.substring(0, size);
	}

	private int search(Node node, String query, int i, int length) {
		if (node == null)
			return length;
		if (node.value != null)
			length = i;
		if (i == query.length())
			return length;
		char ch = query.charAt(i);
		return search(node.next[ch], query, i + 1, length);
	}

	public Iterable<String> KeysThatMatch(String s) {
		Queue<String> queue = new LinkedList<>();
		collect(root, s, "", queue);
		return queue;

	}

	private void collect(Node r, String s, String prefix, Queue<String> queue) {
		if (r == null)
			return;

		int d = prefix.length();

		if (d == s.length() && r.value != null)
			queue.add(prefix);

		if (d < s.length()) {

			char c = s.charAt(d);

			if (c == '.')
				for (char ch = 0; ch < R; ch++)
					collect(r.next[ch], s, prefix + ch, queue);
			else
				collect(r.next[c], s, prefix + c, queue);
		}

	}

	public static void main(String[] args) {

		RWayTrieST<Integer> tst = new RWayTrieST<>();

		tst.put("she", 4);
		tst.put("shells", 5);
		tst.put("by", 4);
		tst.put("sea", 5);
		tst.put("the", 3);
		tst.put("shore", 1);

		for (String s : tst.KeysThatMatch(".he"))
			System.out.println(s);

	}
}
