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
import java.io.FileNotFoundException;
import java.util.Objects;

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

    //line count function
    public static int meaningfulLineCount(String str) throws FileNotFoundException, IOException {
        int count = 0;      
        try (BufferedReader reader = new BufferedReader(new FileReader(str))) {
            count = (int) reader.lines()
                    .filter(line -> isValidLine(line))
                    .count();
        }
        return count;
    }

    private static boolean isValidLine(String line) {
        if (line == null || line.trim().isEmpty() || line.trim().startsWith("#")) {
            return false;
        }
        return true;
    }

}

// Quaternion record class
record Quaternion(double a, double b, double c, double d) {
    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);

    public Quaternion {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Coefficients cannot be NaN");
        }
    }

    public Quaternion plus(Quaternion q) {
        return new Quaternion(
                this.a + q.a,
                this.b + q.b,
                this.c + q.c,
                this.d + q.d
        );
    }

    public Quaternion times(Quaternion q) {
        return new Quaternion(
                this.a * q.a - this.b * q.b - this.c * q.c - this.d * q.d,
                this.a * q.b + this.b * q.a + this.c * q.d - this.d * q.c,
                this.a * q.c - this.b * q.d + this.c * q.a + this.d * q.b,
                this.a * q.d + this.b * q.c - this.c * q.b + this.d * q.a
        );
    }

    public Quaternion conjugate() {
        return new Quaternion(this.a, -this.b, -this.c, -this.d);
    }

    public List<Double> coefficients() {
        return List.of(a, b, c, d);
    }

    @Override
    public String toString() {
        if (a == 0 && b == 0 && c == 0 && d == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        if (a != 0 || (b == 0 && c == 0 && d == 0)) {
            result.append(a);
        }
        if (b != 0) {
            if (b > 0 && result.length() > 0) result.append("+");
            result.append(b == 1 ? "" : b == -1 ? "-" : b).append("i");
        }
        if (c != 0) {
            if (c > 0 && result.length() > 0) result.append("+");
            result.append(c == 1 ? "" : c == -1 ? "-" : c).append("j");
        }
        if (d != 0) {
            if (d > 0 && result.length() > 0) result.append("+");
            result.append(d == 1 ? "" : d == -1 ? "-" : d).append("k");
        }
        return result.length() == 0 ? "0" : result.toString();
    }
}


//BinarySearchTree sealed interface and its implementations
sealed interface BinarySearchTree permits Empty, Node {
    int size();
    
    boolean contains(String value);
    
    BinarySearchTree insert(String value);
    
    String toString();
}

final class Empty implements BinarySearchTree {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(String value) {
        return false;
    }

    @Override
    public BinarySearchTree insert(String value) {
        return new Node(value, new Empty(), new Empty());
    }

    @Override
    public String toString() {
        return "()";
    }
}

final class Node implements BinarySearchTree {
    private final String value;
    private final BinarySearchTree left;
    private final BinarySearchTree right;
    private final int size;

    public Node(String value, BinarySearchTree left, BinarySearchTree right) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.size = 1 + left.size() + right.size(); 
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(String value) {
        if (this.value.equals(value)) {
            return true;
        }
        if (value.compareTo(this.value) < 0) {
            return left.contains(value);
        } else {
            return right.contains(value);
        }
    }

    @Override
    public BinarySearchTree insert(String value) {
        if (value.compareTo(this.value) < 0) {
            return new Node(this.value, left.insert(value), right); 
        } else if (value.compareTo(this.value) > 0) {
            return new Node(this.value, left, right.insert(value)); 
        } else {
            return this; 
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (!(left instanceof Empty)) {
            sb.append(left.toString());
        }
        sb.append(value);
        if (!(right instanceof Empty)) {
            sb.append(right.toString());
        }
        sb.append(")");
        return sb.toString();
    }
}