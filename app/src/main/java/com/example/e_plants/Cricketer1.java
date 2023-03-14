package com.example.e_plants;

import java.io.Serializable;

public class Cricketer1 implements Serializable {
    public String addDate;
    public String diaryContent;

    public Cricketer1() {
    }

    public Cricketer1(String addDate, String diaryContent) {
        this.addDate = addDate;
        this.diaryContent = diaryContent;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String plantName) {
        this.addDate = plantName;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String dateName) {
        this.diaryContent = dateName;
    }
}
