/* Copyright 2013-2015 www.snakerflow.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fantasia.workflow;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasia.core.DbcContext;
import com.fantasia.snakerflow.engine.SnakerEngineFacets;

/**
 * @author yuqs
 * @since 2.0
 */
@Service("KpiFlowService")
public class KpiFlowService {
    @Autowired
    private SnakerEngineFacets facets;
  
    public void process(Map<String, Object> params) {
        String processId = params.get("processId").toString();
        String orderId = params.get("orderId").toString();
        String taskId = params.get("taskId").toString();
        String nextOperator = "";
        
        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(taskId)) {
            facets.startAndExecute(processId, DbcContext.getUser().getUserName(), params);
        } else {
           
            int method = 0;
           
            switch(method) {
                case 0://任务执行
                    facets.execute(taskId, DbcContext.getUser().getUserName(), params);
                    break;
                case -1://驳回、任意跳转
                    facets.executeAndJump(taskId, DbcContext.getUser().getUserName(), params, "");
                    break;
                case 1://转办
                    if(StringUtils.isNotEmpty(nextOperator)) {
                        facets.transferMajor(taskId,DbcContext.getUser().getUserName(), nextOperator.split(","));
                    }
                    break;
                case 2://协办
                    if(StringUtils.isNotEmpty(nextOperator)) {
                        facets.transferAidant(taskId, DbcContext.getUser().getUserName(), nextOperator.split(","));
                    }
                    break;
                default:
                    facets.execute(taskId, DbcContext.getUser().getUserName(), params);
                    break;
            }
        }
        String ccOperator = "";
        if(StringUtils.isNotEmpty(ccOperator)) {
            facets.getEngine().order().createCCOrder(orderId, ccOperator.split(","));
        }
    }
}
