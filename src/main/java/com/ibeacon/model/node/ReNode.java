package com.ibeacon.model.node;

import com.ibeacon.model.base.IdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * 注册的节点，需要统计该节点下标签及其个数
 * Created by zz on 2017/8/3.
 */
@Entity
@Table(name="renode")
public class ReNode extends IdEntity {

    // 节点的mac
    @Column(name="mac")
    private String mac;

    // 节点的名称
    @Column(name="name")
    private String name;

    // 节点下所有uuid的集合
    @ElementCollection(fetch= FetchType.LAZY, //加载策略,延迟加载
            targetClass=String.class) //指定集合中元素的类型
    @CollectionTable(name="renode_uuid_list") //指定集合生成的表
    private List<String> uuidList = new ArrayList<String>();

    public ReNode() {

    }

    public ReNode(String mac, String name) {
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

    public List<String> getUuidList() {
        return uuidList;
    }

    public void setUuidList(List<String> uuidList) {
        this.uuidList = uuidList;
    }

    @Override
    public String toString() {
        return "ReNode{" +
                "mac='" + mac + '\'' +
                ", name='" + name + '\'' +
                ", uuidList=" + uuidList +
                '}';
    }
}
