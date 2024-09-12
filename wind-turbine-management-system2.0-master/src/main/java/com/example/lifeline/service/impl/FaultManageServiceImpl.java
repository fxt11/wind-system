package com.example.lifeline.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lifeline.entity.FaultManage;
import com.example.lifeline.entity.Oldparts;
import com.example.lifeline.entity.Turbine;
import com.example.lifeline.mapper.FaultManageMapper;
import com.example.lifeline.mapper.OldpartsMapper;
import com.example.lifeline.mapper.TurbineMapper;
import com.example.lifeline.service.FaultManageService;
import com.example.lifeline.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("FaultManageService")
public class FaultManageServiceImpl implements FaultManageService {

    @Autowired
    private FaultManageMapper faultManageMapper;

    @Autowired
    private OldpartsMapper oldpartsMapper;

    @Autowired
    private TurbineMapper turbineMapper;

    // 获取所有故障信息
    public Result getAllInfo(int pageNum) {
        Page<FaultManage> page = new Page<>(pageNum, 10);
        IPage<FaultManage> iPage = faultManageMapper.selectPage(page, null);
        return Result.ok().data("page", iPage);
    }

    // 获取某一个故障的信息(根据机组编号)
    public Result getInfo(String turbineId) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.eq("faultTurbine", turbineId);
        Page<FaultManage> page = new Page<>(1, 10);
        IPage<FaultManage> iPage = faultManageMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    // 获取所有故障位置一级的故障信息
    public Result getAllFaultOne() {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.select("faultLocationOne as name,count(*) as value");
        wrapper.groupBy("faultLocationOne");
        List<Map<String, Object>> list = faultManageMapper.selectMaps(wrapper);
        return Result.ok().data("list", list);
    }

    // 获取所有故障位置二级的故障信息
    public Result getAllFaultTwo(String faultLocationOne) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.select("faultLocationTwo as name,count(*) as value");
        wrapper.eq("faultLocationOne", faultLocationOne);
        wrapper.groupBy("faultLocationTwo");
        List<Map<String, Object>> list = faultManageMapper.selectMaps(wrapper);
        return Result.ok().data("list", list);
    }

    // 获取所有排查项目(数量越多表示越需要重点排查)
    public Result getAllCheckItems() {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.select("checkItems as name,count(*) as value");
        wrapper.groupBy("checkItems");
        List<Map<String, Object>> list = faultManageMapper.selectMaps(wrapper);
        return Result.ok().data("list", list);
    }

    // 统计故障类型
    public Result getAllFaultType() {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.select("faultType as name,count(*) as value");
        wrapper.groupBy("faultType");
        List<Map<String, Object>> list = faultManageMapper.selectMaps(wrapper);
        Object[] dataAxis = new Object[list.size()];
        Object[] data = new Object[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Map<String, Object> map = list.get(i);
            dataAxis[i] = map.get("name");
            data[i] = map.get("value");
        }
        return Result.ok().data("dataAxis", dataAxis).data("data", data);
    }

    // 获取某个故障导致的停机维护时间（为维修人员所用）
    public Result getMaintenanceTime(int faultId) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.select("maintenanceTime").eq(":faultId", faultId);
        return Result.ok().data("maintenance_time", faultManageMapper.selectOne(wrapper));
    }

    // 添加故障
    public Result addFault(FaultManage faultManage) {
        faultManageMapper.insert(faultManage);
        return Result.ok();
    }

    // 查询所有未被维修的设备
    public Result getOneInfo(int pageNum) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.lambda().and(wrapper1 -> wrapper1.isNull(FaultManage::getMaintenanceTime))
                .and(wrapper2 -> wrapper2.isNull(FaultManage::getResetRunningTime));
        Page<FaultManage> page = new Page<>(pageNum, 10);
        IPage<FaultManage> iPage = faultManageMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    // 获取单个未安排维修设备的信息
    public Result getOneNoInfo(String turbineId) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.lambda().and(wrapper1 -> wrapper1.isNull(FaultManage::getMaintenanceTime))
                .and(wrapper2 -> wrapper2.isNull(FaultManage::getResetRunningTime));
        wrapper.eq("faultTurbine", turbineId);
        Page<FaultManage> page = new Page<>(1, 10);
        IPage<FaultManage> iPage = faultManageMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    // 查询所有未完成维修的设备
    public Result getTwoInfo(int pageNum) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.lambda().and(wrapper1 -> wrapper1.isNotNull(FaultManage::getMaintenanceTime))
                .and(wrapper1 -> wrapper1.isNull(FaultManage::getResetRunningTime));
        Page<FaultManage> page = new Page<>(pageNum, 10);
        IPage<FaultManage> iPage = faultManageMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    // 获取单个未完成维修设备的信息
    public Result getOneNonInfo(String turbineId) {
        QueryWrapper<FaultManage> wrapper = new QueryWrapper<>();
        wrapper.lambda().and(wrapper1 -> wrapper1.isNotNull(FaultManage::getMaintenanceTime))
                .and(wrapper2 -> wrapper2.isNull(FaultManage::getResetRunningTime));
        wrapper.eq("faultTurbine", turbineId);
        Page<FaultManage> page = new Page<>(1, 10);
        IPage<FaultManage> iPage = faultManageMapper.selectPage(page, wrapper);
        return Result.ok().data("page", iPage);
    }

    // 更新维修时间
    public Result updateFaultInfo(FaultManage faultManage) {
        UpdateWrapper<FaultManage> wrapper = new UpdateWrapper<>();
        wrapper.lambda().and(wrapper1 -> wrapper1.isNull(FaultManage::getMaintenanceTime))
                .and(wrapper2 -> wrapper2.isNull(FaultManage::getResetRunningTime));
        wrapper.eq("faultTurbine", faultManage.getFaultTurbine());
        wrapper.set("maintenanceTime", faultManage.getMaintenanceTime());
        faultManageMapper.update(null, wrapper);
        return Result.ok();
    }

    // 更新维修完成时间
    public Result updateOkFaultInfo(FaultManage faultManage) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UpdateWrapper<FaultManage> wrapper = new UpdateWrapper<>();
        wrapper.lambda().and(wrapper1 -> wrapper1.isNotNull(FaultManage::getMaintenanceTime))
                .and(wrapper2 -> wrapper2.isNull(FaultManage::getResetRunningTime));
        wrapper.eq("faultTurbine", faultManage.getFaultTurbine());
        wrapper.set("resetRunningTime", formatter.format(calendar.getTime()));
        faultManageMapper.update(null, wrapper);
        return Result.ok();
    }

    // 获取备件消耗
    public Result getAllParts() {
        QueryWrapper<Oldparts> wrapper = new QueryWrapper<>();
        wrapper.select("partName as name,quantity as value");
        List<Map<String, Object>> list = oldpartsMapper.selectMaps(wrapper);
        return Result.ok().data("list", list);
    }

    // 获取经济消耗
    public Result getEconomyConsume() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>(); // 存放原因和经济损失
        List<FaultManage> list = faultManageMapper.selectList(null);
        // 查询每一个故障所消耗的零件以及损失的发电量
        for (FaultManage faultManage : list) {
            String partName = faultManage.getCheckItems();
            QueryWrapper<Oldparts> wrapper = new QueryWrapper<>();
            wrapper.eq("partName", partName);
            Oldparts oldParts = oldpartsMapper.selectOne(wrapper);
            if (oldParts != null) {
                double price = oldParts.getUnitPrice();
                // 根据故障机组编号查询它24小时的产电量
                QueryWrapper<Turbine> turbineQueryWrapper = new QueryWrapper<>();
                turbineQueryWrapper.eq("turbineId", faultManage.getFaultTurbine());
                double production = turbineMapper.selectOne(turbineQueryWrapper).getTurbineProduction();
                // 统计此故障导致的电费损失
                if (!Objects.equals(faultManage.getResetRunningTime(), "无")) {
                    price += (getTime(faultManage.getResetRunningTime()) - getTime(faultManage.getFaultReportingTime())) / 1000 / 3600 / 24 * production * 0.92 * 0.38;
                } else {
                    price += (System.currentTimeMillis() - getTime(faultManage.getFaultReportingTime())) / 1000 / 3600 / 24 * production * 0.92 * 0.38;
                }
                hashMap.put(faultManage.getFaultName(), price);
            }
        }
        // 转化为饼状图可以接收的数据
        List<Map<String, Object>> LIST = new ArrayList<>();
        for (String key : hashMap.keySet()) {
            Map<String, Object> MAP = new HashMap<>();
            MAP.put("name", key);
            MAP.put("value", hashMap.get(key));
            LIST.add(MAP);
        }
        return Result.ok().data("list", LIST);
    }

    // 获取时间戳(毫秒级)
    public double getTime(String TIME) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(TIME);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }

    @Override
    public void batchInsert(List<FaultManage> list) {
        faultManageMapper.insertBatchSomeColumn(list);
    }

    @Override
    public Long selectCount(Wrapper<FaultManage> queryWrapper) {
        return faultManageMapper.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<FaultManage>> P selectPage(P page, Wrapper<FaultManage> queryWrapper) {
        return faultManageMapper.selectPage(page, queryWrapper);
    }
}
