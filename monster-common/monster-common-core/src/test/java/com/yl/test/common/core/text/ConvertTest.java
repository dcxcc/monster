package com.yl.test.modules.core.text;

import com.yl.common.core.text.Convert;
import org.junit.Test;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */
public class ConvertTest {
    @Test
    public void test() {
        double money = 123.456789;
        System.out.println(Convert.digitUppercase(money));
    }
}
