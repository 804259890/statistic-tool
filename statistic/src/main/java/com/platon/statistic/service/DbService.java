package com.platon.statistic.service;


import com.platon.statistic.cache.NodeCache;
import com.platon.statistic.dao.entity.Node;
import com.platon.statistic.dao.mapper.CustomNodeMapper;
import com.platon.statistic.dao.mapper.NodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @Auther: dongqile
 * @Date: 2019/8/17 20:09
 * @Description: 批量入库服务
 */
@Component
public class DbService {
    private static Logger logger = LoggerFactory.getLogger(DbService.class);

    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private CustomNodeMapper customNodeMapper;
    @Autowired
    private NodeCache nodeCache;

    @Transactional
    public void insertOrUpdate () {
    	List<Node> nodes =  nodeCache.exportNodeList();

    	if(nodes.size()>0) customNodeMapper.batchInsertOrUpdateSelective(nodes,Node.Column.values());

    }
}
