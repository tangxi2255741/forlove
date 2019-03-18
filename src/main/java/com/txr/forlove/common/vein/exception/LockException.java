/**
 * Copyright(C) 2004-2017 JD.COM All Right Reserved
 */
package com.txr.forlove.common.vein.exception;


import com.txr.forlove.common.vein.domain.Lock;

/**
 * <p> </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2017-01-24 16:48
 */
public class LockException extends RuntimeException{
    private static final long serialVersionUID = 8932692525244083209L;
    private Lock lock;

    public LockException(String message, Lock lock) {
        super(message);
        this.lock = lock;
    }

    public Lock getLock() {
        return lock;
    }
}