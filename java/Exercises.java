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

    //firtThenLowerCase function
    public static Optional <String> firstThenLowerCase(List<String> strList, Function<String, Boolean> func){
        return strList.stream() 
            .filter(str -> func.apply(str))
            .findFirst()
            .map(String::toLowerCase);
    }

    //say function
    public static Say say() {
        return new Say("");
    }

    public static Say say(String str) {
        return new Say(str);
    }

    public static class Say {
        private final String phrase;

        public Say(String str) {
            this.phrase = (str == null) ? "" : str;
        }

        public Say and(String str) {
            if (str == null || str.isEmpty()) {
                return new Say(this.phrase + " "); 
            } else {
                return new Say(this.phrase + " " + str);  
            }
        }

        public String phrase() {
            return this.phrase;
        }
    }

    // Write your line count function here
}

// Write your Quaternion record class here

// Write your BinarySearchTree sealed interface and its implementations here
