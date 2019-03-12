package com.txr.forlove.domain.excuteOrder;

/**
 * @author: T.X
 * @create: 2019-03-12 16:41
 **/
public class ChoseProcessor extends BaseProcessor{

    public ChoseProcessor() {
        super(null);
        System.out.println("ChoseProcessor 执行：构造函数:");
    }

    @Override
    public void Processor() {
        System.out.println("ChoseProcessor 执行 Processor");
    }
}
