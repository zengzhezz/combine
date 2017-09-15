package com.ibeacon.web.controller;

import com.ibeacon.model.inout.InOutData;
import com.ibeacon.model.label.Label;
import com.ibeacon.model.node.ReNode;
import com.ibeacon.service.inout.InOutDataService;
import com.ibeacon.service.label.LabelService;
import com.ibeacon.service.node.ReNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 进出信息控制器
 * Created by zz on 2017/8/4.
 */
@Controller
@RequestMapping("/inout")
public class InOutDataController {

    @Autowired
    private InOutDataService inOutDataService;

    @Autowired
    private ReNodeService reNodeService;

    @Autowired
    private LabelService labelService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/get5inoutdata" })
    public List<InOutData> find5Data(HttpServletRequest request){
        return inOutDataService.find5InOutData();
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/getdatabyuuid" })
    public String findDataByUuid(HttpServletRequest request, Model model){
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String uuid = request.getParameter("uuid");
        List<ReNode> reNodeList = reNodeService.findAll();
        List<Label> labelList = labelService.findAllLabel();
        List<InOutData> inOutlist = inOutDataService.findInOutDataByUuid(uuid, start, end);
        model.addAttribute("nodes", reNodeList);
        model.addAttribute("labels", labelList);
        model.addAttribute("inOutData", inOutlist);
        return "history";
    }

    @RequestMapping(method = RequestMethod.GET, value = { "/getdatabymac" })
    public String findDataByMac(HttpServletRequest request, Model model){
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        String mac = request.getParameter("mac");
        List<ReNode> reNodeList = reNodeService.findAll();
        List<Label> labelList = labelService.findAllLabel();
        List<InOutData> inOutlist = inOutDataService.findInOutDataByMac(mac, start, end);
        model.addAttribute("nodes", reNodeList);
        model.addAttribute("labels", labelList);
        model.addAttribute("inOutData", inOutlist);
        return "history";
    }
    //new
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = { "/get1inoutdata" })
    public InOutData find1Data(HttpServletRequest request){
        return inOutDataService.findOneInOutDataByUuid(request.getParameter("uuid"));
    }
}
