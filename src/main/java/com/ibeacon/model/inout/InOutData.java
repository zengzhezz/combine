package com.ibeacon.model.inout;

import com.ibeacon.model.base.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 保存进出信息
 * Created by zz on 2017/8/3.
 */
@Entity
@Table(name="inout_data")
public class InOutData extends IdEntity{

    // uuid标志
    @Column(name="uuid")
    private String uuid;

    // 该uuid对应的uuidName
    @Column(name="uuid_name")
    private String uuidName;

    // 进出的mac
    @Column(name="mac")
    private String mac;

    // 进出mac的名称
    @Column(name="mac_name")
    private String macName;

    // 进出状态, 0表示无进出，1表示进入，2表示离开
    @Column(name="state")
    private String state;

    // 最后更新事件
    @Column(name="last_update_time")
    private String lastUpdateTime;

    public InOutData() {
    }

    public InOutData(String uuid, String uuidName, String mac, String macName, String state, String lastUpdateTime) {
        this.uuid = uuid;
        this.uuidName = uuidName;
        this.mac = mac;
        this.macName = macName;
        this.state = state;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidName() {
        return uuidName;
    }

    public void setUuidName(String uuidName) {
        this.uuidName = uuidName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMacName() {
        return macName;
    }

    public void setMacName(String macName) {
        this.macName = macName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "InOutData{" +
                "uuid='" + uuid + '\'' +
                ", uuidName='" + uuidName + '\'' +
                ", mac='" + mac + '\'' +
                ", macName='" + macName + '\'' +
                ", state=" + state +
                '}';
    }
}
