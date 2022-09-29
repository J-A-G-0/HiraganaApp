package com.example.hiragana_homepage;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class for methods involving random generation of HiraganaCharacter objects.
 *
 * @author joelgodfrey
 */
public class Randomiser {
    private final ArrayList fourNumbers = new ArrayList();

    public ArrayList getFourNumbers() {
        return fourNumbers;
    }

    /**
     * Return four random characters from provided ArrayList.
     * @param a
     * @return four HiraganaCharacter objects.
     */
    public ArrayList generateNumbers(ArrayList a) {
        Random rand = new Random();
        int numberOfElements = 4;
        ArrayList<Hiragana_character> all_chars = new ArrayList<>();
        all_chars.addAll(a);

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(all_chars.size());
            Hiragana_character randomElement = all_chars.get(randomIndex);
            fourNumbers.add(randomElement);
            all_chars.remove(randomIndex);
        }
        return getFourNumbers();
    }

    /**
     * Randomly select character from given arraylist to be the 'keychar' for lessons.
     * @param a
     * @return
     */
    public Hiragana_character generateKeyChar(ArrayList a) {
        Random rand = new Random();
        ArrayList finalFour = a;

        int randomIndex = rand.nextInt(finalFour.size());
        Hiragana_character keyChar = (Hiragana_character) finalFour.get(randomIndex);
        return keyChar;
    }

    /**
     * Get a random HiraganaCharacter object from an ArrayList.
     * @param a
     * @return
     */
    public Hiragana_character getRandomHiragana(ArrayList a) {
        Random rand = new Random();
        return (Hiragana_character) a.get(rand.nextInt(a.size()));
    }

}

