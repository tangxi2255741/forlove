package com.txr.forlove.domain.excuteOrder;

/**
 * @author: T.X
 * @create: 2019-03-12 16:40
 **/
public class PullProcessor extends BaseProcessor {

    public PullProcessor(MyProcessor myProcessor) {
        super(myProcessor);
        System.out.println("PullProcessor 执行：构造函数:" + myProcessor);
    }

    @Override
    public void Processor() {
        System.out.println("PullProcessor 执行 Processor 开始");
        super.Processor();
        System.out.println("PullProcessor 执行 Processor 结束");
    }
}
