package com.ibeacon.web.controller;

import com.ibeacon.model.node.Node;
import com.ibeacon.model.node.ReNode;
import com.ibeacon.service.label.LabelService;
import com.ibeacon.service.node.NodeService;
import com.ibeacon.service.node.ReNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签数据控制器
 * Created by zz on 2017/8/4.
 */
@Controller
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private ReNodeService reNodeService;

    @Autowired
    private LabelService labelService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/getallnodelabel" })
    public List<Map<String, String>> getAllNodeLabel(){
        List<Node> nodeList = nodeService.findAllNode();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (Node node : nodeList){
            ReNode renode = reNodeService.findReNodeByMac(node.getMac());
            Map<String, String> map = new HashMap<String, String>();
            map.put("mac", renode.getMac());
            map.put("number", String.valueOf(renode.getUuidList().size()));
            map.put("uuidNameString", getUuidNameStringFromList(renode.getUuidList()));
            list.add(map);
        }
        return list;
    }

    /**
     * 根据list生成uuidString
     * @param list
     * @return
     */
    public String getUuidNameStringFromList(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String uuid: list) {
            String uuidName = labelService.findUuidNameByUuid(uuid);
            sb.append(uuidName + ",");
        }
        if(sb.length() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
