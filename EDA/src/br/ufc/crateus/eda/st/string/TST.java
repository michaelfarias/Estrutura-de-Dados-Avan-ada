package br.ufc.crateus.eda.st.string;

import java.util.LinkedList;
import java.util.Queue;

public class TST<V> implements StringST<V> {

	private int n = 0;
	private Node root;

	private class Node {
		char ch;
		V value;
		Node left, mid, right;

		public Node(char ch) {
			this.ch = ch;
		}
	}

	@Override
	public V get(String key) {
		Node node = get(root, key, 0);
		return (node != null) ? node.value : null;
	}

	private Node get(Node r, String key, int i) {
		if (r == null)
			return null;
		char ch = key.charAt(i);
		if (ch < r.ch)
			return get(r.left, key, i);
		if (ch > r.ch)
			return get(r.right, key, i);
		if (i == key.length() - 1)
			return r;
		return get(r.mid, key, i + 1);
	}

	@Override
	public void put(String key, V value) {
		if (!contains(key))
			n++;
		root = put(root, key, value, 0);
	}

	private Node put(Node r, String key, V value, int i) {

		char ch = key.charAt(i);
		if (r == null)
			r = new Node(ch);
		if (ch < r.ch)
			r.left = put(r.left, key, value, i);
		else if (ch > r.ch)
			r.right = put(r.right, key, value, i);
		else if (i == key.length() - 1)
			r.value = value;
		else
			r.mid = put(r.mid, key, value, i + 1);
		return r;
	}

	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return get(key) != null;
	}

	@Override
	public void delete(String key) {
		root = delete(root, key, 0);
	}

	private Node delete(Node r, String key, int i) {
		if (r == null)
			return null;
		Node p = r;
		if (i == key.length() - 1) {
			r.value = null;

			return p;
		}

		char ch = key.charAt(i);

		if (ch < r.ch)
			p.left = delete(r.left, key, i);
		else if (ch > r.ch)
			p.right = delete(r.right, key, i);
		else
			p.mid = delete(r.mid, key, i + 1);

		return p;

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size() == 0;
	}

	@Override
	public int size() {
		return n;
	}

	@Override
	public Iterable<String> keys() {
		Queue<String> queue = new LinkedList<>();
		collect(root, "", queue);
		return queue;
	}

	private void collect(Node r, String prefix, Queue<String> queue) {

		if (r != null) {

			if (r.value != null)
				queue.add(prefix + r.ch);

			collect(r.mid, prefix + r.ch, queue);
			collect(r.left, prefix, queue);
			collect(r.right, prefix, queue);
		}
	}

	public Iterable<String> KeysWithPrefix(String string) {
		Node r = get(root, string, 0);
		Queue<String> queue = new LinkedList<>();
		collect(r.mid, string, queue);
		return queue;

	}

	public Iterable<String> KeysThatMatch(String s) {
		Queue<String> queue = new LinkedList<>();
		collect(root, s, "", 0, queue);
		return queue;

	}

	public String longestPrefixOf(String s) {
		int length = logestPrefixOf(root, s, 0, 0);
		System.out.println(length);
		return s.substring(0, length);
	}

	private int logestPrefixOf(Node r, String s, int i, int j) {

		if (r == null || s == null)
			return 0;

		if (i < s.length()) {
			char ch = s.charAt(i);

			if (r.value != null)
				j = i;

			if (ch < r.ch)
				return logestPrefixOf(r.left, s, i, j);

			else if (ch > r.ch)
				return logestPrefixOf(r.right, s, i, j);

			else
				return logestPrefixOf(r.mid, s, i + 1, j);
		} else
			return j;
	}

	private void collect(Node r, String s, String prefix, int i, Queue<String> queue) {
		if (r == null)
			return;
		if (i < s.length()) {

			char c = s.charAt(i);

			if (c == '.' || c < r.ch)
				collect(r.left, s, prefix, i, queue);
			if (c == '.' || c > r.ch)
				collect(r.right, s, prefix, i, queue);
			if (c == '.' || c == r.ch) {

				if (i == s.length() - 1 && r.value != null)
					queue.add(prefix + r.ch);

				collect(r.mid, s, prefix + r.ch, i + 1, queue);
			}
		}
	}

	public String floor(String key) {
		return longestPrefixOf(key);

	}

	public String ceiling(String key) {
		String max = key;
		for (String s : KeysWithPrefix(key))
			if (s.length() > max.length())
				return s;

		return max;
	}

	public static void main(String[] args) {

		TST<Integer> tst = new TST<>();

		tst.put("she", 4);
		tst.put("shells", 5);
		tst.put("by", 4);
		tst.put("sea", 5);
		tst.put("the", 3);
		tst.put("shore", 1);
		tst.put("shellsort", 7);

	}

	private static int contarSubstring(int l, String texto) {
		TST<Integer> tst = new TST<>();

		String key = "";

		for (int i = 0; i <= texto.length() - l; i++) {
			for (int j = i; j < (i + l); j++)
				key += texto.charAt(j);

			tst.put(key, i);
			key = "";
		}

		for (String s : tst.keys())
			System.out.println(s);

		return tst.size();

	}

}