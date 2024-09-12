package com.example.lifeline.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.service.baseservice.BaseService;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExportListener<T> {


    private BaseService<T> baseService;

    public ExportListener(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    private static final String DATA_FORMAT = "yyyy-MM-dd-HH-mm-ss";

    private static final String CHARACTER_UTF_8 = "UTF-8";

    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    private static final String ACCESS_CONTROL_EXPOSE = "Access-Control-Expose-Headers";

    private static final int PAGE_SIZE = 10000;

    public void exportExcel(HttpServletResponse response, String sheetName, Class<T> pojoClass,
                            LambdaQueryWrapper<T> queryWrapper) throws IOException {
        ServletOutputStream out = getServletOutputStream(response, sheetName);
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(out, pojoClass).build();
        // 这里注意 如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        int totalCount = Math.toIntExact(baseService.selectCount(queryWrapper));
        int pageNumber = (int) Math.ceil((double) totalCount / (double) PAGE_SIZE);    //分页条数看情况
        // 去调用写入,根据数据库分页的总的页数来
        for (int i = 1; i <= pageNumber; i++) {
            //先定义一个空集合每次循环使他变成null减少内存的占用
            List<T> recordList;
            Page<T> page = new Page<>(i, PAGE_SIZE);
            Page<T> pojoIPage = baseService.selectPage(page, queryWrapper);
            recordList = pojoIPage.getRecords();
            excelWriter.write(recordList, writeSheet);
            recordList.clear();
        }
        excelWriter.finish();
        out.flush();
    }

    /**
     * 查询优化的方法
     */
    public void exportNoQueryCount(HttpServletResponse response, String sheetName, Class<T> pojoClass,
                                   LambdaQueryWrapper<T> queryWrapper) throws IOException {
        ServletOutputStream out = getServletOutputStream(response, sheetName);
        ExcelWriter excelWriter = EasyExcel.write(out, pojoClass).build();

        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        int startIndex = 1;
        while (true) {
            int startParam = (startIndex - 1) * PAGE_SIZE;
            int pageNumber = (int) Math.ceil((double) startParam / (double) PAGE_SIZE + 1);
            Page<T> page = new Page<>(pageNumber, PAGE_SIZE, false);
            Page<T> pojoIPage = baseService.selectPage(page, queryWrapper);
            List<T> recordList = pojoIPage.getRecords();
            if (CollectionUtils.isEmpty(recordList)) {
                break;
            }
            startIndex++;
            excelWriter.write(recordList, writeSheet);
        }
        excelWriter.finish();
    }

    public static ServletOutputStream getServletOutputStream(HttpServletResponse response, String sheetName) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_FORMAT);
        String nowTime = formatter.format(LocalDateTime.now());
        String fileName = sheetName.concat("_").concat(nowTime).concat(".xlsx");
        response.setContentType(CONTENT_TYPE);
        //设置字符集为utf-8
        response.setCharacterEncoding(CHARACTER_UTF_8);
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        return response.getOutputStream();
    }
}

