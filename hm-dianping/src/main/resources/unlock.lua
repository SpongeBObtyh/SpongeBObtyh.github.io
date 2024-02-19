--判断锁中的标识与线程标识是否一致
if(redis.call('GET',KEYS[1])==ARGV[1]) then
    --一致，释放锁
    return redis.call('DEL',KEYS[1])
end
return 0
