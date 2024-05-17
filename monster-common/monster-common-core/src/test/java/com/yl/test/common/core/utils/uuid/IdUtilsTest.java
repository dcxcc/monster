package com.yl.test.modules.core.utils.uuid;

import com.yl.common.core.utils.uuid.IdUtils;
import org.junit.Test;

/**
 * @Author: YL
 * @Date: 2024-05-13
 * @Project monster
 */

public class IdUtilsTest {

    @Test
    public void test()
    {
        System.out.println("IdUtils.randomUUID() = " + IdUtils.randomUUID());
        //输出没有横杠的UUID
        System.out.println("IdUtils.simpleUUID() = " + IdUtils.simpleUUID());

    }
}
