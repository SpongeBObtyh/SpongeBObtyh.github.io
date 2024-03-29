--1.参数列表
--1.1优惠券id
local voucherId = ARGV[1]
--1.2用户id
local userId = ARGV[2]
--1.3订单id
local orderId = ARGV[3]
--2.数据key
--2.1库存key
local stockKey = 'seckill:stock:' .. voucherId
--2.1订单key
local orderKey = 'seckill:order:' .. voucherId

--3.脚本业务
--3.1判断库存是否充足 get stockKey
if (tonumber(redis.call('get', stockKey)) <= 0) then
    --库存不足，返回1
    return 1
end
--3.2判断用户是否下过单 get stockKey
if (redis.call('sismember', orderKey, userId) == 1) then
    --存在，说明是重复下单，返回2
    return 2
end
--3.3扣减库存   incrby stockKey -1
redis.call('incrby', stockKey, -1)
--3.4保存用户   sadd orderKey userId
redis.call('sadd', orderKey, userId)
--3.5发送消息到队列中
redis.call('xadd','stream.orders','*','userId',userId,'voucherId',voucherId,'id',orderId)
return 0
