package com.yl.modules.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yl.modules.log.domain.OperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: YL
 * @Date: 2024-05-16
 * @Project monster
 */
@Mapper
public interface LogMapper extends BaseMapper<OperLog> {
}
