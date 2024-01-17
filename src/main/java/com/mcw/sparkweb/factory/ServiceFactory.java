package com.mcw.sparkweb.factory;

import com.mcw.sparkweb.service.LogService;
import com.mcw.sparkweb.service.QueryService;
import com.mcw.sparkweb.service.impl.LogServiceImpl;
import com.mcw.sparkweb.service.impl.QueryServiceImpl;

/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.service.factory
 * @Description : TODO
 * @Create on : 2024/1/15 14:01
 **/
public class ServiceFactory {

    public static QueryService queryServiceInstance;

    public static LogService logServiceInstance;

    /**
     * 获取查询服务实例
     * @return 查询服务实例
     */
    public static QueryService getQueryService() {
        if (queryServiceInstance == null) {
            queryServiceInstance = new QueryServiceImpl();
        }
        return queryServiceInstance;
    }

    /**
     * 获取日志服务实例
     * @return 返回日志服务实例
     */
    public static LogService getLogService() {
        if (logServiceInstance == null) {
            logServiceInstance = new LogServiceImpl();
        }
        return logServiceInstance;
    }
}
