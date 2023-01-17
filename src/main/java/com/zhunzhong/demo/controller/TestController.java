package com.zhunzhong.demo.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhunzhong.demo.common.EventEnum;
import com.zhunzhong.demo.pojo.entity.FaultDict;
import com.zhunzhong.demo.pojo.entity.RollingDoorDto;
import com.zhunzhong.demo.pojo.entity.VehicleInfo;
import com.zhunzhong.demo.statemachine.TestEvents;
import com.zhunzhong.demo.statemachine.TestStates;
import com.zhunzhong.demo.task.ThreadPoolTask;
import com.zhunzhong.demo.util.SpringUtils;

import kotlin.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StateMachine<TestStates, TestEvents> stateMachine;

    public volatile Map<String, String> filePathMap = Maps.newHashMap();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private ThreadPoolTask threadPoolTask;

    @GetMapping("importBigFile2")
    public String readBigFile2() throws IOException {
        List<FaultDict> faultDictList = new ArrayList<>();
        FileInputStream fis = new FileInputStream("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\故障码导入模板-big.xlsx");
        EasyExcel.read(fis, FaultDict.class, new ReadListener<FaultDict>() {

            @Override
            public void invoke(FaultDict faultDict, AnalysisContext analysisContext) {
                System.out.println(JSON.toJSONString(faultDict));
                faultDictList.add(faultDict);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {

            }
        }).sheet(0).doRead();

        System.out.println(faultDictList.size());
        return String.valueOf(10);
    }

    @GetMapping("importBigFile1")
    public String readBigFile1() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\故障码导入模板-big.xlsx");
        ExcelReader excelReader = ExcelUtil.getReader(fis);
        excelReader.getRowCount();
        excelReader.read(0, 10);

        return String.valueOf(10);
    }


    @GetMapping("testValid")
    @ResponseBody
    public String testValid() {
        try {
            rollingDoor(new RollingDoorDto());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "success";
    }

    void rollingDoor(@Valid RollingDoorDto rollingDoorDTO) {

    }

    @GetMapping("importBigFile")
    public String readBigFile() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\codes\\open-source\\zhunzhong\\mqtt-demo\\故障码导入模板-big.xlsx");
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        int n = sheet.getLastRowNum();
        System.out.println(n);
        return String.valueOf(n);
    }

    @GetMapping("exportVehicle")
    public Result<Void> exportVehicle(HttpServletResponse response) throws IOException {
        List<VehicleInfo> list = new ArrayList<>();
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setVehicleNo("111");
        vehicleInfo.setVehicleTypeCode("1111");
        vehicleInfo.setMac("aaa");
        vehicleInfo.setVin("ppp");
        vehicleInfo.setId(10000L);
        list.add(vehicleInfo);

        ExcelUtil.getWriter();
        ExcelWriter writer = ExcelUtil.getBigWriter();

        String title = "车辆管理_" + new SimpleDateFormat("yyyyMMdd").format(new Date());

        ServletOutputStream outputStream = null;
        try {
            //Excel默认配置
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");
            //设置标题
            String fileName = URLEncoder.encode(title, "UTF-8");
            //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            outputStream = response.getOutputStream();
//        writer.autoSizeColumnAll();//设置自动列宽
            writer.setColumnWidth(-1, 20);
            commonExcelWithHeader(writer, list);
            //writer.flush(outputStream);
            commonExcel(writer, list);
            writer.flush(outputStream);
            //将Writer刷新到OutPut
            // writerWithHeader.flush(outputStream, false);//TODO 测试多次刷新

        } catch (Exception e) {
            log.error("导出异常", e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            writer.close();
        }
        return null;
    }

    ExcelWriter commonExcelWithHeader(ExcelWriter writer, List<VehicleInfo> list) {
        //自定义标题别名
        writer.addHeaderAlias("vehicleNo", "车牌号");
        writer.addHeaderAlias("mac", "车机蓝牙MAC");
        writer.addHeaderAlias("vin", "车辆VIN码");
        writer.addHeaderAlias("vehicleTypeCode", "车辆型号编码");
        writer.setOnlyAlias(true);//只保留设置了别名的字段
        writer.write(list, true);
        return writer;
    }

    ExcelWriter commonExcel(ExcelWriter writer, List<VehicleInfo> list) {
        for (int i = 0; i < 1000000; i++) {
            writer.write(list);
        }
        return writer;
    }

    static void export(HttpServletResponse response, ExcelWriter writer, String title) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            //Excel默认配置
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");
            //设置标题
            String fileName = URLEncoder.encode(title, "UTF-8");
            //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            outputStream = response.getOutputStream();
//        writer.autoSizeColumnAll();//设置自动列宽
            writer.setColumnWidth(-1, 20);
            //将Writer刷新到OutPut
            writer.flush(outputStream, true);//TODO 测试多次刷新
        } catch (Exception e) {
            log.error("导出异常", e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            writer.close();
        }
    }

    @GetMapping("/map")
    @ResponseBody
    public String map() {
        int count = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int finalI = i;
            executorService.submit(() -> {
                filePathMap.put(String.valueOf(finalI), String.valueOf(finalI));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("size:{}", filePathMap.entrySet().size());
        log.info(JSON.toJSONString(filePathMap));
        return "success";
    }

    @GetMapping("/event")
    @ResponseBody
    public String event() {
        SpringUtils.applicationContext.publishEvent(EventEnum.EVENT1);
        return "success";
    }

    @GetMapping("/stateMachine")
    @ResponseBody
    public String stateMachine() {
        //stateMachine.start();
        stateMachine.sendEvent(TestEvents.PAY);
        stateMachine.sendEvent(TestEvents.RECEIVE);
        return "success";
    }

    @GetMapping("/threadPoolTest")
    @ResponseBody
    public String threadPoolTest(@RequestParam("count") int count) {
        threadPoolTask.test(count);
        return "success";
    }


}
