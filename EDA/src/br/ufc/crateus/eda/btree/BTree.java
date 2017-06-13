package br.ufc.crateus.eda.btree;

public class BTree<K extends Comparable<K>> {
	// TODO 
	private Page<K> root;
	private String pageFile;
	private String dataFile;
	private int keySize;
	private int dataSize;
	private int m;
	
	public BTree(int m, String pageFile, String dataFile, int keySize, int dataSize) {
		this.m = m;
		this.pageFile = pageFile;
		this.dataFile = dataFile;
		this.keySize = keySize;
		this.dataSize = dataSize;
	}
	
	public boolean contains(K key) {
		return contains(root, key);
	}
	
	private boolean contains(Page<K> r, K key) {
		if (r.isExternal()) return r.holds(key);
		return contains(r.next(key), key);
	}
	
	public void insert(K key) {
		insert(root, key);
		if (root.isOverflowed()) {
			//  TODO offset
			Page<K> newRoot = new Page<>(m, false, 0);  
			Page<K> tmp = root.split();
			newRoot.enter(root);
			newRoot.enter(tmp);
			root = newRoot;
		}
	}
	
	private void insert(Page<K> r, K key) {
				
		if (r.isExternal()) {
			// TODO offset
			long offset = 0;
			r.insert(key, offset);
		}
		else {
			Page<K> next = r.next(key);
			insert(next, key);
			if (next.isOverflowed()) {
				Page<K> tmp = next.split();
				r.enter(tmp);
			}
		}
	}
	
	static class Pessoa {
		String nome;
		Pessoa(String nome) {
			this.nome = nome;
		}
	}
}

















