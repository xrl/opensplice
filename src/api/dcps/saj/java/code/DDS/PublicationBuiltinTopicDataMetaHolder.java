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
package DDS;

public final class PublicationBuiltinTopicDataMetaHolder
{

    public static java.lang.String metaDescriptor[] = { "<MetaData version=\"1.0.0\"><Module name=\"DDS\"><TypeDef name=\"BuiltinTopicKey_t\"><Array size=\"3\"><Long/></Array></TypeDef><Enum name=\"DurabilityQosPolicyKind\"><Element name=\"VOLATILE_DURABILITY_QOS\"/><Element name=\"TRANSIENT_LOCAL_DURABILITY_QOS\"/><Element name=\"TRANSIENT_DURABILITY_QOS\"/><Element name=\"PERSISTENT_DURABILITY_QOS\"/></Enum><Struct name=\"DurabilityQosPolicy\"><Member name=\"kind\"><Type name=\"DDS::DurabilityQosPolicyKind\"/></Member></Struct><Struct name=\"Duration_t\"><Member name=\"sec\"><Long/></Member><Member name=\"nanosec\"><ULong/></Member></Struct><Struct name=\"DeadlineQosPolicy\"><Member name=\"period\"><Type name=\"DDS::Duration_t\"/></Member></Struct><Struct name=\"LatencyBudgetQosPolicy\"><Member name=\"duration\"><Type name=\"DDS::Duration_t\"/></Member></Struct><Enum name=\"LivelinessQosPolicyKind\"><Element name=\"AUTOMATIC_LIVELINESS_QOS\"/><Element name=\"MANUAL_BY_PARTICIPANT_LIVELINESS_QOS\"/><Element name=\"MANUAL_BY_TOPIC_LIVELINESS_QOS\"/></Enum><Struct name=\"LivelinessQosPolicy\"><Member name=\"kind\"><Type name=\"DDS::LivelinessQosPolicyKind\"/></Member><Member name=\"lease_duration\"><Type name=\"DDS::Duration_t\"/></Member></Struct><Enum name=\"ReliabilityQosPolicyKind\"><Element name=\"BEST_EFFORT_RELIABILITY_QOS\"/><Element name=\"RELIABLE_RELIABILITY_QOS\"/></Enum><Struct name=\"ReliabilityQosPolicy\"><Member name=\"kind\"><Type name=\"DDS::ReliabilityQosPolicyKind\"/></Member><Member name=\"max_blocking_time\"><Type name=\"DDS::Duration_t\"/></Member></Struct><Struct name=\"LifespanQosPolicy\"><Member name=\"duration\"><Type name=\"DDS::Duration_t\"/></Member></Struct><Struct name=\"UserDataQosPolicy\"><Member name=\"value\"><Sequence><Octet/></Sequence></Member></Struct><Enum name=\"OwnershipQosPolicyKind\"><Element name=\"SHARED_OWNERSHIP_QOS\"/><Element name=\"EXCLUSIVE_OWNERSHIP_QOS\"/></Enum><Struct name=\"OwnershipQosPolicy\"><Member name=\"kind\"><Type name=\"DDS::OwnershipQosPolicyKind\"/></Member></Struct><Struct name=\"OwnershipStrengthQosPolicy\"><Member name=\"value\"><Long/></Member></Struct><Enum name=\"PresentationQosPolicyAccessScopeKind\"><Element name=\"INSTANCE_PRESENTATION_QOS\"/><Element name=\"TOPIC_PRESENTATION_QOS\"/><Element name=\"GROUP_PRESENTATION_QOS\"/></Enum><Struct name=\"PresentationQosPolicy\"><Member name=\"access_scope\"><Type name=\"DDS::PresentationQosPolicyAccessScopeKind\"/></Member><Member name=\"coherent_access\"><Boolean/></Member><Member name=\"ordered_access\"><Boolean/></Member></Struct><TypeDef name=\"StringSeq\"><Sequence><String/></Sequence></TypeDef><Struct name=\"PartitionQosPolicy\"><Member name=\"name\"><Type name=\"DDS::StringSeq\"/></Member></Struct><Struct name=\"TopicDataQosPolicy\"><Member name=\"value\"><Sequence><Octet/></Sequence></Member></Struct><Struct name=\"GroupDataQosPolicy\"><Member name=\"value\"><Sequence><Octet/></Sequence></Member></Struct><Struct name=\"PublicationBuiltinTopicData\"><Member name=\"key\"><Type name=\"DDS::BuiltinTopicKey_t\"/></Member><Member name=\"participant_key\"><Type name=\"DDS::BuiltinTopicKey_t\"/></Member><Member name=\"topic_name\"><String/></Member><Member name=\"type_name\"><String/></Member><Member name=\"durability\"><Type name=\"DDS::DurabilityQosPolicy\"/></Member><Member name=\"deadline\"><Type name=\"DDS::DeadlineQosPolicy\"/></Member><Member name=\"latency_budget\"><Type name=\"DDS::LatencyBudgetQosPolicy\"/></Member><Member name=\"liveliness\"><Type name=\"DDS::LivelinessQosPolicy\"/></Member><Member name=\"reliability\"><Type name=\"DDS::ReliabilityQosPolicy\"/></Member><Member name=\"lifespan\"><Type name=\"DDS::LifespanQosPolicy\"/></Member><Member name=\"user_data\"><Type name=\"DDS::UserDataQosPolicy\"/></Member><Member name=\"ownership\"><Type name=\"DDS::OwnershipQosPolicy\"/></Member><Member name=\"ownership_strength\"><Type name=\"DDS::OwnershipStrengthQosPolicy\"/></Member><Member name=\"presentation\"><Type name=\"DDS::PresentationQosPolicy\"/></Member><Member name=\"partition\"><Type name=\"DDS::PartitionQosPolicy\"/></Member><Member name=\"topic_data\"><Type name=\"DDS::TopicDataQosPolicy\"/></Member><Member name=\"group_data\"><Type name=\"DDS::GroupDataQosPolicy\"/></Member></Struct></Module></MetaData>" };

    public PublicationBuiltinTopicDataMetaHolder()
    {
    }

}
