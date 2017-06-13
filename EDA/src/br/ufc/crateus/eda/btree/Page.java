package br.ufc.crateus.eda.btree;

import br.ufc.crateus.eda.st.BinarySearchST;
import br.ufc.crateus.eda.st.OrderedST;

public class Page<K extends Comparable<K>> {
	
	private int m;
	private boolean bottom;
	
	private OrderedST<K, Long> st;
	private long offset;
	
	public Page(int m, boolean bottom, long offset) {
		this.st = new BinarySearchST<>(m);
		this.m = m;
		this.bottom = bottom;
		this.offset = offset;
	}

	
	public void insert(K key, Long offset) {
		st.put(key, offset);
	}

	
	public void enter(Page<K> p) {
		K min = p.st.min();
		st.put(min, p.getOffset());
	}

	
	public Page<K> next(K key) {
		K next = st.floor(key);
		long offset = st.get(next);
		return null;
	}

	
	public Page<K> split() {
		// TODO configurar offset
		long offset = 0;
		
		Page<K> page = new Page<>(m, bottom, offset);
		
		return null;
	}

	
	public boolean holds(K key) {
		return st.contains(key);
	}

	public boolean isExternal() {
		return bottom;
	}

	public boolean isOverflowed() {
		return st.size() == m;
	}

	public void close() {

	}

	public Long getOffset() {
		return offset;
	}
}
