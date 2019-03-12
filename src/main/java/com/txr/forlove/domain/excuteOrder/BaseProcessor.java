package com.txr.forlove.domain.excuteOrder;

/**
 * @author: T.X
 * @create: 2019-03-12 16:36
 **/
public abstract class BaseProcessor implements MyProcessor{
    private MyProcessor myProcessor;

    public BaseProcessor(MyProcessor myProcessor){
        System.out.println("BaseProcessor 执行：构造函数:" + myProcessor);
        this.myProcessor = myProcessor;
    }

    @Override
    public void Processor() {
        myProcessor.Processor();
        System.out.println("BaseProcessor 执行 Processor");
    }
}
