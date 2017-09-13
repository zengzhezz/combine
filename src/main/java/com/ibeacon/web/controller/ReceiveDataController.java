package com.ibeacon.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.ibeacon.model.beacon.OriginBeaconModel;
import com.ibeacon.model.inout.InOutData;
import com.ibeacon.model.node.ReNode;
import com.ibeacon.model.variables.StaticVariables;
import com.ibeacon.service.inout.InOutDataService;
import com.ibeacon.service.node.LocateNodeService;
import com.ibeacon.service.node.LocateReNodeService;
import com.ibeacon.service.node.ReNodeService;
import com.ibeacon.utils.Algorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibeacon.web.websocket.Websocket;
import com.ibeacon.model.msginfo.MessageInfo;
import com.ibeacon.service.label.LabelService;
import com.ibeacon.service.node.NodeService;
import com.ibeacon.utils.Constants;
import com.ibeacon.utils.ConvertUtils;
import com.ibeacon.utils.HttpUtils;

/**
 * 处理从慧联接口传过来的原始数据
 *
 * @author zz
 * @version 1.0 2017年3月13日
 */
@Controller
@RequestMapping("/api/")
public class ReceiveDataController {

    private static Logger log = LogManager.getLogger("ReceiveDataController");

    @Autowired
    private NodeService nodeService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ReNodeService reNodeService;

    @Autowired
    private InOutDataService inOutDataService;

    @Autowired
    private LocateNodeService locateNodeService;

    @Autowired
    private LocateReNodeService locateReNodeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "data.do" })
    public MessageInfo GetOriginData(HttpServletRequest request) {
        try {
            // 得到post过来的json字符串
            String jsonData = HttpUtils.getStringFromHttpRequest(request);
            log.debug("receive data:" + jsonData);
            JSONObject obj = new JSONObject(jsonData);
            // 新建一些字符串存取数据
            String mac = obj.getString("mac");
            String appeui = obj.getString("appeui");
            String data = obj.getString("data");
            String lut = obj.getString("last_update_time");
            // 数据的头一定要符合格式，否则认为错误数据，不解析
            if(data.substring(0,2).equals(Constants.VERSION)){
                if (nodeService.checkNodeExist(mac)) {
                    // 解析data
                    for (int i = 2 * 5; i < data.length(); i = i + 6) {
                        // 该字段为uuid
                        String uuid = data.substring(i, i + 4);
                        // 该字段为rssi, 16进制转10进制
                        int rssi = Integer.parseInt(data.substring(i + 4, i + 6),16);
                        String macName = nodeService.findNodeNameByMac(mac);
                        String uuidName = labelService.findUuidNameByUuid(uuid);
                        doAreaDetect(lut, mac, macName, uuid, uuidName, rssi);
                    }
                    Websocket.sendMessageToAll(getLabelMsg(mac));
                } else if(locateNodeService.checkNodeExist(mac)){
                    // 解析data
                    for (int i = 2 * 5; i < data.length(); i = i + 6) {
                        // 该字段为uuid
                        String uuid = data.substring(i, i + 4);
                        // 该字段为rssi, 16进制转10进制
                        String rssi = String.valueOf(Integer.parseInt(data.substring(i + 4, i + 6),16));
                        String macName = nodeService.findNodeNameByMac(mac);
                        String uuidName = labelService.findUuidNameByUuid(uuid);
                        // 值对rssi<100的值进行
                        if(Integer.valueOf(rssi) <= 100){
                            log.debug("写入数据到originalBeaconList...");
                            StaticVariables.originalBeaconList.add(new OriginBeaconModel(uuid,uuidName,mac,rssi,ConvertUtils.formatTimestamp(lut)));
                        }
                    }
                } else {
                    log.debug("该数据对应的mac未注册:" + data);
                }
            }else{
                log.debug("数据格式错误:" + data);
            }
        } catch (JSONException e) {
            log.error(e);
            log.debug("return failed....");
            return new MessageInfo(1, "failed");
        }
        log.debug("return success....");
        return new MessageInfo(0, "success");
    }

    /**
     * 作区域检测
     * @param mac
     * @param macName
     * @param uuid
     * @param uuidName
     * @param rssi
     */
    public void doAreaDetect(String lut, String mac, String macName, String uuid, String uuidName, int rssi){
        // calculate the real distance
        double distance = Algorithm.calDistanceWithRssi(rssi);
        int inout = 0; // 定义进出标志，0表示无进出，1表示进入，2表示离开
        ReNode renode = reNodeService.findReNodeByMac(mac);
        if(distance < 6){
            if(!renode.getUuidList().contains(uuid)){
                inout = 1;
                renode.getUuidList().add(uuid);
            }
        }else if(distance > 10){
            if(renode.getUuidList().contains(uuid)){
                inout = 2;
                renode.getUuidList().remove(uuid);
                reNodeService.saveOrUpdate(renode);
            }
        }
        if(inout != 0){
            String event = (inout == 1)?"进入":"离开";
            Websocket.sendMessageToAll(getFlowMsg(lut, uuid, uuidName, event, mac, macName));
            // 保存进出数据到数据库
            inOutDataService.saveInOutData(uuid, uuidName, mac, macName, event, ConvertUtils.formatTimestamp(lut));
        }
    }

    /**
     * 生成人员信息的字符串,格式("label_msg,mac,人数,人员1,人员2...")
     * @param mac
     * @return
     */
    public String getLabelMsg(String mac) {
        ReNode renode = reNodeService.findReNodeByMac(mac);
        StringBuilder sb = new StringBuilder();
        sb.append("label_msg," + renode.getMac() + "," + renode.getUuidList().size() + ",");
        for (String uuid: renode.getUuidList()) {
            String uuidName = labelService.findUuidNameByUuid(uuid);
            sb.append(uuidName + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 生成流水信息字符串，格式("flow_msg,time,uuid,uuidName,event,macName")
     * @param time
     * @param uuidName
     * @param event
     * @param macName
     * @return
     */
    public String getFlowMsg(String time, String uuid ,String uuidName, String event,
                             String mac, String macName) {
        return "flow_msg,"+ConvertUtils.formatTimestamp(time)+","+uuid+","+uuidName+","+event+","+mac+","+macName;
    }

}
