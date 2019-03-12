package com.txr.forlove.domain.excuteOrder;

/**
 * @author: T.X
 * @create: 2019-03-12 16:40
 **/
public class DoProcessor {

    public static void main(String[] args) {
        new PullProcessor(new HitProcessor(new ChoseProcessor())).Processor();
    }
}
