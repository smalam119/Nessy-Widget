package com.thyme.smalam119.nessy.Cons;

import com.thyme.smalam119.nessy.Model.Quote;
import com.thyme.smalam119.nessy.R;

import java.util.ArrayList;
import static com.thyme.smalam119.nessy.Cons.Cons.randInt;

/**
 * Created by smalam119 on 1/11/18.
 */

public class LocalQuotesProvider {
    private ArrayList<Quote> localQuotesList;

    public LocalQuotesProvider() {
        localQuotesList = new ArrayList<>();
        addAll();
    }

    private void addAll() {
        localQuotesList.add(new Quote("Nothing is impossible, the word itself says “I’m possible”!", "Audrey Hepburn"));
        localQuotesList.add(new Quote("Whether you think you can or you think you can’t, you’re right.", "Henry Ford"));
        localQuotesList.add(new Quote("Life is 10% what happens to me and 90% of how I react to it.", "Charles Swindoll"));
        localQuotesList.add(new Quote("Believe you can and you’re halfway there", "Theodore Roosevelt."));
        localQuotesList.add(new Quote("Do or do not. There is no try", "Yoda"));
        localQuotesList.add(new Quote("The most common way people give up their power is by thinking they don’t have any.",
                "Alice Walker"));
        localQuotesList.add(new Quote("The most difficult thing is the decision to act, the rest is merely tenacity.",
                "Amelia Earhart"));
        localQuotesList.add(new Quote("The only way to do great work is to love what you do.", "Steve Jobs"));
        localQuotesList.add(new Quote("Change your thoughts and you change your world.", "Norman Vincent Peale"));
        localQuotesList.add(new Quote("You can’t use up creativity. The more you use, the more you have.", "Maya Angelou"));
        localQuotesList.add(new Quote("Certain things catch your eye, but pursue only those that capture the heart","Indian Proverb"));
        localQuotesList.add(new Quote("Everything has beauty, but not everyone can see.","Confucius"));
        localQuotesList.add(new Quote("We can’t help everyone, but everyone can help someone.","Ronald Reagan"));
        localQuotesList.add(new Quote("Nothing will work unless you do.","Maya Angelou"));
        localQuotesList.add(new Quote("I alone cannot change the world, but I can cast a stone across the water to create many ripples. ","Mother Teresa"));
        localQuotesList.add(new Quote("What we achieve inwardly will change outer reality.","Plutarch"));
        localQuotesList.add(new Quote("I would rather die of passion than of boredom.","Vincent van Gogh"));
        localQuotesList.add(new Quote("Do not take life too seriously. You will never get out of it alive.","Elbert Hubbard"));
        localQuotesList.add(new Quote("Behind every great man is a woman rolling her eyes.","Jim Carrey"));
        localQuotesList.add(new Quote("Life is hard. After all, it kills you.","Steve Martins"));
        localQuotesList.add(new Quote("I love you the more in that I believe you had liked me for my own sake and for nothing else.","John Keats"));
        localQuotesList.add(new Quote("When you reach the end of your rope, tie a knot in it and hang on.","Franklin D. Roosevelt"));
        localQuotesList.add(new Quote("There is nothing permanent except change.","Heraclitus"));
        localQuotesList.add(new Quote("The only journey is the one within.","Rainer Maria Rilke"));
        localQuotesList.add(new Quote("Happiness can exist only in acceptance.","George Orwell"));
        localQuotesList.add(new Quote("The journey of a thousand miles begins with one step.","Lao Tzu"));
        localQuotesList.add(new Quote("The best preparation for tomorrow is doing your best today.","H. Jackson Brown, Jr."));
        localQuotesList.add(new Quote("If we lose this war, I'll just start another in my wife's name.","Moshe Dayan"));
        localQuotesList.add(new Quote("If your head is wax, don't walk in the sun.","Benjamin Franklin"));
        localQuotesList.add(new Quote("Arguments are to be avoided: they are always vulgar and often convincing.","Oscar Wilde"));
    }

    public Quote getRandomQuote() {
        int randomNumber = randInt(1,30);
        return localQuotesList.get(randomNumber);
    }

    public int getRandomImage() {
        int[] images = {R.drawable.city,R.drawable.city_1,R.drawable.city_2,R.drawable.city_3};
        int randomNumber = randInt(1,4);
        return images[randomNumber];
    }
}
