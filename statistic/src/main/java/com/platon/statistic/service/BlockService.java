package com.platon.statistic.service;

import com.platon.statistic.arg.Params;
import com.platon.statistic.bean.StatisticNode;
import com.platon.statistic.cache.NodeCache;
import com.platon.statistic.dao.mapper.CustomNodeMapper;
import com.platon.statistic.excption.CandidateException;
import com.platon.statistic.util.PlatOnClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.PlatonBlock;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Auther: Chendongming
 * @Date: 2019/9/6 13:34
 * @Description: 区块采集服务
 */
@Slf4j
@Service
public class BlockService {
    @Autowired
    private PlatOnClient client;
    @Autowired
    private StatisticService statisticService;

    @Autowired
    private CustomNodeMapper customNodeMapper;

    @Autowired
    private NodeCache nodeCache;

    private ExecutorService executor;

    // 并发采集的块信息，无序
    private final static Map<Long, PlatonBlock.Block> BLOCK_MAP = new ConcurrentHashMap<>();
    // 由于异常而未采集的区块号列表
    private final static Set<BigInteger> RETRY_NUMBER = new CopyOnWriteArraySet<>();
    List <PlatonBlock.Block> getSortedBlocks() {
        List<PlatonBlock.Block> sortedBlock = new LinkedList<>(BLOCK_MAP.values());
        sortedBlock.sort(Comparator.comparing(PlatonBlock.Block::getNumber));
        return sortedBlock;
    }
    private void reset() {
        BLOCK_MAP.clear();
        RETRY_NUMBER.clear();
    }

    // 已采集入库的最高块
    private long commitBlockNumber = 0;

    // 每一批次采集区块的数量
    @Value("${platon.web3j.collect.batch-size}")
    private int collectBatchSize;

    public void init(Params param) throws CandidateException, InterruptedException {
        statisticService.setNeedInit(true);
        statisticService.init(param);
        statisticService.epochChange(commitBlockNumber);
    }

    @PostConstruct
    private void init() {
        executor= Executors.newFixedThreadPool(collectBatchSize);
        BigInteger maxBlockNumber = customNodeMapper.selectMaxNumber();
        commitBlockNumber = maxBlockNumber.longValue();
        List<StatisticNode> nodes = customNodeMapper.selectLatestConsensusNode();
        nodeCache.init(nodes);
    }

    /**
     * 收集区块
     * @return
     * @throws Exception
     */
    public boolean collect() throws Exception {
        // 从(已采最高区块号+1)开始构造连续的指定数量的待采区块号列表
        Set<BigInteger> blockNumbers = new HashSet<>();
        // 当前链上最新区块号
        BigInteger curChainBlockNumber=getLatestNumber();
        long blockNumber=commitBlockNumber+1;
        while (blockNumber<=(commitBlockNumber+collectBatchSize)) {
            // 如果块号>当前链上块号,则不再累加
            if(blockNumber>curChainBlockNumber.longValue()) break;
            blockNumbers.add(BigInteger.valueOf(blockNumber));
            blockNumber++;
        }
        if (!blockNumbers.isEmpty()) {
            // 并行采块 ξξξξξξξξξξξξξξξξξξξξξξξξξξξ
            // 采集前先重置结果容器
            // 开始并行采集
            List<PlatonBlock.Block> blocks = collect(blockNumbers);
            if (!blocks.isEmpty()){
                // 串行分析 ξξξξξξξξξξξξξξξξξξξξξξξξξξξ
                List<BigInteger> numbers = new ArrayList<>(blockNumbers);
                Collections.sort(numbers);
                log.debug("collected blocks: {}", numbers);
                for (PlatonBlock.Block block:blocks){
                    statisticService.analyze(block);
                }
                // 更新已采最高区块号
                commitBlockNumber = blocks.get(blocks.size()-1).getNumber().longValue();
                TimeUnit.SECONDS.sleep(1);
            }
        } else {
            log.info("当前链最高块({}),等待下一批块...",curChainBlockNumber);
            TimeUnit.SECONDS.sleep(1);
        }
        return true;
    }


    /**
     * 并行采集区块及交易，并转换为数据库结构
     * @param blockNumbers 批量采集的区块号
     * @return void
     */
    public List<PlatonBlock.Block> collect(Set<BigInteger> blockNumbers) throws InterruptedException {
        reset();
        // 把待采块放入重试列表，当作重试块号操作
        RETRY_NUMBER.addAll(blockNumbers);
        // 记录每次重试出异常的块号，方便放入下次重试
        Set<BigInteger> exceptionNumbers = new CopyOnWriteArraySet<>();
        while (!RETRY_NUMBER.isEmpty()){
            exceptionNumbers.clear();
            // 并行批量采集区块
            CountDownLatch latch = new CountDownLatch(RETRY_NUMBER.size());
            RETRY_NUMBER.forEach(blockNumber->
                executor.submit(()->{
                    try {
                        PlatonBlock.Block block = getBlock(blockNumber);
                        BLOCK_MAP.put(blockNumber.longValue(),block);
                    } catch (Exception e) {
                        log.error("搜集区块[{}]异常,加入重试列表",blockNumber,e);
                        // 把出现异常的区块号加入异常块号列表
                        exceptionNumbers.add(blockNumber);
                    }finally {
                        latch.countDown();
                    }
                })
            );
            latch.await();
            // 清空重试列表
            RETRY_NUMBER.clear();
            // 把本轮异常区块号加入重试列表
            RETRY_NUMBER.addAll(exceptionNumbers);
        }
        return getSortedBlocks();
    }

    /**
     * 调用RPC接口获取区块
     * @param blockNumber
     * @return
     */
    public PlatonBlock.Block getBlock(BigInteger blockNumber) throws InterruptedException {
        while (true) try {
            return client.getWeb3j().platonGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true).send().getBlock();
        } catch (Exception e) {
            log.error("搜集区块[{}]异常,将重试:", blockNumber, e);
            TimeUnit.SECONDS.sleep(1L);
        }
    }

    /**
     * 取链上当前块号
     * @return
     */
    public BigInteger getLatestNumber() throws InterruptedException {
        while (true) try {
            return client.getWeb3j().platonBlockNumber().send().getBlockNumber();
        } catch (IOException e) {
            log.error("取链上最新区块号失败,将重试{}:", e.getMessage());
            TimeUnit.SECONDS.sleep(1L);
        }
    }
}
