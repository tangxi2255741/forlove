package com.txr.forlove.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 
 * @author yanglei, cdyanglei5@jd.com
 * @version 1.0.1, 2018年8月24日
 * @since 2018年8月24日
 * 
 */
public class DefaultStepSelector implements StepSelector {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<ExceptionStep> configStep;
    private final List<ExceptionStep> skipStep;

    private int nextStep = 0;

    public DefaultStepSelector(Set<ExceptionStep> configStep, Set<ExceptionStep> skipStep) {
        super();
        this.configStep = configStep == null ? Collections.<ExceptionStep> emptyList() : new ArrayList<ExceptionStep>(configStep);
        this.skipStep = skipStep == null ? Collections.<ExceptionStep> emptyList() : new ArrayList<ExceptionStep>(skipStep);

        Collections.sort(this.configStep, new ExceptionStepComparator());

        if (logger.isDebugEnabled()) {
            logger.debug("ConfigStep: {}", this.configStep);
            logger.debug("SkipStep: {}", this.skipStep);
        }
    }

    @Override
    public ExceptionStep next() {

        ExceptionStep step = null;
        while (nextStep < configStep.size()) {
            if ((step = configStep.get(nextStep++)) != null && (!step.isSkipEnable() || !skipStep.contains(step))) {
                return step;
            }
        }

        return null;
    }

    static class ExceptionStepComparator implements Comparator<ExceptionStep> {

        @Override
        public int compare(ExceptionStep o1, ExceptionStep o2) {
            return o1.ordinal() - o2.ordinal();
        }

    }
}
