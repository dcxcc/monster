package com.yl.common.core.web.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */


@Data
public class PageResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private String msg;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> rows;


}
