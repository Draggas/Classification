package fr.univlille.iut.sae302.utils;

public class ConnectableProperty extends ObservableProperty implements Observer {

	protected boolean propagating;

	public ConnectableProperty() {
		propagating = false;
	}

	public void connectTo(ConnectableProperty other) {
		other.attach(this);
	}

	public void biconnectTo(ConnectableProperty other) {
		connectTo(other);
		update(other, other.getValue());
		other.connectTo(this);
	}

	public void unconnectFrom(ConnectableProperty other) {
		other.detach(this);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object val) {
		if (! propagating) {
			propagating = true;
			super.setValue(val);
			propagating = false;
		}
	}

	@Override
	public void update(Observable observable) {
		// jamais appelé
	}

	@Override
	public void update(Observable other, Object data) {
		setValue(data);
	}

}
