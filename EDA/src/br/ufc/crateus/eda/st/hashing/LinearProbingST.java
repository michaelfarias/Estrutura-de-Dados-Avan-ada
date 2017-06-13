package br.ufc.crateus.eda.st.hashing;

import java.util.LinkedList;
import java.util.Queue;

import br.ufc.crateus.eda.st.ST;

public class LinearProbingST<K, V> implements ST<K, V> {

	private K[] keys;
	private V[] values;
	private int m;
	private int n = 0;

	@SuppressWarnings("unchecked")
	public LinearProbingST(int m) {
		this.keys = (K[]) new Object[m];
		this.values = (V[]) new Object[m];
		this.m = m;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}	

	@Override
	public V get(K key) {
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (key.equals(keys[i]))
				return values[i];
		return null;
	}

	@Override
	public void put(K key, V value) {
		if (n >= m / 2)
			resize(2 * m);

		int i = hash(key);

		while (keys[i] != null) {
			if (key.equals(keys[i])) {
				values[i] = value;
				return;
			}
			i = (i + 1) % m;
		}

		keys[i] = key;
		values[i] = value;
		n++;
	}

	@Override
	public boolean contains(K key) {

		// TODO Auto-generated method stub
		return get(key) != null;
	}

	@Override
	public void delete(K key) {

		if (contains(key)) {
			int i = hash(key);

			while (!key.equals(keys[i]))
				i = (i + 1) % m;

			values[i] = null;
			n--;

			if (n > 0 && n <= m / 8)
				resize(m / 2);
		}
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size() == 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return n;
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> queue = new LinkedList<>();
		for (int i = 0; i < m; i++)
			if (values[i] != null)
				queue.add(keys[i]);
		return queue;
	}

	private void resize(int M) {

		LinearProbingST<K, V> lp = new LinearProbingST<>(M);

		for (int i = 0; i < m; i++)
			if (values[i] != null)
				lp.put(keys[i], values[i]);

		this.keys = lp.keys;
		this.values = lp.values;
		this.m = lp.m;
	}

	public static void main(String[] args) {
		LinearProbingST<Integer, Integer> l = new LinearProbingST<>(16);

		l.put(1, 5);
		l.put(2, 5);
		l.put(3, 5);
		l.put(4, 5);
		l.put(5, 5);
		l.put(6, 5);
		l.put(7, 5);
		l.put(8, 5);
		l.put(9, 5);
		l.put(10, 5);
		l.put(11, 5);
		l.put(12, 5);
		l.put(13, 5);
		l.put(14, 5);
		l.put(15, 5);
		l.put(16, 5);

		l.delete(1);
		l.delete(2);
		l.delete(3);
		l.delete(4);
		l.delete(5);
		l.delete(6);
		l.delete(7);
		l.delete(8);
		l.delete(9);
		l.delete(10);
		l.delete(11);
		l.delete(12);
		l.delete(13);
		l.delete(13);

		for (Integer i : l.keys())
			System.out.println(i);
	}

}
