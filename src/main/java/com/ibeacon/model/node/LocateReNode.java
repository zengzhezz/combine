package com.ibeacon.model.node;

import com.ibeacon.model.base.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用于实时定位的注册节点
 * Created by zz on 2017/8/11.
 */
@Entity
@Table(name="locate_renode")
public class LocateReNode extends IdEntity{

    @Column(name="mac")
    private String mac;

    @Column(name="name")
    private String name;

    public LocateReNode() {
    }

    public LocateReNode(String mac, String name) {
        this.mac = mac;
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LocateReNode{" +
                "mac='" + mac + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
