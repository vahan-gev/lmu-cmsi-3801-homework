from dataclasses import dataclass
from collections.abc import Callable
from typing import Optional, Generator, Union

def change(amount: int) -> dict[int, int]:
    if not isinstance(amount, int):
        raise TypeError('Amount must be an integer')
    if amount < 0:
        raise ValueError('Amount cannot be negative')
    counts, remaining = {}, amount
    for denomination in (25, 10, 5, 1):
        counts[denomination], remaining = divmod(remaining, denomination)
    return counts


# Write your first then lower case function here
def first_then_lower_case(a: list[str], p: Callable[[str], bool]) -> Optional[str]:
    for i in a:
        if p(i):
            return i.lower()
    return None

# Write your powers generator here
def powers_generator(base:int, limit:int) -> Generator[int, None, None]:
    index = 0;
    while base**index <= limit:
        yield (base**index)
        index += 1


def say(text: str=None) -> callable:
    if text is None:
        return ""

    def inner(next: str=None) -> Union[str, callable]:
        if next is None:
            return text
        return say(text + " " + next)

    return inner

# Write your line count function here
def meaningful_line_count(file_path: str) -> int:
    count = 0
    try:
        file = open(file_path, "r")
        for line in file:
            if len(line) > 0 and not line.isspace():
                for char in line:
                    if not char.isspace():
                        if char != "#":
                            count += 1
                            break
                        else:
                            break
        file.close()
    except:
        raise FileNotFoundError("No such file")
    return count

# Write your Quaternion class here
@dataclass(frozen=True)
class Quaternion:
    a: float
    b: float
    c: float
    d: float

    def __str__(self):
        result = ""
        if self.a != 0 or (self.b == 0 and self.c == 0 and self.d == 0):
            result += str(self.a)
        if self.b != 0:
            result += "+" if (self.b > 0 and result != "") else ""
            result += ("" if self.b == 1 else "-" if self.b == -1 else str(self.b)) + "i"
        if self.c != 0:
            result += "+" if (self.c > 0 and result != "") else ""
            result += ("" if self.c == 1 else "-" if self.c == -1 else str(self.c)) + "j"
        if self.d != 0:
            result += "+" if (self.d > 0 and result != "") else ""
            result += ("" if self.d == 1 else "-" if self.d == -1 else str(self.d)) + "k"
        return result or "0"

    __repr__ = __str__

    def __add__(self, other):
        if isinstance(other, Quaternion):
            return Quaternion(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
        return NotImplemented

    def __mul__(self, other):
        if isinstance(other, Quaternion):
            return Quaternion(
                self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d,
                self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c,
                self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b,
                self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a
            )
        return NotImplemented

    @property
    def conjugate(self):
        return Quaternion(self.a, -self.b, -self.c, -self.d)

    @property
    def coefficients(self):
        return (self.a, self.b, self.c, self.d)
