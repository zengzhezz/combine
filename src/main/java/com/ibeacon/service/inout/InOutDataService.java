package com.ibeacon.service.inout;

import com.ibeacon.model.inout.InOutData;
import com.ibeacon.service.base.AbstractService;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zz on 2017/8/3.
 */
@Service
public class InOutDataService extends AbstractService {

    /**
     * 保存InOutData
     * @param uuid
     * @param uuidName
     * @param mac
     * @param macName
     * @param state
     */
    public void saveInOutData(String uuid, String uuidName, String mac, String macName, String state, String lastUpdateTime){
        InOutData data = new InOutData(uuid, uuidName, mac, macName, state, lastUpdateTime);
        this.save(data);
    }


    /**
     * 查找最新的5条数据
     * @return
     */
    public List<InOutData> find5InOutData(){
        Query query = this.getSession().createQuery("from InOutData order by createTime desc");
        query.setLockOptions(LockOptions.UPGRADE);
        query.setMaxResults(5);
        return query.list();
    }

    /**
     * 根据uuid查找一段时间的数据
     * @param uuid
     * @param start
     * @param end
     * @return
     */
    public List<InOutData> findInOutDataByUuid(String uuid, String start, String end){
        return this.find("from InOutData where uuid = ? and lastUpdateTime between ? and " +
                "? order by lastUpdateTime asc", uuid, start, end);
    }

    /**
     * 根据mac查找一段时间的数据
     * @param mac
     * @param start
     * @param end
     * @return
     */
    public List<InOutData> findInOutDataByMac(String mac, String start, String end){
        return this.find("from InOutData where mac = ? and lastUpdateTime between ? and " +
                "? order by lastUpdateTime asc", mac, start, end);
    }

    //new
    public InOutData findOneInOutDataByUuid(String uuid){
        List<InOutData> list=  this.find("from InOutData where uuid = ? order by lastUpdateTime asc", uuid);
        return list.get(0);
    }

}
