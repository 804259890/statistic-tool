package com.platon.statistic.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NodeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andBlockNumberIsNull() {
            addCriterion("block_number is null");
            return (Criteria) this;
        }

        public Criteria andBlockNumberIsNotNull() {
            addCriterion("block_number is not null");
            return (Criteria) this;
        }

        public Criteria andBlockNumberEqualTo(Long value) {
            addCriterion("block_number =", value, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberNotEqualTo(Long value) {
            addCriterion("block_number <>", value, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberGreaterThan(Long value) {
            addCriterion("block_number >", value, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberGreaterThanOrEqualTo(Long value) {
            addCriterion("block_number >=", value, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberLessThan(Long value) {
            addCriterion("block_number <", value, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberLessThanOrEqualTo(Long value) {
            addCriterion("block_number <=", value, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberIn(List<Long> values) {
            addCriterion("block_number in", values, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberNotIn(List<Long> values) {
            addCriterion("block_number not in", values, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberBetween(Long value1, Long value2) {
            addCriterion("block_number between", value1, value2, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andBlockNumberNotBetween(Long value1, Long value2) {
            addCriterion("block_number not between", value1, value2, "blockNumber");
            return (Criteria) this;
        }

        public Criteria andNodeIdIsNull() {
            addCriterion("node_id is null");
            return (Criteria) this;
        }

        public Criteria andNodeIdIsNotNull() {
            addCriterion("node_id is not null");
            return (Criteria) this;
        }

        public Criteria andNodeIdEqualTo(String value) {
            addCriterion("node_id =", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotEqualTo(String value) {
            addCriterion("node_id <>", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThan(String value) {
            addCriterion("node_id >", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdGreaterThanOrEqualTo(String value) {
            addCriterion("node_id >=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThan(String value) {
            addCriterion("node_id <", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLessThanOrEqualTo(String value) {
            addCriterion("node_id <=", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdLike(String value) {
            addCriterion("node_id like", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotLike(String value) {
            addCriterion("node_id not like", value, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdIn(List<String> values) {
            addCriterion("node_id in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotIn(List<String> values) {
            addCriterion("node_id not in", values, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdBetween(String value1, String value2) {
            addCriterion("node_id between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andNodeIdNotBetween(String value1, String value2) {
            addCriterion("node_id not between", value1, value2, "nodeId");
            return (Criteria) this;
        }

        public Criteria andSignCountIsNull() {
            addCriterion("sign_count is null");
            return (Criteria) this;
        }

        public Criteria andSignCountIsNotNull() {
            addCriterion("sign_count is not null");
            return (Criteria) this;
        }

        public Criteria andSignCountEqualTo(Long value) {
            addCriterion("sign_count =", value, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountNotEqualTo(Long value) {
            addCriterion("sign_count <>", value, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountGreaterThan(Long value) {
            addCriterion("sign_count >", value, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountGreaterThanOrEqualTo(Long value) {
            addCriterion("sign_count >=", value, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountLessThan(Long value) {
            addCriterion("sign_count <", value, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountLessThanOrEqualTo(Long value) {
            addCriterion("sign_count <=", value, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountIn(List<Long> values) {
            addCriterion("sign_count in", values, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountNotIn(List<Long> values) {
            addCriterion("sign_count not in", values, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountBetween(Long value1, Long value2) {
            addCriterion("sign_count between", value1, value2, "signCount");
            return (Criteria) this;
        }

        public Criteria andSignCountNotBetween(Long value1, Long value2) {
            addCriterion("sign_count not between", value1, value2, "signCount");
            return (Criteria) this;
        }

        public Criteria andSettleEpochIsNull() {
            addCriterion("settle_epoch is null");
            return (Criteria) this;
        }

        public Criteria andSettleEpochIsNotNull() {
            addCriterion("settle_epoch is not null");
            return (Criteria) this;
        }

        public Criteria andSettleEpochEqualTo(Integer value) {
            addCriterion("settle_epoch =", value, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochNotEqualTo(Integer value) {
            addCriterion("settle_epoch <>", value, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochGreaterThan(Integer value) {
            addCriterion("settle_epoch >", value, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochGreaterThanOrEqualTo(Integer value) {
            addCriterion("settle_epoch >=", value, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochLessThan(Integer value) {
            addCriterion("settle_epoch <", value, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochLessThanOrEqualTo(Integer value) {
            addCriterion("settle_epoch <=", value, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochIn(List<Integer> values) {
            addCriterion("settle_epoch in", values, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochNotIn(List<Integer> values) {
            addCriterion("settle_epoch not in", values, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochBetween(Integer value1, Integer value2) {
            addCriterion("settle_epoch between", value1, value2, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andSettleEpochNotBetween(Integer value1, Integer value2) {
            addCriterion("settle_epoch not between", value1, value2, "settleEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochIsNull() {
            addCriterion("consensus_epoch is null");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochIsNotNull() {
            addCriterion("consensus_epoch is not null");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochEqualTo(Integer value) {
            addCriterion("consensus_epoch =", value, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochNotEqualTo(Integer value) {
            addCriterion("consensus_epoch <>", value, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochGreaterThan(Integer value) {
            addCriterion("consensus_epoch >", value, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochGreaterThanOrEqualTo(Integer value) {
            addCriterion("consensus_epoch >=", value, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochLessThan(Integer value) {
            addCriterion("consensus_epoch <", value, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochLessThanOrEqualTo(Integer value) {
            addCriterion("consensus_epoch <=", value, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochIn(List<Integer> values) {
            addCriterion("consensus_epoch in", values, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochNotIn(List<Integer> values) {
            addCriterion("consensus_epoch not in", values, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochBetween(Integer value1, Integer value2) {
            addCriterion("consensus_epoch between", value1, value2, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andConsensusEpochNotBetween(Integer value1, Integer value2) {
            addCriterion("consensus_epoch not between", value1, value2, "consensusEpoch");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeIsNull() {
            addCriterion("stat_validator_time is null");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeIsNotNull() {
            addCriterion("stat_validator_time is not null");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeEqualTo(Integer value) {
            addCriterion("stat_validator_time =", value, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeNotEqualTo(Integer value) {
            addCriterion("stat_validator_time <>", value, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeGreaterThan(Integer value) {
            addCriterion("stat_validator_time >", value, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("stat_validator_time >=", value, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeLessThan(Integer value) {
            addCriterion("stat_validator_time <", value, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeLessThanOrEqualTo(Integer value) {
            addCriterion("stat_validator_time <=", value, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeIn(List<Integer> values) {
            addCriterion("stat_validator_time in", values, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeNotIn(List<Integer> values) {
            addCriterion("stat_validator_time not in", values, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeBetween(Integer value1, Integer value2) {
            addCriterion("stat_validator_time between", value1, value2, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andStatValidatorTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("stat_validator_time not between", value1, value2, "statValidatorTime");
            return (Criteria) this;
        }

        public Criteria andSignRateIsNull() {
            addCriterion("sign_rate is null");
            return (Criteria) this;
        }

        public Criteria andSignRateIsNotNull() {
            addCriterion("sign_rate is not null");
            return (Criteria) this;
        }

        public Criteria andSignRateEqualTo(Double value) {
            addCriterion("sign_rate =", value, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateNotEqualTo(Double value) {
            addCriterion("sign_rate <>", value, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateGreaterThan(Double value) {
            addCriterion("sign_rate >", value, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateGreaterThanOrEqualTo(Double value) {
            addCriterion("sign_rate >=", value, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateLessThan(Double value) {
            addCriterion("sign_rate <", value, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateLessThanOrEqualTo(Double value) {
            addCriterion("sign_rate <=", value, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateIn(List<Double> values) {
            addCriterion("sign_rate in", values, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateNotIn(List<Double> values) {
            addCriterion("sign_rate not in", values, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateBetween(Double value1, Double value2) {
            addCriterion("sign_rate between", value1, value2, "signRate");
            return (Criteria) this;
        }

        public Criteria andSignRateNotBetween(Double value1, Double value2) {
            addCriterion("sign_rate not between", value1, value2, "signRate");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampIsNull() {
            addCriterion("block_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampIsNotNull() {
            addCriterion("block_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampEqualTo(Long value) {
            addCriterion("block_timestamp =", value, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampNotEqualTo(Long value) {
            addCriterion("block_timestamp <>", value, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampGreaterThan(Long value) {
            addCriterion("block_timestamp >", value, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("block_timestamp >=", value, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampLessThan(Long value) {
            addCriterion("block_timestamp <", value, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampLessThanOrEqualTo(Long value) {
            addCriterion("block_timestamp <=", value, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampIn(List<Long> values) {
            addCriterion("block_timestamp in", values, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampNotIn(List<Long> values) {
            addCriterion("block_timestamp not in", values, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampBetween(Long value1, Long value2) {
            addCriterion("block_timestamp between", value1, value2, "blockTimestamp");
            return (Criteria) this;
        }

        public Criteria andBlockTimestampNotBetween(Long value1, Long value2) {
            addCriterion("block_timestamp not between", value1, value2, "blockTimestamp");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}