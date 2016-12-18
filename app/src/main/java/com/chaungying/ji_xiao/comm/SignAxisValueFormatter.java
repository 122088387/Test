package com.chaungying.ji_xiao.comm;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by philipp on 02/06/16.
 */
public class SignAxisValueFormatter implements AxisValueFormatter {

    private DecimalFormat mFormat;

    public SignAxisValueFormatter() {
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return String.valueOf((int)value);
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
