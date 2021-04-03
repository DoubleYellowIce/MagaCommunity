package com.DYI.android.Repair;

import org.litepal.crud.LitePalSupport;

public class RepairRequestForm extends LitePalSupport {
    //表格数据
    private String phoneNum;
    private String repaireeName;
    private String brokenEquipment;
    private String address;
    private String DetailDescription;
    private String Time;
    private Boolean isUrgent=false;//是否紧急
    public void setPhoneNum(String  phoneNum){
        this.phoneNum=phoneNum;
    }
    public String getPhoneNum(){
        return phoneNum;
    }
    public void setRepaireeName(String  repaireeName){
        this.repaireeName=repaireeName;
    }
    public String getRepaireeName(){
        return repaireeName;
    }
    public void setBrokenEquipment(String brokenEquipment){
        this.brokenEquipment=brokenEquipment;
    }
    public String getBrokenEquipment(){
        return brokenEquipment;
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return address;
    }
    public void setDetailDescription(String detailDescription){
        this.DetailDescription=detailDescription;
    }
    public String getDetailDescription(){
        return DetailDescription;
    }
    public String getTime(){
        return Time;
    }
    public void  setTime(String time){
        this.Time=time;
    }
    public void  setIsUrgent(){
        this.isUrgent =true;
    }
    public Boolean  getIsUrgent(){
        return isUrgent;
    }
}
