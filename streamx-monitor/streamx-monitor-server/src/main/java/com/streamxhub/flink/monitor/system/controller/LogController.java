package com.streamxhub.flink.monitor.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.streamxhub.flink.monitor.base.annotation.Log;
import com.streamxhub.flink.monitor.base.controller.BaseController;
import com.streamxhub.flink.monitor.base.exception.AdminXException;
import com.streamxhub.flink.monitor.system.entity.SysLog;
import com.streamxhub.flink.monitor.system.service.LogService;
import com.streamxhub.flink.monitor.base.domain.RestRequest;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author benjobs
 */
@Slf4j
@Validated
@RestController
@RequestMapping("log")
public class LogController extends BaseController {

    private String message;

    @Autowired
    private LogService logService;

    @PostMapping("list")
    @RequiresPermissions("log:view")
    public Map<String, Object> logList(RestRequest request, SysLog sysLog) {
        return getDataTable(logService.findLogs(request, sysLog));
    }

    @Log("删除系统日志")
    @DeleteMapping("delete")
    @RequiresPermissions("log:delete")
    public void deleteLogss(@NotBlank(message = "{required}") String ids) throws AdminXException {
        try {
            String[] logIds = ids.split(StringPool.COMMA);
            this.logService.deleteLogs(logIds);
        } catch (Exception e) {
            message = "删除日志失败";
            log.info(message, e);
            throw new AdminXException(message);
        }
    }

    @PostMapping("export")
    @RequiresPermissions("log:export")
    public void export(RestRequest request, SysLog sysLog, HttpServletResponse response) throws AdminXException {
        try {
            List<SysLog> sysLogs = this.logService.findLogs(request, sysLog).getRecords();
            ExcelKit.$Export(SysLog.class, response).downXlsx(sysLogs, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.info(message, e);
            throw new AdminXException(message);
        }
    }
}
