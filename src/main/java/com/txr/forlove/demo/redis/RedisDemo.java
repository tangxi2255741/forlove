package com.txr.forlove.demo.redis;

public class RedisDemo {

    public static void main(String[] args) {
        // 按时间存储缓存
//        double scores = ZUtils.scoreCompute(sendDate);
//        subBatchMap.put(JSON.toJSONString(coupAdvanceMsgBo), scores);
//        redisUtil.zadd(CacheKey.advanceMsgTimeToSend(), subBatchMap);

//        List<String> subBatchIds = redisUtil.zRangeByScore(CacheKey.advanceMsgTimeToSend(), 0, ZUtils.scoreCompute(dateNow), 0, DEAL_TASK_SIZE);
//        redisUtil.zrems(CacheKey.advanceMsgTimeToSend(),subBatchIds.toArray(new String[subBatchIds.size()]));


        // pipeline lpush
//        List<String> list
//        Pipeline pipeline = jedis.pipelined();
//        String[] listArr = list.toArray(new String[list.size()]);
//        pipeline.lpush(key,listArr);
    }
}
