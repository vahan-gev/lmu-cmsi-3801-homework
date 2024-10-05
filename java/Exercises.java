import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Function;
import java.io.FileNotFoundException;

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

record Quaternion(double a, double b, double c, double d) {
    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);

    public Quaternion {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)){
            throw new IllegalArgumentException("Coefficients cannot be NaN");
        }
    }

    public Quaternion plus(Quaternion other) {
        return new Quaternion(
                this.a + other.a,
                this.b + other.b,
                this.c + other.c,
                this.d + other.d
        );
    }

    public Quaternion times(Quaternion other) {
        return new Quaternion(
                this.a * other.a - this.b * other.b - this.c * other.c - this.d * other.d,
                this.a * other.b + this.b * other.a + this.c * other.d - this.d * other.c,
                this.a * other.c - this.b * other.d + this.c * other.a + this.d * other.b,
                this.a * other.d + this.b * other.c - this.c * other.b + this.d * other.a
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
        appendComponent(result, b, "i");
        appendComponent(result, c, "j");
        appendComponent(result, d, "k");
        return result.length() == 0 ? "0" : result.toString();
    }

    private void appendComponent(StringBuilder builder, double value, String symbol) {
        if (value != 0) {
            if (value > 0 && builder.length() > 0) builder.append("+");
            builder.append(value == 1 ? "" : value == -1 ? "-" : value).append(symbol);
        }
    }
}

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
    public boolean contains(String searchValue) {
        if (this.value.equals(searchValue)) {
            return true;
        }
        if (searchValue.compareTo(this.value) < 0) {
            return left.contains(searchValue);
        } else {
            return right.contains(searchValue);
        }
    }

    @Override
    public BinarySearchTree insert(String newValue) {
        if (newValue.compareTo(this.value) < 0) {
            return new Node(this.value, left.insert(newValue), right);
        } else if (newValue.compareTo(this.value) > 0) {
            return new Node(this.value, left, right.insert(newValue));
        } else {
            return this;
        }
    }

    @Override
    public String toString() {
        StringBuilder treeString = new StringBuilder();
        treeString.append("(");
        if (!(left instanceof Empty)) {
            treeString.append(left.toString());
        }
        treeString.append(value);
        if (!(right instanceof Empty)) {
            treeString.append(right.toString());
        }
        treeString.append(")");
        return treeString.toString();
    }
}