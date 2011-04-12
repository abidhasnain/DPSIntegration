// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.tesense.dpsintegration.service;

import com.tesense.dpsintegration.service.Node;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Node_Roo_Entity {
    
    declare @type: Node: @Entity;
    
    @PersistenceContext
    transient EntityManager Node.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Node.id;
    
    @Version
    @Column(name = "version")
    private Integer Node.version;
    
    public Node.new() {
        super();
    }

    public Long Node.getId() {
        return this.id;
    }
    
    public void Node.setId(Long id) {
        this.id = id;
    }
    
    public Integer Node.getVersion() {
        return this.version;
    }
    
    public void Node.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Node.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Node.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Node attached = Node.findNode(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Node.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Node.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Node Node.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Node merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Node.entityManager() {
        EntityManager em = new Node().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Node.countNodes() {
        return entityManager().createQuery("select count(o) from Node o", Long.class).getSingleResult();
    }
    
    public static List<Node> Node.findAllNodes() {
        return entityManager().createQuery("select o from Node o", Node.class).getResultList();
    }
    
    public static Node Node.findNode(Long id) {
        if (id == null) return null;
        return entityManager().find(Node.class, id);
    }
    
    public static List<Node> Node.findNodeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Node o", Node.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}