package com.ibeacon.web.controller;

import com.ibeacon.model.msginfo.MessageInfo;
import com.ibeacon.service.label.LabelService;
import com.ibeacon.service.node.LocateReNodeService;
import com.ibeacon.service.node.ReNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册控制器
 * Created by zz on 2017/8/4.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private ReNodeService reNodeService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private LocateReNodeService locateReNodeService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/addnode" })
    public MessageInfo addReNode(HttpServletRequest request){
        String mac = request.getParameter("mac");
        String macName = request.getParameter("macName");
        if(reNodeService.saveReNode(mac, macName)){
            return new MessageInfo(0, "success");
        }
        return new MessageInfo(1, "failed");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/addlocationnode" })
    public MessageInfo addLocationReNode(HttpServletRequest request){
        String mac = request.getParameter("mac");
        String macName = request.getParameter("macName");
        if(locateReNodeService.saveReNode(mac, macName)){
            return new MessageInfo(0, "success");
        }
        return new MessageInfo(1, "failed");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/addlabel" })
    public MessageInfo addLabel(HttpServletRequest request){
        String uuid = request.getParameter("uuid");
        String uuidName = request.getParameter("uuidName");
        String type = request.getParameter("type");
        Integer typeint = Integer.parseInt(type);
        if(labelService.saveLabel(uuid, uuidName, typeint,"")){
            return new MessageInfo(0, "success");
        }
        return new MessageInfo(1, "failed");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/deletenode" })
    public MessageInfo deleteReNode(HttpServletRequest request){
        Long id = Long.valueOf(request.getParameter("id"));
        if(reNodeService.deleteReNode(id)){
            return new MessageInfo(0, "success");
        }
        return new MessageInfo(1, "failed");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/deletelocationnode" })
    public MessageInfo deleteLocationReNode(HttpServletRequest request){
        Long id = Long.valueOf(request.getParameter("id"));
        if(locateReNodeService.deleteReNode(id)){
            return new MessageInfo(0, "success");
        }
        return new MessageInfo(1, "failed");
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/deletelabel" })
    public MessageInfo deleteLabel(HttpServletRequest request){
        Long id = Long.valueOf(request.getParameter("id"));
        if(labelService.deleteLabel(id)){
            return new MessageInfo(0, "success");
        }
        return new MessageInfo(1, "failed");
    }
}
