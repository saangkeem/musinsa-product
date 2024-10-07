package com.musinsa.shop.util;

import java.text.DecimalFormat;
import java.math.BigDecimal;

public class PriceFormatter {

    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    // 숫자를 #,###  형식으로 반환하는 메서드
    public static String formatPrice(BigDecimal price) {
        return formatter.format(price);
    }
}