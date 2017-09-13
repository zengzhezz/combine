package com.ibeacon.service.node;

import com.ibeacon.model.node.LocateReNode;
import com.ibeacon.service.base.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zz on 2017/8/11.
 */
@Service
public class LocateReNodeService extends AbstractService{

    /**
     * 保存ReNode
     * @param mac
     * @param macName
     * @return true表示保存成功，false表示保存失败
     */
    public boolean saveReNode(String mac, String macName){
        if(checkReNodeExist(mac)){
            return false;
        }
        LocateReNode reNode = new LocateReNode(mac, macName);
        this.save(reNode);
        return true;
    }

    /**
     * 删除该id的renode，删除成功返回true，否则返回false
     * @param id
     * @return
     */
    public boolean deleteReNode(Long id){
        LocateReNode renode = findReNodeById(id);
        if(renode==null){
            return false;
        }
        this.delete(renode);
        return true;
    }

    /**
     * 根据id查找ReNode
     * @param id
     * @return
     */
    public LocateReNode findReNodeById(Long id){
        return this.findUnique("from LocateReNode where id = ?", id);
    }

    /**
     * 根据mac查找到对应的ReNode
     * @param mac
     * @return
     */
    public LocateReNode findReNodeByMac(String mac){
        return this.findUnique("from LocateReNode where mac = ?", mac);
    }

    /**
     * 查找所有的ReNode
     * @return
     */
    public List<LocateReNode> findAll(){
        return this.find("from LocateReNode");
    }

    /**
     * 检测此mac的ReNode是否存在
     * @param mac
     * @return
     */
    public boolean checkReNodeExist(String mac){
        LocateReNode renode = findReNodeByMac(mac);
        return renode != null;
    }

    /**
     * 检测该id的ReNode是否存在
     * @param id
     * @return
     */
    public boolean checkReNodeExist(Long id){
        LocateReNode renode = findReNodeById(id);
        return renode != null;
    }

}
