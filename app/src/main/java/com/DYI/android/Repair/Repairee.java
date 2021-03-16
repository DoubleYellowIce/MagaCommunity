package com.DYI.android.Repair;

public class Repairee {

    private String name;

    private int imageId;

    private String skill;
    public Repairee(int imageId,String name, String skill) {
        this.name = name;
        this.imageId = imageId;
        this.skill=skill;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
    public String getSkill(){
        return skill;
    }
}
