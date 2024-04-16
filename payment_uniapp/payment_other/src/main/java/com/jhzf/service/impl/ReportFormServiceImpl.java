package com.jhzf.service.impl;

import com.jhzf.mapper.ReportFormMapper;
import com.jhzf.service.ReportFormService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.reportForm.ReportFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @Author： 林晓龙
 * @DATE: 2024/4/16 11:05
 */
@Service
public class ReportFormServiceImpl implements ReportFormService {
    @Autowired
    private ReportFormMapper reportFormMapper;
    @Override
    public ResponseDTO selectStoreReportForm(String storeId) {
        List<ReportFormVo> reportForm = reportFormMapper.selectStoreReportForm(storeId);
        Map<String, Object> chartData = convertToChartData(reportForm);
        return ResponseDTO.success(200,"success",chartData);
    }


    public static Map<String, Object> convertToChartData(List<ReportFormVo> reportForms) {
        Map<String, List<Double>> storeDataMap = new HashMap<>();
        List<String> categories = new ArrayList<>();


        // 遍历报表数据，将数据按照日期和店铺进行归类
        for (ReportFormVo reportForm : reportForms) {
            String date = reportForm.getCategories();
            String storeName = reportForm.getName();
            double data = reportForm.getData();

            // 如果日期列表中没有当前日期，则添加
            if (!categories.contains(date)) {
                categories.add(date);
            }

            // 如果店铺数据集合中没有当前店铺，则添加
            if (!storeDataMap.containsKey(storeName)) {
                storeDataMap.put(storeName, new ArrayList<>());
            }

            // 将数据添加到店铺对应的数据集合中
            storeDataMap.get(storeName).add(data);
        }

        // 构建最终的格式化数据
        List<Map<String, Object>> series = new ArrayList<>();
        for (String storeName : storeDataMap.keySet()) {
            Map<String, Object> seriesData = new HashMap<>();
            seriesData.put("name", storeName);
            seriesData.put("data", storeDataMap.get(storeName));
            series.add(seriesData);
        }

        // 构建返回的数据结构
        Map<String, Object> chartData = new HashMap<>();
        chartData.put("categories", categories);
        chartData.put("series", series);

        return chartData;
    }
}
