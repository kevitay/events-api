package com.galvanize.events;


import java.util.ArrayList;
import java.util.List;

public class ExtEventList {

    List<ExtEvent> extEventList;

    public ExtEventList() {
        this.extEventList = new ArrayList<>();
    }

    public ExtEventList(List<ExtEvent> extEventList) {
        this.extEventList = extEventList;
    }

    public void add(ExtEvent extEvent) {
        extEventList.add(extEvent);
    }

    public List<ExtEvent> getExtEventList() {
        return extEventList;
    }

    public void setExtEventList(List<ExtEvent> extEventList) {
        this.extEventList = extEventList;
    }
}
