import { createInterface } from 'readline';
import { createReadStream } from 'fs';

export function change(amount: bigint): Map<bigint, bigint> {
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let counts: Map<bigint, bigint> = new Map()
  let remaining = amount
  for (const denomination of [25n, 10n, 5n, 1n]) {
    counts.set(denomination, remaining / denomination)
    remaining %= denomination
  }
  return counts
}

export function firstThenApply<T, U>(
  a: Array<T>, 
  p: (element: T) => boolean, 
  f: (element: T) => U
): U | undefined {
  for (const element of a) {
    if (p(element)) {
      return f(element);
    }
  }
  return undefined;
}

export function* powersGenerator(base: bigint): Generator<bigint> {
  let power = 1n;
  while (true) {
    yield power;
    power *= base;
  }
}

export async function meaningfulLineCount(filePath: string): Promise<number> {
  try {
    let validLines = 0;
    const lineReader = createInterface({
      input: createReadStream(filePath),
      crlfDelay: Infinity
    });

    for await (const line of lineReader) {
      const trimmedLine = line.trim();
      if (trimmedLine && !trimmedLine.startsWith('#')) {
        validLines++;
      }
    }

    return validLines;
  } catch (error) {
    console.error(`Error reading file: ${filePath}`, error);
    throw error;
  }
}

type Box = {
  readonly kind: 'Box';
  readonly width: number;
  readonly length: number;
  readonly depth: number;
};

type Sphere = {
  readonly kind: 'Sphere';
  readonly radius: number;
};

export type Shape = Box | Sphere;

export function surfaceArea(shape: Shape): number {
  switch (shape.kind) {
    case 'Box':
      return 2 * (
        shape.width * shape.length +
        shape.width * shape.depth +
        shape.length * shape.depth
      );
    case 'Sphere':
      return 4 * Math.PI * shape.radius ** 2;
  }
}

export function volume(shape: Shape): number {
  switch (shape.kind) {
    case 'Box':
      return shape.width * shape.length * shape.depth;
    case 'Sphere':
      return (4 / 3) * Math.PI * shape.radius ** 3;
  }
}

export abstract class BinarySearchTree<T extends Comparable> {
  abstract size(): number;
  abstract contains(value: T): boolean;
  abstract insert(value: T): BinarySearchTree<T>;
  abstract inorder(): Generator<T>;
  abstract toString(): string;
}

export class Empty<T extends Comparable> extends BinarySearchTree<T> {
  size(): number {
    return 0;
  }

  contains(_value: T): boolean {
    return false;
  }

  insert(value: T): BinarySearchTree<T> {
    return new Node(value, new Empty(), new Empty());
  }

  *inorder(): Generator<T> {}

  toString(): string {
    return "()";
  }
}

class Node<T extends Comparable> extends BinarySearchTree<T> {
  constructor(
    private value: T,
    private left: BinarySearchTree<T>,
    private right: BinarySearchTree<T>
  ) {
    super();
  }

  size(): number {
    return 1 + this.left.size() + this.right.size();
  }

  contains(value: T): boolean {
    if (value < this.value) return this.left.contains(value);
    if (value > this.value) return this.right.contains(value);
    return true;
  }

  insert(value: T): BinarySearchTree<T> {
    if (value < this.value) {
      return new Node(this.value, this.left.insert(value), this.right);
    }
    if (value > this.value) {
      return new Node(this.value, this.left, this.right.insert(value));
    }
    return this;
  }

  *inorder(): Generator<T> {
    yield* this.left.inorder();
    yield this.value;
    yield* this.right.inorder();
  }

  toString(): string {
    const leftStr = this.left instanceof Empty ? "" : this.left.toString();
    const rightStr = this.right instanceof Empty ? "" : this.right.toString();
    return `(${leftStr}${this.value}${rightStr})`;
  }
}

type Comparable = string | number | boolean;