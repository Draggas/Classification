package fr.univlille.iut.r304.utils;


public class ObservableProperty extends Observable {

	protected Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object val) {
		value = val;
		notifyObservers(val);
	}
}
