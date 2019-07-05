package com.txr.forlove.demo.collection;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForEachDemo {
    private static final int BATCH_QUERY_SIZE = 100;

    /** 分页获取list所有数据 */
    public static Map<Long, String> getAllByIds(List<Long> list){
        Assert.notEmpty(list,"list不能为空");
        Map<Long, String> result = new HashMap<>();
        int fromIndex = 0;
        while (true){
            fromIndex = fromIndex > list.size() ? list.size() : fromIndex;
            int endIndex = fromIndex + BATCH_QUERY_SIZE > list.size() ? list.size() : fromIndex + BATCH_QUERY_SIZE;
            List<Long> subList = new ArrayList<>(list.subList(fromIndex,endIndex));
            if(CollectionUtils.isEmpty(subList)){
                break;
            }
            Map<Long, String> subResult = batchQueryData(subList);
            result.putAll(subResult);
            fromIndex += BATCH_QUERY_SIZE;
        }
        return result;
    }

    private static Map<Long, String> batchQueryData(List<Long> list){
        Map<Long, String> result = new HashMap<>();
        for(Long id : list){
            result.put(id,"value_" + id);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        for(int i=0;i<56;i++){
            list.add((long) i);
        }
        Map<Long, String> result = getAllByIds(list);
        System.out.println("result.size = " + result.size());
//        System.out.println(JSON.toJSONString(result));
    }

}
