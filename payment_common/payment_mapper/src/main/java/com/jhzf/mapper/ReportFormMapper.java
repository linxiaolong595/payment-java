package com.jhzf.mapper;

import com.jhzf.vo.reportForm.ReportFormVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/16 10:04
 */
@Mapper
public interface ReportFormMapper {
    List<ReportFormVo> selectStoreReportForm(@Param("storeId") String storeId);
}
