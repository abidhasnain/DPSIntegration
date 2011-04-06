package com.tesense.dpsintegration.service;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.tesense.dpsintegration.service.Node;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class NodeDetailForOrder {

    @ManyToOne
    private Node node;

    private int containerCount;
    
    public boolean equals(Object o) {
    	NodeDetailForOrder currentNode = (NodeDetailForOrder) o; 
    	return (this.node.getLocation().getLatitude().equals(currentNode.getNode().getLocation().getLatitude()) && 
    			this.node.getLocation().getLongitude().equals(currentNode.getNode().getLocation().getLongitude()));
	}
    
    public NodeDetailForOrder(Node node){
    	this.node = node;
    }

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getContainerCount() {
		return containerCount;
	}

	public void setContainerCount(int containerCount) {
		this.containerCount = containerCount;
	}
    
    
}
