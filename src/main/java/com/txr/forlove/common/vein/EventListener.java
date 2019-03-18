/**
 * Copyright(C) 2004-2017 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein;


import com.txr.forlove.common.vein.domain.Context;
import com.txr.forlove.common.vein.domain.Event;

/**
 * <p> 数据导出中的相关事件的监听 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2017-01-24 14:36
 */
public interface EventListener {

    void handle(Event event, Context context, Object obj);
}
