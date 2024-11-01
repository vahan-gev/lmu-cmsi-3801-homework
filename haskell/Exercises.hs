module Exercises
    ( firstThenApply
    , lower
    , lengthOverThree
    , change
    , powers
    , meaningfulLineCount
    , Shape(..)
    , volume
    , surfaceArea
    , BST(..)
    , insert
    , contains
    , size
    , inorder
    ) where

import qualified Data.Map as Map
import Data.Text (pack, unpack)
import Data.List (find, isPrefixOf)
import Data.Char (toLower, isSpace)
import System.IO

change :: Integer -> Either String (Map.Map Integer Integer)
change amount
    | amount < 0 = Left "amount cannot be negative"
    | otherwise = Right $ changeHelper [25, 10, 5, 1] amount Map.empty
  where
    changeHelper [] remaining counts = counts
    changeHelper (d:ds) remaining counts =
        changeHelper ds newRemaining newCounts
      where
        (count, newRemaining) = remaining `divMod` d
        newCounts = Map.insert d count counts

firstThenApply :: [a] -> (a -> Bool) -> (a -> b) -> Maybe b
firstThenApply xs p f = f <$> find p xs

lower :: String -> String
lower = map toLower

lengthOverThree :: String -> Bool
lengthOverThree s = length s > 3

powers :: Integral a => a -> [a]
powers b = (^ 0) b : map (b *) (powers b)

meaningfulLineCount :: FilePath -> IO Int
meaningfulLineCount path = do
    contents <- readFile path
    return $ length $ filter isNonEmpty $ lines contents
  where
    isNonEmpty line = not (null trimmed || all isSpace line || "#" `isPrefixOf` trimmed)
      where
        trimmed = dropWhile isSpace line

data Shape = Sphere Double 
          | Box Double Double Double
    deriving (Show, Eq)

volume :: Shape -> Double
volume (Sphere r) = (4/3) * pi * r^3
volume (Box l w h) = l * w * h

surfaceArea :: Shape -> Double
surfaceArea (Sphere r) = 4 * pi * r^2
surfaceArea (Box l w h) = 2 * (l*w + l*h + w*h)

data BST a = Empty 
           | Node (BST a) a (BST a)
    deriving (Eq)

instance (Show a) => Show (BST a) where
    show Empty = "()"
    show (Node left val right) = "(" ++ showLeft ++ show val ++ showRight ++ ")"
      where
        showLeft = case left of
            Empty -> ""
            _ -> show left
        showRight = case right of
            Empty -> ""
            _ -> show right

insert :: Ord a => a -> BST a -> BST a
insert x Empty = Node Empty x Empty
insert x (Node left val right)
    | x < val = Node (insert x left) val right
    | x > val = Node left val (insert x right)
    | otherwise = Node left val right

contains :: Ord a => a -> BST a -> Bool
contains _ Empty = False
contains x (Node left val right)
    | x == val = True
    | x < val = contains x left
    | otherwise = contains x right

size :: BST a -> Int
size Empty = 0
size (Node left _ right) = 1 + size left + size right

inorder :: BST a -> [a]
inorder Empty = []
inorder (Node left val right) = inorder left ++ [val] ++ inorder right