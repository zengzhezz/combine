package com.ibeacon.service.label;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ibeacon.model.label.Label;
import com.ibeacon.service.base.AbstractService;

@Service
public class LabelService extends AbstractService {

    /**
     * 保存Label
     * @param uuid
     * @param uuidName
     */
    public boolean saveLabel(String uuid, String uuidName, String description){
        if(checkLabelExist(uuid)){
            return false;
        }
        Label label = new Label();
        label.setUuid(uuid);
        label.setUuidName(uuidName);
        label.setDescription(description);
        this.save(label);
        return true;
    }

    /**
     * 根据uuid查找唯一的Label
     * @param uuid
     * @return
     */
    public Label findLabelByUuid(String uuid){
        return this.findUnique("from Label where uuid = ?", uuid);
    }

    /**
     * 根据id查找唯一的Label
     * @param id
     * @return
     */
    public Label findLabelById(Long id){
        return this.findUnique("from Label where id = ?", id);
    }

    /**
     * 查找改uuid的标签是否在数据库中已经存在
     * @param uuid
     * @return 已经存在返回true，否则返回fasle
     */
    public boolean checkLabelExist(String uuid){
        Label label = findLabelByUuid(uuid);
        return label != null;
    }

    /**
     * 删除标签
     * @param uuid
     * @return 删除成功返回true，失败返回false
     */
    public boolean deleteLabel(String uuid){
        Label label = findLabelByUuid(uuid);
        if(label!=null){
            this.delete(label);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除特定id
     * @param id
     * @return
     */
    public boolean deleteLabel(Long id){
        Label label = findLabelById(id);
        if(label!=null){
            this.delete(label);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查找数据库中所有的Label,按照uuid从小到大排序
     * @return
     */
    public List<Label> findAllLabel(){
        return this.find("from Label order by uuid asc");
    }

    /**
     * 根据uuid查找对应的uuidName
     * @param uuid
     * @return 找到返回uuidName，找不到返回""
     */
    public String findUuidNameByUuid(String uuid){
        Label label = this.findLabelByUuid(uuid);
        if(label!=null){
            return label.getUuidName();
        }
        return "";
    }

    /**
     * 根据uuid查找描述信息
     * @param uuid
     * @return
     */
    public String findDescriptionByUuid(String uuid){
        Label label = this.findLabelByUuid(uuid);
        if(label!=null){
            return label.getDescription();
        }
        return "";
    }

}
