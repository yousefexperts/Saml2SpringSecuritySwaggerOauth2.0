package com.experts.core.biller.statemachine.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicReference;


public final class Calculator {

    private final AtomicReference<BigDecimal> value = new AtomicReference<>();

    private int scale = 0;

    private RoundingMode mode = RoundingMode.DOWN;

    private boolean roundingAlways = false;

    private int defaultScale = 18;

    private Calculator(Number v) {
        try {
            this.value.set(new BigDecimal(v.toString()));
        } catch (NumberFormatException e) {
            this.value.set(BigDecimal.ZERO);
        }
    }

    private Calculator(BigDecimal v) {
        this.value.set(v);
    }

    public Calculator scale(int scale) {
        return scale(scale, RoundingMode.DOWN);
    }

    public Calculator scale(int scale, RoundingMode mode) {
        this.scale = scale;
        this.mode = mode;
        return this;
    }

    public Calculator roundingAlways(boolean roundingAlways) {
        this.roundingAlways = roundingAlways;
        return this;
    }


    public Calculator add(Number v) {
        try {
            add(new BigDecimal(v.toString()));
        } catch (NumberFormatException e) {
        }
        return this;
    }

    public Calculator add(BigDecimal v) {
        value.set(rounding(value.get().add(v)));
        return this;
    }

    private BigDecimal rounding(BigDecimal v) {
        return roundingAlways ? v.setScale(scale, mode) : v;
    }


    public Calculator subtract(Number v) {
        try {
            subtract(new BigDecimal(v.toString()));
        } catch (NumberFormatException e) {
        }
        return this;
    }


    public Calculator subtract(BigDecimal v) {
        value.set(rounding(value.get().subtract(v)));
        return this;
    }


    public Calculator multiply(Number v) {
        try {
            multiply(new BigDecimal(v.toString()));
        } catch (NumberFormatException e) {
        }
        return this;
    }


    public Calculator multiply(BigDecimal v) {
        value.set(rounding(value.get().multiply(v)));
        return this;
    }


    public Calculator divideBy(Number v) {
        try {
            divideBy(new BigDecimal(v.toString()));
        } catch (NumberFormatException e) {
        }
        return this;
    }


    public Calculator divideBy(BigDecimal v) {
        BigDecimal ret = roundingAlways ? value.get().divide(v, scale, mode)
                : value.get().divide(v, defaultScale, mode);
        value.set(ret);
        return this;
    }


    public int intValue() {
        return decimal().intValue();
    }


    public long longValue() {
        return decimal().longValue();
    }


    public BigDecimal decimal() {
        BigDecimal v = value.get();
        return v != null ? v.setScale(scale, mode) : BigDecimal.ZERO;
    }


    public static Calculator init() {
        return new Calculator(BigDecimal.ZERO);
    }


    public static Calculator of(Number v) {
        return new Calculator(v);
    }


    public static Calculator of(BigDecimal v) {
        return new Calculator(v);
    }

}
