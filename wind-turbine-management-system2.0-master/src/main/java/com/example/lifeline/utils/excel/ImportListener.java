package com.example.lifeline.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.example.lifeline.service.baseservice.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ImportListener<T> implements ReadListener<T> {

    private final BaseService<T> baseService;

    /**
     * 每隔100条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private final AtomicInteger count = new AtomicInteger(0);

    public ImportListener(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    @Override
    public void invoke(T entity, AnalysisContext analysisContext) {
        count.addAndGet(1);

        log.info("解析到一条数据:{}", JSON.toJSONString(entity));
        cachedDataList.add(entity);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            batchInsert();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        batchInsert();
        log.info("所有数据解析完成！");
    }

    @Async
    public void batchInsert() {
        log.info("{}条数据，开始存储数据库!", count.get());
        baseService.batchInsert(cachedDataList);
        log.info("存储数据库成功!");
    }
}

