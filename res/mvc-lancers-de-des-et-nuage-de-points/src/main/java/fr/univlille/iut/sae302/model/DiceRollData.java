package fr.univlille.iut.sae302.model;

import fr.univlille.iut.r304.utils.Observable;
import java.util.Random;

public class DiceRollData extends Observable {
    private int[] frequency = new int[11]; // Fréquences pour les faces de 0 à 10
    private Random random = new Random();

    public int rollDice() {
        int result = random.nextInt(11); // Lancer un dé avec des valeurs de 0 à 10
        frequency[result]++;
        notifyObservers(); // Notifiez les observateurs
        return result;
    }

    public int rollDiceN(int n) {
        for (int i = 0; i <= n; i++) {
            int result = random.nextInt(11); // Lancer un dé avec des valeurs de 0 à 10
            frequency[result]++;
        }

        notifyObservers(); // Notifiez les observateurs
        return frequency[frequency.length-1];
    }

    public int[] getFrequency() {
        return frequency;
    }
}
