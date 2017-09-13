package com.ibeacon.model.node;

import com.ibeacon.model.base.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用于实时定位的页面显示节点
 * Created by zz on 2017/8/11.
 */
@Entity
@Table(name="locate_node")
public class LocateNode extends IdEntity {

    // 节点的mac
    @Column(name="mac")
    private String mac;

    // 节点的名称
    @Column(name="name")
    private String name;

    // 节点距离浏览器顶部的相对距离
    @Column(name="node_top")
    private String nodeTop;

    // 节点距离浏览器左边的相对距离
    @Column(name="node_left")
    private String nodeLeft;

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

    public String getNodeTop() {
        return nodeTop;
    }

    public void setNodeTop(String nodeTop) {
        this.nodeTop = nodeTop;
    }

    public String getNodeLeft() {
        return nodeLeft;
    }

    public void setNodeLeft(String nodeLeft) {
        this.nodeLeft = nodeLeft;
    }

}
