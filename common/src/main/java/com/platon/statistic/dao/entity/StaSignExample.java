package com.platon.statistic.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StaSignExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StaSignExample() {
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

        public Criteria andSignNumberIsNull() {
            addCriterion("sign_number is null");
            return (Criteria) this;
        }

        public Criteria andSignNumberIsNotNull() {
            addCriterion("sign_number is not null");
            return (Criteria) this;
        }

        public Criteria andSignNumberEqualTo(Integer value) {
            addCriterion("sign_number =", value, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberNotEqualTo(Integer value) {
            addCriterion("sign_number <>", value, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberGreaterThan(Integer value) {
            addCriterion("sign_number >", value, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_number >=", value, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberLessThan(Integer value) {
            addCriterion("sign_number <", value, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberLessThanOrEqualTo(Integer value) {
            addCriterion("sign_number <=", value, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberIn(List<Integer> values) {
            addCriterion("sign_number in", values, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberNotIn(List<Integer> values) {
            addCriterion("sign_number not in", values, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberBetween(Integer value1, Integer value2) {
            addCriterion("sign_number between", value1, value2, "signNumber");
            return (Criteria) this;
        }

        public Criteria andSignNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_number not between", value1, value2, "signNumber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberIsNull() {
            addCriterion("staking_node_numgber is null");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberIsNotNull() {
            addCriterion("staking_node_numgber is not null");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberEqualTo(Integer value) {
            addCriterion("staking_node_numgber =", value, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberNotEqualTo(Integer value) {
            addCriterion("staking_node_numgber <>", value, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberGreaterThan(Integer value) {
            addCriterion("staking_node_numgber >", value, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberGreaterThanOrEqualTo(Integer value) {
            addCriterion("staking_node_numgber >=", value, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberLessThan(Integer value) {
            addCriterion("staking_node_numgber <", value, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberLessThanOrEqualTo(Integer value) {
            addCriterion("staking_node_numgber <=", value, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberIn(List<Integer> values) {
            addCriterion("staking_node_numgber in", values, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberNotIn(List<Integer> values) {
            addCriterion("staking_node_numgber not in", values, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberBetween(Integer value1, Integer value2) {
            addCriterion("staking_node_numgber between", value1, value2, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andStakingNodeNumgberNotBetween(Integer value1, Integer value2) {
            addCriterion("staking_node_numgber not between", value1, value2, "stakingNodeNumgber");
            return (Criteria) this;
        }

        public Criteria andConNumberIsNull() {
            addCriterion("con_number is null");
            return (Criteria) this;
        }

        public Criteria andConNumberIsNotNull() {
            addCriterion("con_number is not null");
            return (Criteria) this;
        }

        public Criteria andConNumberEqualTo(Integer value) {
            addCriterion("con_number =", value, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberNotEqualTo(Integer value) {
            addCriterion("con_number <>", value, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberGreaterThan(Integer value) {
            addCriterion("con_number >", value, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("con_number >=", value, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberLessThan(Integer value) {
            addCriterion("con_number <", value, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberLessThanOrEqualTo(Integer value) {
            addCriterion("con_number <=", value, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberIn(List<Integer> values) {
            addCriterion("con_number in", values, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberNotIn(List<Integer> values) {
            addCriterion("con_number not in", values, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberBetween(Integer value1, Integer value2) {
            addCriterion("con_number between", value1, value2, "conNumber");
            return (Criteria) this;
        }

        public Criteria andConNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("con_number not between", value1, value2, "conNumber");
            return (Criteria) this;
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