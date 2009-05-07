/*
 *                         OpenSplice DDS
 *
 *   This software and documentation are Copyright 2006 to 2009 PrismTech 
 *   Limited and its licensees. All rights reserved. See file:
 *
 *                     $OSPL_HOME/LICENSE 
 *
 *   for full copyright notice and license terms. 
 *
 */
package org.opensplice.common.model.table.qos;

import org.opensplice.cm.CMException;
import org.opensplice.cm.Topic;
import org.opensplice.cm.qos.DeadlinePolicy;
import org.opensplice.cm.qos.DurabilityPolicy;
import org.opensplice.cm.qos.DurabilityServicePolicy;
import org.opensplice.cm.qos.HistoryPolicy;
import org.opensplice.cm.qos.LatencyPolicy;
import org.opensplice.cm.qos.LifespanPolicy;
import org.opensplice.cm.qos.LivelinessPolicy;
import org.opensplice.cm.qos.OrderbyPolicy;
import org.opensplice.cm.qos.OwnershipPolicy;
import org.opensplice.cm.qos.ReliabilityPolicy;
import org.opensplice.cm.qos.ResourcePolicy;
import org.opensplice.cm.qos.TopicDataPolicy;
import org.opensplice.cm.qos.TopicQoS;
import org.opensplice.cm.qos.TransportPolicy;
import org.opensplice.common.CommonException;

/**
 * Concrete implementation of the EntityQoSTableModel that is capable of
 * resolving and administrating the QoS of a Topic (TopicQoS). 
 * 
 * @date Jan 10, 2005 
 */
public class TopicQoSTableModel extends EntityQoSTableModel{

    /**
     * Constructs a new table model that holds the QoS of the supplied
     * Topic.
     *
     * @param _entity The Topic, which QoS must be administrated.
     * @throws CommonException Thrown when the Entity is not available (anymore)
     */
    public TopicQoSTableModel(Topic _entity) throws CommonException {
        super(_entity);
    }

    protected void init() {
        Object[] data = new Object[3];
        
        /*row 0*/
        data[0] = "TOPIC_DATA";
        data[1] = "value";
        data[2] = "N/A";
        this.addRow(data);
        
        /*row 1*/
        data[0] = "DURABILITY";
        data[1] = "kind";
        this.addRow(data);
        nonEditRows.add(new Integer(1));
        
        /*row 2*/
        data[0] = "DURABILITY_SERVICE";
        data[1] = "service_cleanup_delay";
        this.addRow(data);
        nonEditRows.add(new Integer(2));
        
        /*row 3*/
        data[1] = "history_kind";
        this.addRow(data);
        nonEditRows.add(new Integer(3));
        
        /*row 4*/
        data[1] = "history_depth";
        this.addRow(data);
        nonEditRows.add(new Integer(4));
        
        /*row 5*/
        data[1] = "max_samples";
        this.addRow(data);
        nonEditRows.add(new Integer(5));
        
        /*row 6*/
        data[1] = "max_instances";
        this.addRow(data);
        nonEditRows.add(new Integer(6));
        
        /*row 7*/
        data[1] = "max_samples_per_instance";
        this.addRow(data);
        nonEditRows.add(new Integer(7));
        
        /*row 8*/
        data[0] = "DEADLINE";
        data[1] = "period";
        this.addRow(data);
        
        /*row 9*/
        data[0] = "LATENCY_BUDGET";
        data[1] = "duration";
        this.addRow(data);
        
        /*row 10*/
        data[0] = "LIVELINESS";
        data[1] = "kind";
        this.addRow(data);
        nonEditRows.add(new Integer(10));
        
        /*row 11*/
        data[1] = "lease_duration";
        this.addRow(data);
        nonEditRows.add(new Integer(11));
        
        /*row 12*/
        data[0] = "RELIABILITY";
        data[1] = "kind";
        this.addRow(data);
        nonEditRows.add(new Integer(12));
        
        /*row 13*/
        data[1] = "max_blocking_time";
        this.addRow(data);
        nonEditRows.add(new Integer(13)); 
        
        /*row 14*/
        data[0] = "DESTINATION_ORDER";
        data[1] = "kind";
        this.addRow(data);
        nonEditRows.add(new Integer(14));
        
        /*row 15*/
        data[0] = "HISTORY";
        data[1] = "kind";
        this.addRow(data);
        nonEditRows.add(new Integer(15));
        
        /*row 16*/
        data[1] = "depth";
        this.addRow(data);
        nonEditRows.add(new Integer(16));
        
        /*row 17*/
        data[0] = "RESOURCE_LIMITS";
        data[1] = "max_samples";
        this.addRow(data);
        nonEditRows.add(new Integer(17));
        
        /*row 18*/
        data[1] = "max_instances";
        this.addRow(data);
        nonEditRows.add(new Integer(18));
        
        /*row 19*/
        data[1] = "max_samples_per_instance";
        this.addRow(data);
        nonEditRows.add(new Integer(19));
        
        /*row 20*/
        data[0] = "TRANSPORT_PRIORITY";
        data[1] = "value";
        this.addRow(data);
        
        /*row 21*/
        data[0] = "LIFESPAN";
        data[1] = "duration";
        this.addRow(data);
        
        /*row 22*/
        data[0] = "OWNERSHIP";
        data[1] = "kind";
        this.addRow(data);
        nonEditRows.add(new Integer(22));
    }

    public boolean update() {
        boolean result;
        Object nill = "null";
        
        this.cancelEditing();
        
        try {
            TopicQoS qos = (TopicQoS)entity.getQoS();
            currentQos = qos;
            TopicDataPolicy tdp = qos.getTopicData();
            int row = 0;
            
            if(tdp != null){
                this.setValueAt(tdp, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            DurabilityPolicy dbp = qos.getDurability();
            
            if(dbp != null){
                this.setValueAt(dbp.kind, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            DurabilityServicePolicy dsp = qos.getDurabilityService();
            
            if(dsp != null){
                this.setValueAt(dsp.service_cleanup_delay, row++, 2);
                this.setValueAt(dsp.history_kind, row++, 2);
                this.setValueAt(new Integer(dsp.history_depth), row++, 2);
                this.setValueAt(new Integer(dsp.max_samples), row++, 2);
                this.setValueAt(new Integer(dsp.max_instances), row++, 2);
                this.setValueAt(new Integer(dsp.max_samples_per_instance), row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
            }
            DeadlinePolicy dlp = qos.getDeadline();
            
            if(dlp != null){
                this.setValueAt(dlp.period, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            LatencyPolicy ltp = qos.getLatency();
            
            if(ltp != null){
                this.setValueAt(ltp.duration, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            LivelinessPolicy llp = qos.getLiveliness();
            
            if(llp != null){
                this.setValueAt(llp.kind, row++, 2);
                this.setValueAt(llp.lease_duration, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
            }
            ReliabilityPolicy rlp = qos.getReliability();
            
            if(rlp != null){
                this.setValueAt(rlp.kind, row++, 2);
                this.setValueAt(rlp.max_blocking_time, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
            }
            OrderbyPolicy obp = qos.getOrderby();
            
            if(obp != null){
                this.setValueAt(obp.kind, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            HistoryPolicy hsp = qos.getHistory();
            
            if(hsp != null){
                this.setValueAt(hsp.kind, row++, 2);
                this.setValueAt(new Integer(hsp.depth), row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
            }
            ResourcePolicy rsp = qos.getResource();
            
            if(rsp != null){
                this.setValueAt(new Integer(rsp.max_samples), row++, 2);
                this.setValueAt(new Integer(rsp.max_instances), row++, 2);
                this.setValueAt(new Integer(rsp.max_samples_per_instance), row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
                this.setValueAt(nill, row++, 2);
            }
            TransportPolicy tpp = qos.getTransport();
            
            if(tpp != null){
                this.setValueAt(new Integer(tpp.value), row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            LifespanPolicy lsp = qos.getLifespan();
            
            if(lsp != null){
                this.setValueAt(lsp.duration, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }
            OwnershipPolicy osp = qos.getOwnership();
            
            if(osp != null){
                this.setValueAt(osp.kind, row++, 2);
            } else {
                this.setValueAt(nill, row++, 2);
            }

            assert row == this.getRowCount(): "#rows does not match filled rows.";
            
            result = true;
        } catch (CMException e) {
            result = false;
        }
        return result;
    }

}
