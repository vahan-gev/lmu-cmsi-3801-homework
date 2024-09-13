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
function first_then_lower_case(a, p)
    for i = 1, #a do
        if p(a[i]) then
            return a[i]:lower()
        end
    end

    return nil
end
-- Write your powers generator here
function powers_generator(base, limit)
    local index = 0
    return coroutine.create(function ()
        while base^index <= limit do 
            coroutine.yield(base^index)
            index = index + 1
        end
    end)
end
-- Write your say function here
function say(word)
    if word == nil then
        return ""
    end

    local function connect(next)
        if next == nil then
            return word
        end
        return say(word .. " " .. next)
    end

    return connect
end

-- Write your line count function here
function meaningful_line_count(file_path)
    local count = 0
    local file = io.open(file_path, 'r')
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
Quaternion = (function (class) 
    class.new = function (a, b, c, d)
        return setmetatable({a = a, b = b, c = c, d = d}, {
            __index = {
                conjugate = function(self)
                    return class.new(self.a, -self.b, -self.c, -self.d)
                end,
                coefficients = function(self)
                    return {self.a, self.b, self.c, self.d}
                end
            },
            __add = function(self, other)
                return class.new(self.a + other.a, self.b + other.b, self.c + other.c, self.d + other.d)
            end,
            __eq = function(self, other)
                return self.a == other.a and self.b == other.b and self.c == other.c and self.d == other.d
            end,
            __mul = function(self, other)
                local a = self.a * other.a - self.b * other.b - self.c * other.c - self.d * other.d
                local b = self.a * other.b + self.b * other.a + self.c * other.d - self.d * other.c
                local c = self.a * other.c - self.b * other.d + self.c * other.a + self.d * other.b
                local d = self.a * other.d + self.b * other.c - self.c * other.b + self.d * other.a
                return class.new(a, b, c, d)
            end,
            __tostring = function(self)
                result = ""
                if self.a ~= 0 or (self.b == 0 and self.c == 0 and self.d == 0) then
                    result = result .. self.a
                end
                if self.b ~= 0 then
                    if self.b > 0 and result ~= "" then
                        result = result .. "+"
                    end
                    if self.b == 1 then
                        result = result .. "i"
                    elseif self.b == -1 then
                        result = result .. "-i"
                    else 
                        result = result .. self.b .. "i"
                    end
                end
                if self.c ~= 0 then
                    if self.c > 0 and result ~= "" then
                        result = result .. "+"
                    end
                    if self.c == 1 then
                        result = result .. "j"
                    elseif self.c == -1 then
                        result = result .. "-j"
                    else 
                        result = result .. self.c .. "j"
                    end
                end
                if self.d ~= 0 then
                    if self.d > 0 and result ~= "" then
                        result = result .. "+"
                    end
                    if self.d == 1 then
                        result = result .. "k"
                    elseif self.d == -1 then
                        result = result .. "-k"
                    else 
                        result = result .. self.d .. "k"
                    end
                end
                return result
            end
        })
    end
    return class
end)({})
