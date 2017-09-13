package com.ibeacon.web.controller;

import com.ibeacon.model.msginfo.MessageInfo;
import com.ibeacon.model.node.LocateNode;
import com.ibeacon.model.node.Node;
import com.ibeacon.service.node.LocateNodeService;
import com.ibeacon.service.node.NodeService;
import com.ibeacon.utils.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 页面显示的节点控制器
 * Created by zz on 2017/8/4.
 */
@Controller
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private LocateNodeService locateNodeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/getallnode" })
    public List<Node> getAllNode(HttpServletRequest request){
        List<Node> nodeList = nodeService.findAllNode();
        return nodeList;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/savenode" })
    public MessageInfo saveNode(HttpServletRequest request){
        try {
            String jsonData = HttpUtils.getStringFromHttpRequest(request);
            JSONArray jsonArray = new JSONArray(jsonData);
            for (Object object : jsonArray) {
                JSONObject jsonObj = (JSONObject) object;
                String mac = jsonObj.getString("mac");
                String name = jsonObj.getString("name");
                String nodeTop = jsonObj.getString("nodeTop");
                String nodeLeft = jsonObj.getString("nodeLeft");
                if(!nodeService.checkNodeExist(mac)){
                    nodeService.saveNode(mac, name, nodeTop, nodeLeft);
                }else{
                    nodeService.updateNodeLocation(mac, nodeTop, nodeLeft);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new MessageInfo(1,"服务器错误...");
        }
        return new MessageInfo(0,"success");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/deletenode" })
    public MessageInfo deleteNode(HttpServletRequest request){
        String mac = request.getParameter("mac");
        if(nodeService.deleteNodeByMac(mac)){
            return new MessageInfo(0,"success");
        }else{
            return new MessageInfo(1,"删除节点失败...此mac在数据库中不存在");
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/getalllocationnode" })
    public List<LocateNode> getAllLocationNode(HttpServletRequest request){
        List<LocateNode> nodeList = locateNodeService.findAllNode();
        return nodeList;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/savelocationnode" })
    public MessageInfo saveLocationNode(HttpServletRequest request){
        try {
            String jsonData = HttpUtils.getStringFromHttpRequest(request);
            JSONArray jsonArray = new JSONArray(jsonData);
            for (Object object : jsonArray) {
                JSONObject jsonObj = (JSONObject) object;
                String mac = jsonObj.getString("mac");
                String name = jsonObj.getString("name");
                String nodeTop = jsonObj.getString("nodeTop");
                String nodeLeft = jsonObj.getString("nodeLeft");
                if(!locateNodeService.checkNodeExist(mac)){
                    locateNodeService.saveNode(mac, name, nodeTop, nodeLeft);
                }else{
                    locateNodeService.updateNodeLocation(mac, nodeTop, nodeLeft);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return new MessageInfo(1,"服务器错误...");
        }
        return new MessageInfo(0,"success");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/deletelocationnode" })
    public MessageInfo deleteLocationNode(HttpServletRequest request){
        String mac = request.getParameter("mac");
        if(locateNodeService.deleteNodeByMac(mac)){
            return new MessageInfo(0,"success");
        }else{
            return new MessageInfo(1,"删除节点失败...此mac在数据库中不存在");
        }
    }

}
