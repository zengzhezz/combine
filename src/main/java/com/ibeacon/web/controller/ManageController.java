package com.ibeacon.web.controller;

import com.ibeacon.model.label.Label;
import com.ibeacon.model.node.LocateReNode;
import com.ibeacon.model.node.ReNode;
import com.ibeacon.service.label.LabelService;
import com.ibeacon.service.node.LocateReNodeService;
import com.ibeacon.service.node.ReNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 控制页面的跳转
 * Created by zz on 2017/8/3.
 */
@Controller
public class ManageController {

    @Autowired
    private ReNodeService reNodeService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private LocateReNodeService locateReNodeService;

    @RequestMapping(method = RequestMethod.GET, value = { "/", "index" })
    public String toIndex() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/register" })
    public String toRegister(Model model) {
        List<ReNode> reNodeList = reNodeService.findAll();
        List<Label> labelList = labelService.findAllLabel();
        List<LocateReNode> locateReNodeList = locateReNodeService.findAll();
        model.addAttribute("nodes", reNodeList);
        model.addAttribute("labels", labelList);
        model.addAttribute("locationNodes", locateReNodeList);
        return "register";
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/config" })
    public String toConfig(Model model){
        List<ReNode> reNodeList = reNodeService.findAll();
        List<LocateReNode> locateReNodeList = locateReNodeService.findAll();
        model.addAttribute("nodes", reNodeList);
        model.addAttribute("locationNodes", locateReNodeList);
        return "config";
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/history" })
    public String toHistory(Model model){
        List<ReNode> reNodeList = reNodeService.findAll();
        List<Label> labelList = labelService.findAllLabel();
        model.addAttribute("nodes", reNodeList);
        model.addAttribute("labels", labelList);
        return "history";
    }

}
