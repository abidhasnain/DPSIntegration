package com.tesense.dpsintegration.service;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
public class Sensor {

    private long fillLevel;

    @ManyToOne
    private Node node;

    @NotNull
    private String name;

    public Sensor(String name, long fillLevel, Node node) {
    	this.name = name;
        this.fillLevel = fillLevel;
        this.node = node;
    }

    public long getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(long fillLevel) {
        this.fillLevel = fillLevel;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
