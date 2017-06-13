package br.ufc.crateus.eda.st.hashing;

public class Polinomio {

	private Pn polinomio = new Pn();

	private class Pn {

		int coeficiente;
		int expoente;
		Pn next;

		public Pn(int c, int e, Pn next) {
			this.coeficiente = c;
			this.expoente = e;
			this.next = next;
		}

		public Pn() {

		}

	}

	public Pn getListaEncadeada() {
		return polinomio;
	}

	public void add(int coeficiente, int expoente) {
		Pn novo = new Pn(coeficiente, expoente, null);

		Pn ant = polinomio;
		Pn p = polinomio.next;
		while (p != null && (p.expoente > expoente)) {
			ant = p;
			p = p.next;
		}

		novo.next = ant.next;
		ant.next = novo;
	}

	private static Polinomio multiplicar(Pn p1, Pn p2) {
		Polinomio L = new Polinomio();

		for (Pn pn1 = p1.next; pn1 != null; pn1 = pn1.next)
			for (Pn pn2 = p2.next; pn2 != null; pn2 = pn2.next)
				L.add(pn1.coeficiente * pn2.coeficiente, pn1.expoente + pn2.expoente);

		return L;
	}

	public static void printP3(Pn p1, Pn p2) {

		Polinomio L = multiplicar(p1, p2);

		SeparateChainingHashingST<Integer, Integer> sch = new SeparateChainingHashingST<>(10);

		for (Pn p3 = L.getListaEncadeada().next; p3 != null; p3 = p3.next)
			sch.put(p3.expoente, p3.coeficiente);

		sch.PrintkeysValues();

	}

	public void imprimir() {
		for (Pn p = polinomio.next; p != null; p = p.next)
			System.out.print(p.coeficiente + "x^" + p.expoente + " ");
	}

	public static void main(String[] args) {
		Polinomio p1 = new Polinomio();
		Polinomio p2 = new Polinomio();

		p1.add(4, 2);
		p2.add(7, 1);
		p2.add(8, 1);

		Polinomio.printP3(p1.getListaEncadeada(), p2.getListaEncadeada());
	}

}
