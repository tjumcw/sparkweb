package com.mcw.sparkweb.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.mcw.sparkweb.common.Result;
import com.mcw.sparkweb.common.enums.ErrorEnum;
import com.mcw.sparkweb.common.exception.BizException;
import com.mcw.sparkweb.domain.po.QueryLogPO;
import com.mcw.sparkweb.domain.vo.QuerySpeakerReqVO;
import com.mcw.sparkweb.domain.vo.QuerySpeechReqVO;
import com.mcw.sparkweb.factory.ServiceFactory;
import com.mcw.sparkweb.service.LogService;
import com.mcw.sparkweb.service.QueryService;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.bson.Document;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import spark.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;



/**
 * @author miaochangwei1
 * @Package : com.mcw.sparkweb.controller
 * @Description : TODO
 * @Create on : 2024/1/15 12:37
 **/
public class Main {

    private static final QueryService queryService = ServiceFactory.getQueryService();

    private static final LogService logService = ServiceFactory.getLogService();

    public static void main(String[] args) {

        port(4567);

        // FreeMarker配置
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 设置FreeMarker引擎
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);

        // 设置Spark静态文件夹路径
        staticFiles.externalLocation("src/main/resources/public");

        get("/", (req, res) -> {
            List<Document> list = logService.queryLogHistory();
            Map<String, Object> map = new HashMap<>();
            if (!CollectionUtils.isEmpty(list)) {
                map.put("logHistoryList", list);
            }
            // 渲染FreeMarker模板
            return new ModelAndView(map, "index.ftl");
        }, freeMarkerEngine);

        get("/speech/:speechId", (req, res) -> {
            String speechId = req.params("speechId");
            Document document = queryService.querySpeechInfoById(speechId);
            return new ModelAndView(document, "speech.ftl");
        }, freeMarkerEngine);

        post("/api/query/speaker", (req, res) -> {
            res.type("application/json");
            try {
                QuerySpeakerReqVO querySpeakerReqVO = JSON.parseObject(req.body(), new TypeReference<QuerySpeakerReqVO>() {});
                List<Document> list = queryService.querySpeakerInfo(querySpeakerReqVO);
                return Result.buildSuccess(list);
            } catch (BizException e) {
                return Result.buildError(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return Result.buildError(ErrorEnum.SERVER_ERROR);
            }
        });

        post("/api/query/speech", (req, res) -> {
            res.type("application/json");
            try {
                QuerySpeechReqVO querySpeechReqVO = JSON.parseObject(req.body(), new TypeReference<QuerySpeechReqVO>() {});
                List<Document> list = queryService.querySpeechInfoByTime(querySpeechReqVO);
                return Result.buildSuccess(list);
            } catch (BizException e) {
                return Result.buildError(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return Result.buildError(ErrorEnum.SERVER_ERROR);
            }
        });

        get("/api/log/history", (req, res) -> {
            res.type("application/json");
            try {
                List<Document> list = logService.queryLogHistory();
                return Result.buildSuccess(list);
            } catch (BizException e) {
                return Result.buildError(e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
                return Result.buildError(ErrorEnum.SERVER_ERROR);
            }
        });

        post("/api/log/add", (req, res) -> {
            res.type("application/json");
            try {
                QueryLogPO queryLogPO = JSON.parseObject(req.body(), new TypeReference<QueryLogPO>() {});
                Boolean isSuccess = logService.insertNewLog(queryLogPO);
                return Result.buildSuccess(isSuccess);
            } catch (BizException e) {
                return Result.buildError(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                return Result.buildError(ErrorEnum.SERVER_ERROR);
            }
        });
    }
}
