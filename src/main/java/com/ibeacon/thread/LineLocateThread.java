package com.ibeacon.thread;

import com.ibeacon.model.beacon.BeaconModel;
import com.ibeacon.model.beacon.OriginBeaconModel;
import com.ibeacon.model.beacon.WeightBeaconModel;
import com.ibeacon.model.location.LocationModel;
import com.ibeacon.model.node.LocateNode;
import com.ibeacon.model.variables.StaticVariables;
import com.ibeacon.service.beacon.ShowBeaconService;
import com.ibeacon.service.label.LabelService;
import com.ibeacon.service.node.LocateNodeService;
import com.ibeacon.utils.Algorithm;
import com.ibeacon.utils.Constants;
import com.ibeacon.utils.SpringContextHolder;
import com.ibeacon.web.websocket.Websocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 两点直线定位算法
 * Created by zz on 2017/8/11.
 */
public class LineLocateThread extends Thread{

    private static Logger log = LogManager.getLogger("LineLocateThread");
    // 记录当前时间
    private Date this_date;

    @Override
    public void run() {
        while (true) {
            try {
                // 休眠5s
                Thread.sleep(6000);
                log.debug("start thread...");
                Websocket.sendMessageToAll("delete_all");
                // 选取当前系统时间作为lastUpdateTime
                this_date = new Date();
                if (!CollectionUtils.isEmpty(StaticVariables.originalBeaconList)) {
                    log.debug("originalBeaconList接收到数据，现在写入数据到weightHandledList...");
                    for (OriginBeaconModel model : StaticVariables.originalBeaconList) {
                        String uuid = model.getUuid();
                        String uuidName = model.getUuidName();
                        String mac = model.getMac();
                        String rssi = model.getRssi();
                        updateMsgToHandledList(uuid, uuidName, mac, rssi, Constants.needSend.NEED,
                                this_date.getTime());
                    }
                }else{
                    log.debug("originalBeaconList无数据...");
                }
                if (!CollectionUtils.isEmpty(StaticVariables.weightHandledList)) {
                    log.debug(StaticVariables.weightHandledList);
                    for (WeightBeaconModel model : StaticVariables.weightHandledList) {
                        Locate locate = getLocation(model);
                        if(locate!=null){
                            DecimalFormat df = new DecimalFormat("0.0000");
                            // 发送位置数据到前端
                            Integer labelType = SpringContextHolder.getBean(LabelService.class).findLabelTypeByUuid(model.getUuid());
                            String description = SpringContextHolder.getBean(LabelService.class).findDescriptionByUuid(model.getUuid());
                            //sb.append(model.getUuid() + "," + model.getUuidName() + ","  + labelType + "," + description + ",");
                            Websocket.sendMessageToAll("location,"+model.getUuid()+","+model.getUuidName()+ ","  + labelType + "," + "nothing"+
                                    "," + df.format(locate.getxAxis())+
                                    "," + df.format(locate.getyAxis()));
                            // 保存位置信息
                            ShowBeaconService showBeaconService = SpringContextHolder
                                    .getBean(ShowBeaconService.class);
                            showBeaconService.saveShowBeacon(model.getUuid(),
                                    model.getUuidName(), df.format(locate.getxAxis()),
                                    df.format(locate.getyAxis()));
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                log.error(e);
            }finally{
                clear();
                log.debug("end thread...");
            }
        }
    }

    /**
     * 清除缓存list
     */
    private void clear() {
        StaticVariables.originalBeaconList.clear();
        StaticVariables.weightHandledList.clear();
    }

    /**
     * 根据WeightBeaconModel得到uuid的位置坐标
     * @param model
     * @return
     */
    private Locate getLocation(WeightBeaconModel model) {
        // 定义最后输出的坐标信息
        Locate locate = new Locate();
        // 得到一共有多少节点收到此标签的信息
        int numNodes = model.getLocationList().size();
        // 如果数目小于2，无法定位
        if(numNodes<2){
            return null;
        }
        // 得到model中的所有Location的拷贝
        List<LocationModel> lmList = new ArrayList<>();
        lmList.addAll(model.getLocationList());
        Collections.sort(lmList);
        // TODO
        log.debug(lmList);
        double[] tempLocation = calculate(model.getUuid(), lmList);
        locate.setxAxis(tempLocation[0]);
        locate.setyAxis(tempLocation[1]);
        return locate;
    }

    /**
     * 根据节点以及距离的信息，算出坐标
     * @param lm
     * @return
     */
    public double[] calculate(String uuid, List<LocationModel> lm){
        /*基站的mac与坐标, double[0]表示x, double[1]表示y*/
        final Map<String, double[]> basesLocation =new HashMap<String, double[]>();
        /*距离数组*/
        double[] distanceArray = new double[2];
        String[] macs = new String[2];
        for(int i = 0; i < 2; i++){
            LocationModel model = lm.get(i);
            macs[i] = model.getMac();
            distanceArray[i] = Algorithm.calDistanceWithRssi(model.getRssi());
        }
        LocateNodeService nodeService = SpringContextHolder.getBean(LocateNodeService.class);
        for(int i = 0; i < macs.length; i++){
            LocateNode node = nodeService.findNodeByMac(macs[i]);
            double[] loc = new double[2];
            loc[0] = Double.valueOf(node.getNodeLeft());
            loc[1] = Double.valueOf(node.getNodeTop());
            basesLocation.put(macs[i], loc);
        }
        double[] point = new double[2];
        point[0] = basesLocation.get(macs[0])[0] + (distanceArray[0] / (distanceArray[0]+distanceArray[1])) *
                (basesLocation.get(macs[1])[0] - basesLocation.get(macs[0])[0]);
        point[1] = basesLocation.get(macs[0])[1] + (distanceArray[0] / (distanceArray[0]+distanceArray[1])) *
                (basesLocation.get(macs[1])[1] - basesLocation.get(macs[0])[1]);
        return point;
    }

    /**
     * 根据originList里的数据更新HandedList
     * @param uuid
     * @param uuidName
     * @param mac
     * @param rssi
     * @param need
     * @param time
     */
    private void updateMsgToHandledList(String uuid, String uuidName, String mac, String rssi, int need, long time) {
        // the flag where this uuid's message is already in handledList.
        boolean isExist = false;
        // check if handedList already has the uuid message. if no, add it ;
        // otherwise update it if rssi is smaller.
        for (WeightBeaconModel model : StaticVariables.weightHandledList) {
            if (model.getUuid().equals(uuid)){
                isExist = true;
                model.setUuidName(uuidName);
                model.setLastUpdateTime(time);
                model.setNeed_send(need);
                boolean isExstMac = false;
                // check if LocationList always has the message,
                // if yes then update to the smaller rssi, otherwise add a new LocationModel
                for(LocationModel beaconModel: model.getLocationList()){
                    if(beaconModel.getMac().equals(mac)){
                        isExstMac = true;
                        if(Integer.valueOf(rssi) < beaconModel.getRssi()){
                            beaconModel.setRssi(Integer.valueOf(rssi));
                        }
                    }
                }
                if(!isExstMac){
                    model.getLocationList().add(new LocationModel(mac, Integer.valueOf(rssi)));
                }
            }
        }
        if(!isExist){
            WeightBeaconModel weightModel = new WeightBeaconModel();
            weightModel.setUuid(uuid);
            weightModel.setUuidName(uuidName);
            weightModel.setLastUpdateTime(time);
            weightModel.setNeed_send(need);
            List<LocationModel> list = new ArrayList<LocationModel>();
            list.add(new LocationModel(mac,Integer.valueOf(rssi)));
            weightModel.setLocationList(list);
            StaticVariables.weightHandledList.add(weightModel);
        }
    }

    /**
     * 内部类，定义位置对象，具有x和y属性
     * @author zz
     * @version 1.0 2017年7月20日
     */
    private class Locate{

        private double xAxis;
        private double yAxis;
        public double getxAxis() {
            return xAxis;
        }
        public void setxAxis(double xAxis) {
            this.xAxis = xAxis;
        }
        public double getyAxis() {
            return yAxis;
        }
        public void setyAxis(double yAxis) {
            this.yAxis = yAxis;
        }

    }

}
