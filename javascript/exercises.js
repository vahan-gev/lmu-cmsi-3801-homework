import fs from "fs"

export function change(amount) {
  if (!Number.isInteger(amount)) {
    throw new TypeError("Amount must be an integer")
  }
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let [counts, remaining] = [{}, amount]
  for (const denomination of [25, 10, 5, 1]) {
    counts[denomination] = Math.floor(remaining / denomination)
    remaining %= denomination
  }
  return counts
}

// Write your first then lower case function here

export function firstThenLowerCase(arr, predicate) {
  for (let i in arr) {
    if (predicate(arr[i])) {
      return arr[i].toLowerCase()
    }
  }
  return undefined
}

// Write your powers generator here

export function* powersGenerator({ ofBase, upTo }) {
  let exp = 0
  while (Math.pow(ofBase, exp) <= upTo) {
    let curr = Math.pow(ofBase, exp)
    yield curr
    exp++
  }
}

// Write your say function here

export function say(str) {
  let message = str
  if (str === undefined) {
    return ""
  }
  function newStr(next) {
    if (next === undefined) {
      return message
    }
    message += ` ${next}`
    return newStr
  }
  return newStr
}

// Write your line count function here

export async function meaningfulLineCount(str) {
  let count = 0

  const lines = fs.readFileSync(str, "utf-8").split(/\r?\n/)
  function isValidLine(line) {
    if (line === "" || line.trim() === "" || line.trim()[0] === "#") {
      return false
    }
    return true
  }
  for (let i in lines) {
    if (isValidLine(lines[i])) {
      count++
    }
  }
  return count
}

// Write your Quaternion class here

export class Quaternion {
  constructor(a, b, c, d) {
    this.a = a
    this.b = b
    this.c = c
    this.d = d
    Object.freeze(this)
  }
  //adding
  plus(quat) {
    return new Quaternion(
      this.a + quat.a,
      this.b + quat.b,
      this.c + quat.c,
      this.d + quat.d
    )
  }
  //multiplying
  times(quat) {
    return new Quaternion(
      this.a * quat.a - this.b * quat.b - this.c * quat.c - this.d * quat.d,
      this.a * quat.b + this.b * quat.a + this.c * quat.d - this.d * quat.c,
      this.a * quat.c - this.b * quat.d + this.c * quat.a + this.d * quat.b,
      this.a * quat.d + this.b * quat.c - this.c * quat.b + this.d * quat.a
    )
  }
  //conjugate
  get conjugate() {
    return new Quaternion(this.a, -this.b, -this.c, -this.d)
  }
  //coefficients
  get coefficients() {
    console.log([this.a, this.b, this.c, this.d])
    return [this.a, this.b, this.c, this.d]
  }
  //equal
  equals(quat) {
    return (
      this.a === quat.a &&
      this.b === quat.b &&
      this.c === quat.c &&
      this.d === quat.d
    )
  }
  //string representation
  toString() {
    let result = ""
    if (this.a !== 0 || (this.b === 0 && this.c === 0 && this.d === 0)) {
      result += this.a
    }
    if (this.b !== 0) {
      result += this.b > 0 && result !== "" ? "+" : ""
      result += (this.b === 1 ? "" : this.b === -1 ? "-" : this.b) + "i"
    }
    if (this.c !== 0) {
      result += this.c > 0 && result !== "" ? "+" : ""
      result += (this.c === 1 ? "" : this.c === -1 ? "-" : this.c) + "j"
    }
    if (this.d !== 0) {
      result += this.d > 0 && result !== "" ? "+" : ""
      result += (this.d === 1 ? "" : this.d === -1 ? "-" : this.d) + "k"
    }
    return result || "0"
  }
}
