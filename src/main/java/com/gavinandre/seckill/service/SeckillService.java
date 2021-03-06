package com.gavinandre.seckill.service;

import com.gavinandre.seckill.dto.Exposer;
import com.gavinandre.seckill.dto.SeckillExecution;
import com.gavinandre.seckill.entity.Seckill;
import com.gavinandre.seckill.exception.RepeatKillException;
import com.gavinandre.seckill.exception.SeckillCloseException;
import com.gavinandre.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口:站在"使用者"角度设计接口
 * 三个方面:方法定义粒度,参数,返回类型(return 类型/异常)
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址,
     * 否则输出系统时间和秒杀时间
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀过程
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * 执行秒杀过程by存储过程
     */
    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);
}
