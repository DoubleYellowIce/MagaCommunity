package com.DYI.android.Repair;

import com.contrarywind.interfaces.IPickerViewData;

public class EquipBean implements IPickerViewData {
    String des;

    public EquipBean( String des) {

        this.des = des;
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