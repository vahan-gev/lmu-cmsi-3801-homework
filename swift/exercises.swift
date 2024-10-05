import Foundation

struct NegativeAmountError: Error {}
struct NoSuchFileError: Error {}

func change(_ amount: Int) -> Result<[Int:Int], NegativeAmountError> {
    if amount < 0 {
        return .failure(NegativeAmountError())
    }
    var (counts, remaining) = ([Int:Int](), amount)
    for denomination in [25, 10, 5, 1] {
        (counts[denomination], remaining) = 
            remaining.quotientAndRemainder(dividingBy: denomination)
    }
    return .success(counts)
}

// Write your first then lower case function here
func firstThenLowerCase(of strings: [String], satisfying predicate: (String) -> Bool) -> String? {
    for string: String in strings {
        if predicate(string) {
            return string.lowercased()
        }
    }
    return nil
}
// Write your say function here
struct Sayer {

    let phrase: String

    func add(_ word: String) -> Sayer {
        return Sayer(phrase: self.phrase + " " + word)
    }

    func and(_ word: String) -> Sayer {
        return add(word)
    }
}

func say(_ word: String = "") -> Sayer {
    return Sayer(phrase: word)
}
// Write your meaningfulLineCount function here
func meaningfulLineCount(_ filename: String) async -> Result<Int, NoSuchFileError> {

    let url: URL = URL(fileURLWithPath: filename)
    var count: Int = 0

    do {
        for line: String in try String(contentsOf: url).components(separatedBy: .newlines) {
            
            let trimmedLine: String = line.trimmingCharacters(in: .whitespaces)
            
            if !trimmedLine.isEmpty && !trimmedLine.hasPrefix("#") {
                count += 1
            }
        }
        return .success(count)
    }
    catch {
        return .failure(NoSuchFileError())
    }

}
// Write your Quaternion struct here
struct Quaternion: Equatable, CustomStringConvertible {

    let a: Double
    let b: Double
    let c: Double
    let d: Double

    init(a: Double = 0, b: Double = 0, c: Double = 0, d: Double = 0) {
        self.a = a
        self.b = b
        self.c = c
        self.d = d
    }

    static let ZERO: Quaternion = Quaternion(a: 0.0, b: 0.0, c: 0.0, d: 0.0)
    static let I: Quaternion = Quaternion(a: 0.0, b: 1.0, c: 0.0, d: 0.0)
    static let J: Quaternion = Quaternion(a: 0.0, b: 0.0, c: 1.0, d: 0.0)
    static let K: Quaternion = Quaternion(a: 0.0, b: 0.0, c: 0.0, d: 1.0)

    var coefficients: [Double] {
        return [self.a, self.b, self.c, self.d]
    }

    var conjugate: Quaternion {
        return Quaternion(a: self.a, b: -self.b, c: -self.c, d: -self.d)
    }

    static func +(left: Quaternion, right: Quaternion) -> Quaternion {
        return Quaternion(a: left.a + right.a, b: left.b + right.b, c: left.c + right.c, d: left.d + right.d)
    }

    static func *(left: Quaternion, right: Quaternion) -> Quaternion {
        let a: Double = left.a * right.a - left.b * right.b - left.c * right.c - left.d * right.d
        let b: Double = left.a * right.b + left.b * right.a + left.c * right.d - left.d * right.c
        let c: Double = left.a * right.c - left.b * right.d + left.c * right.a + left.d * right.b
        let d: Double = left.a * right.d + left.b * right.c - left.c * right.b + left.d * right.a

        return Quaternion(a: a, b: b, c: c, d: d)
    }

    static func ==(left: Quaternion, right: Quaternion) -> Bool {
        return left.a == right.a && left.b == right.b && left.c == right.c && left.d == right.d
    }

    var description: String {
        var result: String = ""

        if self.a != 0 || (self.b != 0 && self.c == 0 && self.d == 0) {
            result += "\(self.a)"
        } 
        if self.b != 0 {
            if self.b > 0 && result != "" {
                result += "+"
            }
            if self.b == 1 {
                result += "i"
            }
            else if self.b == -1 {
                result += "-i"
            }
            else {
                result += "\(self.b)i"
            }
        }
        if self.c != 0 {
            if self.c > 0 && result != "" {
                result += "+"
            }
            if self.c == 1 {
                result += "j"
            }
            else if self.c == -1 {
                result += "-j"
            }
            else {
                result += "\(self.c)j"
            }
        }
        if self.d != 0 {
            if self.d > 0 && result != "" {
                result += "+"
            }
            if self.d == 1 {
                result += "k"
            }
            else if self.d == -1 {
                result += "-k"
            }
            else {
                result += "\(self.d)k"
            }
        }
        
        return result.isEmpty ? "0" : result
    }
}

// Write your Binary Search Tree enum here
indirect enum BinarySearchTree: CustomStringConvertible {

    case empty 
    case node(left: BinarySearchTree, value: String, right: BinarySearchTree)

    var size: Int {
        switch self {
            case .empty: return 0
            case .node(let left, _, let right):
                return 1 + left.size + right.size
        }
    }

    func insert(_ newValue: String) -> BinarySearchTree {

        switch self {
            case .empty:
                return .node(left: .empty, value: newValue, right: .empty)
            case .node(var left, let value, var right):
                switch newValue {
                    case _ where newValue < value:
                        left = left.insert(newValue)
                    case _ where newValue > value:
                        right = right.insert(newValue)
                    default: 
                        return self
                }
                return .node(left: left, value: value, right: right)
            }
    }

    func contains(_ value: String) -> Bool {

        switch self {
            case .empty:
                return false
            case .node(let left, let nodeValue, let right):
                if value == nodeValue {
                    return true
                }
                else if value < nodeValue {
                    return left.contains(value)
                }
                else {
                    return right.contains(value)
                }
        }
    }

    var description: String {
        switch self {
            case .empty:
                return "()"
            case .node(let left, let value, let right):
                let leftString: String = left.description
                let rightString: String = right.description
                if leftString == "()" && rightString == "()" {
                    return "(\(value))"
                }
                return "(\(leftString)\(value)\(rightString))"
        }
    }

}