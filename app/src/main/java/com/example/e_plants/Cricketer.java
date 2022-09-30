package com.example.e_plants;

import java.io.Serializable;

public class Cricketer implements Serializable {
    public String plantName;
    public String dateName;
    public String extraName;

    public Cricketer() {
    }

    public Cricketer(String plantName, String dateName, String extraName) {
        this.plantName = plantName;
        this.dateName = dateName;
        this.extraName = extraName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public String getExtraName() {
        return extraName;
    }

    public void setExtraName(String extraName) {
        this.extraName = extraName;
    }
}
