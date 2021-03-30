package com.DYI.android.Repair;

import com.contrarywind.interfaces.IPickerViewData;

public class EquipBean implements IPickerViewData {
    private String des;
    private long id;

    public EquipBean( long id,String des) {
        this.id=id;
        this.des = des;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String getPickerViewText() {
        return des;
    }

}