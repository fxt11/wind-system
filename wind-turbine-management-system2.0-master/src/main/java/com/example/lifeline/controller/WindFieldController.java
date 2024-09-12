package com.example.lifeline.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.lifeline.entity.WindField;
import com.example.lifeline.service.WindFieldService;
import com.example.lifeline.utils.excel.ExportListener;
import com.example.lifeline.utils.excel.ImportListener;
import com.example.lifeline.utils.log.OperationLogAnnotation;
import com.example.lifeline.utils.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/WindField")
@Api(tags = "风场管理")
public class WindFieldController {

    @Autowired
    @Qualifier("WindFieldService")
    private WindFieldService windFieldService;

    @GetMapping("/all_windfield_info")
    @ApiOperation(value = "显示风场全部信息")
    @OperationLogAnnotation(module = "windfield", operationType = "GET", operationDesc = "显示风场全部信息")
    public Result getAllWindFieldInfo(@RequestParam(defaultValue = "1") int pageNum) {
        return windFieldService.getAllWindField(pageNum);
    }

    @GetMapping("/windfield_info")
    @ApiOperation(value = "查询某个风场信息")
    @OperationLogAnnotation(module = "windfield", operationType = "GET", operationDesc = "查询某个风场信息")
    public Result getWindFieldInfo(@RequestParam String windFieldName) {
        return windFieldService.getWindField(windFieldName);
    }

    @GetMapping("/all_turbine_info")
    @ApiOperation(value = "查询某个风场中的所有机组")
    @OperationLogAnnotation(module = "windfield", operationType = "GET", operationDesc = "查询某个风场中的所有机组")
    public Result getAllTurbines(@RequestParam String windFieldName, @RequestParam(defaultValue = "1") int pageNum) {
        return windFieldService.getAllTurbines(windFieldName, pageNum);
    }

    @PostMapping("/add_windfield")
    @ApiOperation(value = "新增一个风场")
    @OperationLogAnnotation(module = "windfield", operationType = "POST", operationDesc = "新增一个风场")
    public Result addWindField(@RequestBody WindField windField, @RequestParam(defaultValue = "1") int pageNum) throws Exception {
        return windFieldService.addWindField(windField, pageNum);
    }

    @PostMapping("/edit_windfield")
    @ApiOperation(value = "编辑一个风场")
    @OperationLogAnnotation(module = "windfield", operationType = "POST", operationDesc = "编辑一个风场")
    public Result editWindField(@RequestBody WindField windField, @RequestParam(defaultValue = "1") int pageNum) {
        return windFieldService.editWindField(windField, pageNum);
    }

    @PostMapping("/delete_windfield")
    @ApiOperation(value = "删除一个风场")
    @OperationLogAnnotation(module = "windfield", operationType = "POST", operationDesc = "删除一个风场")
    public Result deleteWindField(@RequestParam String windFieldName) {
        return windFieldService.deleteWindField(windFieldName);
    }

    @GetMapping("/all_windfield_name")
    @ApiOperation(value = "获取所有风场名称")
    @OperationLogAnnotation(module = "windfield", operationType = "GET", operationDesc = "获取所有风场名称")
    public Result getAllTurbines() {
        return windFieldService.getAllWindFieldName();
    }

    @GetMapping("/extemplate")
    @ApiOperation(value = "导出风场信息表模板")
    @OperationLogAnnotation(module = "WindFieldManage", operationType = "GET", operationDesc = "导出风场信息表模板")
    public void downloadModule(HttpServletResponse response) throws IOException {
        //从数据库中读出所有数据
        List<WindField> windFieldList = new ArrayList<>();
        //返回输出流excel格式
        String sheetName = "风场信息表模板";
        String fileName = sheetName.concat(".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //设置字符集为utf-8
        response.setCharacterEncoding("UTF-8");
        //使前端可以获取文件名解码转成中文
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //调用EasyExcel的write方法生成表格返回给用户
        EasyExcel.write(response.getOutputStream(), WindField.class).autoCloseStream(Boolean.FALSE).sheet("风场信息表模板").doWrite(windFieldList);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出风场信息Excel")
    @OperationLogAnnotation(module = "WindFieldManage", operationType = "GET", operationDesc = "导出风场信息表")
    public void exportExcel(HttpServletResponse response) throws IOException {
        LambdaQueryWrapper<WindField> queryWrapper = new LambdaQueryWrapper<>();
        new ExportListener<>(windFieldService)
                .exportExcel(response, "风场信息表", WindField.class, queryWrapper);
    }

    @PostMapping(value = "/import")
    @ApiOperation(value = "导入风场信息Excel")
    @OperationLogAnnotation(module = "WindFieldManage", operationType = "POST", operationDesc = "导入风场信息表")
    public void importExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), WindField.class,
                new ImportListener<>(windFieldService)).sheet().headRowNumber(1).doRead();
    }
}
