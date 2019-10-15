package com.platon.statistic.cache;

import com.platon.statistic.bean.StatisticNode;
import com.platon.statistic.config.BlockChainConfig;
import com.platon.statistic.dao.entity.Node;
import com.platon.statistic.excption.NoSuchBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NodeCache {

    @Autowired
    private BlockChainConfig chainConfig;

    // <节点ID - 节点>
    private Map<String, StatisticNode> nodeMap = new HashMap<>();

    /**
     * 根据节点ID获取节点
     * @param nodeId
     * @return
     */
    public StatisticNode getNode(String nodeId) throws NoSuchBeanException {
        StatisticNode node = nodeMap.get(nodeId);
        if(node==null) throw new NoSuchBeanException("节点(id="+nodeId+")的节点不存在");
        return node;
    }

    /**
     * 添加节点，同时更新stakingSet和delegationSet全量缓存
     * @param node
     */
    public void addNode(StatisticNode node){
        nodeMap.put(node.getNodeId(),node);
    }

    private List<Node> exportData = new ArrayList<>();
    public List<Node> exportNodeList(){
        exportData.clear();
        nodeMap.values().forEach(sn->{
            sn.calcSignRate(chainConfig.getConsensusPeriodBlockCount().longValue());
            exportData.add(sn);
        });
        return exportData;
    }

    public void init(List<StatisticNode> nodes) {
        nodeMap.clear();
        nodes.forEach(this::addNode);
    }
}
