package com.jhzf.service.impl;

import com.jhzf.mapper.ReportFormMapper;
import com.jhzf.pojo.PaymentOrder;
import com.jhzf.pojo.PaymentStore;
import com.jhzf.service.ReportFormService;
import com.jhzf.util.ResponseDTO;
import com.jhzf.vo.reportForm.OrdersVo;
import com.jhzf.vo.reportForm.ReportFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    public ResponseDTO selectStoreReportForm(String[] data,int storeId) {
        List<ReportFormVo> reportForm = reportFormMapper.selectStoreReportForm(data,storeId);
        Map<String, Object> chartData = convertToChartData(reportForm);
        return ResponseDTO.success(200,"success",chartData);
    }

    @Override
    public ResponseDTO selectStoreName() {
        List<PaymentStore> name = reportFormMapper.selectStoreName();
        return ResponseDTO.success(200,"success",name);
    }

    @Override
    public ResponseDTO selectStoreMoney(String[] data,int storeId) {
        List<PaymentOrder> orders = reportFormMapper.selectStoreMoney(data,storeId);
        int count=0;
        double sum=0;
        double refund=0;
        double average=0;

        for (PaymentOrder order : orders) {
            if (order.getOrderReback()==0){
                count++;
                sum += order.getOrderMoney();
            } else {
                refund += order.getOrderMoney();
            }
        }
        if (count!=0){
            average = sum/count;
        }
        // 使用 DecimalFormat 格式化平均值并保留两位小数
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAverage = df.format(average);

        Map<String, Object> money = new HashMap<>();
        money.put("count",count);
        money.put("sum",sum);
        money.put("refund",refund);
        money.put("average",formattedAverage);

        return ResponseDTO.success(200,"success",money);
    }

    @Override
    public ResponseDTO selectStoreOrder(OrdersVo ordersVo) {
        List<PaymentOrder> orders = reportFormMapper.selectStoreOrder(ordersVo);
        // 创建一个 Map 用于存储每日的统计数据，键为日期，值为该日期的统计信息
        Map<LocalDate, Map<String, Object>> dailyStats = new HashMap<>();

        for (PaymentOrder order : orders) {
            // 定义日期时间格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // 解析日期字符串
            LocalDateTime dateTime = LocalDateTime.parse(order.getOrderCreatetime(), formatter);

            // 转换为 LocalDate
            LocalDate orderDate = dateTime.toLocalDate();

            // 获取该日期的统计信息，如果不存在则创建一个新的统计信息对象
            Map<String, Object> stats = dailyStats.computeIfAbsent(orderDate, k -> new HashMap<>());

            // 更新该日期的统计信息
            int count = (int) stats.getOrDefault("count", 0);
            double sum = (double) stats.getOrDefault("sum", 0.0);
            double refund = (double) stats.getOrDefault("refund", 0.0);
            int refundSum = (int) stats.getOrDefault("refundSum", 0);

            if (order.getOrderReback() == 0) {
                count++;
                sum += order.getOrderMoney();
            } else {
                refundSum++;
                refund += order.getOrderMoney();
            }

            stats.put("count", count);
            stats.put("sum", sum);
            stats.put("refund", refund);
            stats.put("refundSum", refundSum);
        }

        // 构造最终结果，按日期倒序排序
        List<Map<String, Object>> dailyStatsList = new ArrayList<>();
        dailyStats.entrySet().stream()
                .sorted(Map.Entry.<LocalDate, Map<String, Object>>comparingByKey().reversed())
                .forEach(entry -> {
                    Map<String, Object> dailyStat = new HashMap<>();
                    dailyStat.put("date", entry.getKey());
                    dailyStat.putAll(entry.getValue());
                    dailyStatsList.add(dailyStat);
                });

        return ResponseDTO.success(200, "success", dailyStatsList);
    }


    public static Map<String, Object> convertToChartData(List<ReportFormVo> reportForms) {
        // 使用 TreeMap 来自动按照日期排序
        Map<String, List<Double>> storeDataMap = new TreeMap<>();
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

            // 如果店铺数据集合中没有当前店铺，则添加，并将其数据列表初始化为0
            if (!storeDataMap.containsKey(storeName)) {
                List<Double> dataList = new ArrayList<>(Collections.nCopies(categories.size(), 0.0));
                storeDataMap.put(storeName, dataList);
            }

            // 确保数据列表大小与日期列表大小相同
            List<Double> dataList = storeDataMap.get(storeName);
            while (dataList.size() < categories.size()) {
                dataList.add(0.0);
            }

            // 将数据添加到店铺对应的数据集合中
            int index = categories.indexOf(date);
            dataList.set(index, data);
        }

        // 补全缺失的日期数据，确保每个店铺的数据列表长度与日期列表一致
        for (List<Double> dataList : storeDataMap.values()) {
            while (dataList.size() < categories.size()) {
                dataList.add(0.0);
            }
        }

        // 构建返回的数据结构
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
