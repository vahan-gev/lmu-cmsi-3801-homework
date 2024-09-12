function change(amount)
  if math.type(amount) ~= "integer" then
    error("Amount must be an integer")
  end
  if amount < 0 then
    error("Amount cannot be negative")
  end
  local counts, remaining = {}, amount
  for _, denomination in ipairs({25, 10, 5, 1}) do
    counts[denomination] = remaining // denomination
    remaining = remaining % denomination
  end
  return counts
end

-- Write your first then lower case function here
function first_then_lower_case(array, predicate)
  for i, item in ipairs(array) do
    if predicate(item) then
      return string.lower(item)
    end
  end
end

-- Write your powers generator here
function powers_generator(base, limit)
  local power = 0
  --To create coroutine pass a function to coroutine.create--
  --Implement procedure inside coroutine.create--
  return coroutine.create(function()
    while base^power <= limit do 
      coroutine.yield(base^power)
      power = power + 1
    end
  end)
end
-- Write your say function here
function say(word)
  if word == nil then
    return ""
  end

  local function connect(text)
    if text == nil then
      return word
    end
    return say(word .. " " .. text)
  
  end
  return connect
end
-- Write your line count function here
function meaningful_line_count(file_path)
  local file = io.open(file_path, "r")
  local count = 0

  if file == nil then
    error("No such file")
  end

  for line in file:lines() do 
    if line ~= "" and not line:match("^%s*$") and not line:match("^%s*#") then
      count = count + 1
    end
  end
  file:close()
  return count
end
-- Write your Quaternion table here
--Creates an empty table--
Quaternion = {}
--quaternionmeta is a table that contains the metamethods to define how the Quaternion table will behave--
quaternionmeta = {
  __index = {
    coefficients = function(self)
      return {self.a, self.b, self.c, self.d}
    end,
    conjugate = function(self)
      return Quaternion.new(self.a, -self.b, -self.c, -self.d)
    end
  },
  __add = function(self, other)
    return Quaternion.new(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
  end,
  __mul = function(self, other)
    local a = self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d
    local b = self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c
    local c = self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b
    local d = self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a
    return Quaternion.new(a, b, c, d)
  end,
  __equals = function(self, other)
    return self.a == other.a and self.b == other.b and self.c == other.c and self.d == other.d
  end,
  --__tostring metamethod is used to convert a table to a string--
  __tostring = function(self)
    return ""
  end
     
}
--Quaternion constructor--
Quaternion.new = function(a, b, c, d)
  return setmetatable({a = a, b = b, c = c, d = d}, quaternionmeta)
end
    
  
