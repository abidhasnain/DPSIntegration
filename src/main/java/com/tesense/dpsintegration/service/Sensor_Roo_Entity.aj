// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.tesense.dpsintegration.service;

import com.tesense.dpsintegration.service.Sensor;
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

privileged aspect Sensor_Roo_Entity {
    
    declare @type: Sensor: @Entity;
    
    @PersistenceContext
    transient EntityManager Sensor.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Sensor.id;
    
    @Version
    @Column(name = "version")
    private Integer Sensor.version;
    
    public Sensor.new() {
        super();
    }

    public Long Sensor.getId() {
        return this.id;
    }
    
    public void Sensor.setId(Long id) {
        this.id = id;
    }
    
    public Integer Sensor.getVersion() {
        return this.version;
    }
    
    public void Sensor.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void Sensor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Sensor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Sensor attached = Sensor.findSensor(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Sensor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Sensor.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Sensor Sensor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Sensor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager Sensor.entityManager() {
        EntityManager em = new Sensor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Sensor.countSensors() {
        return entityManager().createQuery("select count(o) from Sensor o", Long.class).getSingleResult();
    }
    
    public static List<Sensor> Sensor.findAllSensors() {
        return entityManager().createQuery("select o from Sensor o", Sensor.class).getResultList();
    }
    
    public static Sensor Sensor.findSensor(Long id) {
        if (id == null) return null;
        return entityManager().find(Sensor.class, id);
    }
    
    public static List<Sensor> Sensor.findSensorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("select o from Sensor o", Sensor.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
