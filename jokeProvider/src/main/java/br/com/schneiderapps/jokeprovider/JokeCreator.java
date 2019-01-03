package br.com.schneiderapps.jokeprovider;

import java.util.ArrayList;
import java.util.Random;

public class JokeCreator {

    private ArrayList<String> jokesList = new ArrayList<>();
    private Random random;

    public JokeCreator(){
        random = new Random();
        populateJokesList();
    }

    private void populateJokesList() {
        jokesList.add("There are three kinds of lies: Lies, damned lies, and benchmarks.");

        jokesList.add("Q: How many prolog programmers does it take to change a light bulb?\n" +
                "A: Yes.");

        jokesList.add("I've got a really good UDP joke to tell you, but I don't know if you'll get it. ");
        jokesList.add("Two threads walk into a bar. The barkeeper looks up and yells, \"hey, I don't want any conditions race like the time last!\"");
        jokesList.add("3 Database Admins walked into a NoSQL bar. A little later, they walked out because they couldn't find a table.");
        jokesList.add("The C language combines all the power of assembly language with all the ease-of-use of assembly language.");
        jokesList.add("8 bytes walk into a bar, the bartenders asks \"What will it be?\" One of them says, \"Make us a double.\"");
        jokesList.add("An SEO couple had twins. For the first time, they were happy with duplicate content.");
        jokesList.add("There are two ways to write error-free programs; only the third one works. ");
        jokesList.add("In order to understand recursion, you must first understand recursion.");
    }

    public String getJoke(){
        int position = random.nextInt(jokesList.size()-1);
        return jokesList.get(position);
    }
}
