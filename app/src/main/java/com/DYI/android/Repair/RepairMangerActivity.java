package com.DYI.android.Repair;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.baronzhang.android.weather.R;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

public class RepairMangerActivity extends AppCompatActivity {
    //维修界面管理员活动
    private List<RepairRequestForm> RepairRequestForms=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_manger);
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addData();
        RepaireeRequestFormAdapter repaireeRequestFormAdapter=new RepaireeRequestFormAdapter(RepairMangerActivity.this,R.layout.repairee_form_item,RepairRequestForms);
        ListView listView=findViewById(R.id.list_view);
        listView.setAdapter(repaireeRequestFormAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RepairRequestForm repairRequestForm=RepairRequestForms.get(i);
                RepaireeRequsetFormRead.actionStart(RepairMangerActivity.this,
                        repairRequestForm.getPhoneNum(),
                        repairRequestForm.getAddress(),
                        repairRequestForm.getBrokenEquipment(),
                        repairRequestForm.getDetailDescription(),
                        repairRequestForm.getTime()
                );
            }
        });
    }
    public void addData(){
        List<RepairRequestForm> repairRequestForms= LitePal.findAll(RepairRequestForm.class);
        for (RepairRequestForm repairRequestForm:repairRequestForms){
            RepairRequestForms.add(repairRequestForm);
        }
    }
}
