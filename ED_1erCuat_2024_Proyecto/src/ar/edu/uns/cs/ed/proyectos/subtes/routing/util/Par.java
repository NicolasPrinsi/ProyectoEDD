package ar.edu.uns.cs.ed.proyectos.subtes.routing.util;

public class Par<E,F> {
	
	E first;
	F second;
	
	public Par(E first, F second) {
		this.first = first;
		this.second = second;
	}

	public E getFirst() {
		return first;
	}

	public F getSecond() {
		return second;
	}
	
	@Override
	public String toString() {
		return "( "+first+" , "+second+" )";
	}

	@Override
	public int hashCode() {
		return first.hashCode()+second.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return ((obj instanceof Par) && 
				first.equals(((Par)obj).first) &&
				second.equals(((Par)obj).second));
	}
	
	
}
