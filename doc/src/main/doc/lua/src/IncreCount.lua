-- 对目标字段计数+1
function increCount(countList, target)
	if not countList then countList='' end
	local targetPerf = target..";"
	local index = 1
	local result
	for word in string.gmatch(countList, "%w+;%d+;")
	do
		--print(word.."   index:"..index)
		local x,y=string.find(word,targetPerf,1)
		if (x == 1)
			then
				local count=string.match(word, "%d+",y+1)+1
				result = string.sub(countList, 1, index-1)..target..";"..string.format("%d",count)..";"..string.sub(countList, index+string.len(word))
				break
			end
		index = index + string.len(word)
	end

	if (not result)
		then
			result = countList..target..";1;"
		end
	--print("result:"..result)
	return result
end


-- 对目标字段计数+1。有限制，比如对于业务111和11，会把11计到111头上。
function increCount2(countList, target)
	local result
	local index = string.find(countList, target..";", 1);

	if (index)
	  then
		countIndex = index+string.len(target)+1
		perfix = string.sub(countList, 1, index+string.len(target))
		--print("perfix:"..perfix)
		surfix = string.sub(countList, countIndex)
		--print("surfix:"..surfix)
		count=string.match(surfix, "%d+")
		--print("count:"..count)
		surfix = string.gsub(surfix,count,string.format("%d",count+1),1)
		--print("surfix:"..surfix)
		result = perfix..surfix
	  else
		--print("not find")
		result = countList..target..";1;"
	  end

	return result

end


print(increCount("110000;1;120000;1;130000;1;",110000))
print(increCount("110000;1;120000;1;130000;1;",120000))
print(increCount("110000;1;120000;1;130000;1;",130000))
print(increCount("110000;1;120000;1;130000;1;",140000))
print(increCount("110000;1;120000;1;130000;1;",1000))
print(increCount("110000;1;120000;1;130000;1;",20000))
print(increCount("110000;1;120000;1;130000;1;",1))

print(increCount("a1;1;b2;1;130000;1;","a1"))
print(increCount("a1;1;b2;1;130000;1;","b2"))
print(increCount("a1;1;b2;1;130000;1;","c3"))
print(increCount("","c3"))
print(increCount(nil,"c4"))

