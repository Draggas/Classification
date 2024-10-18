package fr.univlille.iut.sae302.utils;

public interface Observer {
        void update(Observable observable);
        void update(Observable observable, Object data);
}
