package com.example.hiragana_homepage;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Randomiser {
    ArrayList fourNumbers = new ArrayList();

    public ArrayList getFourNumbers() {
        return fourNumbers;
    }

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



    public Hiragana_character generateKeyChar(ArrayList a) {
        Random rand = new Random();
        ArrayList finalFour = a;

        int randomIndex = rand.nextInt(finalFour.size());
        Hiragana_character keyChar = (Hiragana_character) finalFour.get(randomIndex);
        return keyChar;
    }

    public Hiragana_character getRandomHiragana(ArrayList a) {
        Random rand = new Random();
        return (Hiragana_character) a.get(rand.nextInt(a.size()));
    }

    public Integer generateRandomNumber(Integer i){
        Random rand = new Random();
        Integer rand_num = rand.nextInt(i);
        return rand_num;
    }

}

