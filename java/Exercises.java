import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.Arrays;
import java.util.stream.Stream; 

public class Exercises {
    static Map<Integer, Long> change(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        var counts = new HashMap<Integer, Long>();
        for (var denomination : List.of(25, 10, 5, 1)) {
            counts.put(denomination, amount / denomination);
            amount %= denomination;
        }
        return counts;
    }

    public static Optional <String> firstThenLowerCase(List<String> strList, Function<String, Boolean> func){
        return strList.stream() 
            .filter(str -> func.apply(str))
            .findFirst()
            .map(String::toLowerCase);
    }

     public interface Chainable {
        Chainable and(String next);
        String phrase();
    }

    public static Chainable say() {
        return say("");
    }

    public static Chainable say(String str) {
        return new Chainable() {
            private StringBuilder message = new StringBuilder(str != null ? str : "");

            @Override
            public Chainable and(String next) {
                if (next != null) {
                    if (message.length() > 0) {
                        message.append(" ");
                    }
                    message.append(next);
                }
                return this;
            }

            @Override
            public String phrase() {
                return message.toString();
            }
        };
    }
    
    // Write your line count function here
}

// Write your Quaternion record class here

// Write your BinarySearchTree sealed interface and its implementations here
