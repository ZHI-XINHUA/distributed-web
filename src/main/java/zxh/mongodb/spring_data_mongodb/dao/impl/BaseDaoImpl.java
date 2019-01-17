package zxh.mongodb.spring_data_mongodb.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;
import zxh.mongodb.spring_data_mongodb.dao.IBaseDao;
import zxh.mongodb.spring_data_mongodb.util.PageModel;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 *  操作mongodb数据库
 */
public abstract  class BaseDaoImpl<T> implements IBaseDao<T>{
    private static Logger LOG = Logger.getLogger(BaseDaoImpl.class);

    /**
     * 获取T的class方法，实现类要实现
     * @return
     */
    protected abstract Class<T> getEntityClass();

    @Autowired
    @Qualifier("mongoTemplate") //指定数据源的template
    private MongoTemplate template;



    @Override
    public void save(T entity) {
        template.save(entity);
    }

    @Override
    public void update(T entity) {
        Map<String,Object> paramMap = null;
        try {
            paramMap = parseEntity(entity);
        } catch (Exception e) {
            LOG.error("更新失败，原因：解析实体类出错！");
            e.printStackTrace();
            throw new RuntimeException("解析实体类出错！");
        }

        if(paramMap==null || paramMap.isEmpty()){
            return;
        }

        //过滤条件，id过滤
        Query query = new Query();
        //更新字段
        Update update = new Update();

        Set<Map.Entry<String, Object>> set = paramMap.entrySet();
        for(Map.Entry<String,Object> item:set){
            String key = item.getKey();
            Object value = item.getValue();
            if(key.indexOf("#")==0){
                query.addCriteria(Criteria.where(key.replace("#","")).is(value));
                continue;
            }
            update.set(key,value);
        }
        //更新数据库
        template.updateFirst(query,update,getEntityClass());
    }

    @Override
    public void saveOrUpdate(T entity) {

    }

    @Override
    public void delete(Serializable... ids) {
        if(StringUtils.isEmpty(ids)){
            return;
        }
        for (Serializable id : ids){
            template.remove(template.findById(id,getEntityClass()));
        }

    }

    @Override
    public T findOne(Serializable id) {
        return template.findById(id,getEntityClass());
    }

    @Override
    public List<T> findAll() {
        return template.findAll(getEntityClass());
    }

    @Override
    public List<T> findAll(String order) {
        if(StringUtils.isEmpty(order)){
            return findAll();
        }
        Query query  = new Query();
        query.with(new Sort(parseOrder(order))); //添加排序
        return template.find(query,getEntityClass());
    }

    @Override
    public List<T> findByParam(String paramName, Object paramValue) {
        return  findByParam(paramName,paramValue,null);
    }

    @Override
    public List<T> findByParam(String paramName, Object paramValue, String order) {
        Query query = new Query();
        query.addCriteria(Criteria.where(paramName).is(paramValue));

        if(!StringUtils.isEmpty(order)){
            query.with(new Sort(parseOrder(order))); //添加排序
        }
        return template.find(query,getEntityClass());

    }

    @Override
    public List<T> findByParams(String[] paramNames, Object[] paramValues) {
        return findByParams(paramNames,paramValues,null);
    }

    @Override
    public List<T> findByParams(String[] paramNames, Object[] paramValues, String order) {
        Query query =createQuery(paramNames,paramValues,order);
        return template.find(query,getEntityClass());
    }

    @Override
    public T uniqueByParam(String paramName, Object paramValue) {
        return template.findOne(new Query(Criteria.where(paramName).is(paramValue)),getEntityClass());
    }

    @Override
    public T uniqueByParams(String[] paramNames, Object[] paramValues) {
        Query query = createQuery(paramNames,paramValues,null);
        return template.findOne(query,getEntityClass());
    }

    @Override
    public int count(String[] paramNames, Object[] paramValues) {
        Query query = createQuery(paramNames,paramValues,null);
        Long count =  template.count(query,getEntityClass());
        return  count.intValue();
    }

    @Override
    public PageModel<T> pageAll(int pageNo, int pageSize) {
        return pageAll(pageNo,pageSize,null);
    }

    @Override
    public PageModel<T> pageAll(int pageNo, int pageSize, String order) {
        return pageByParams(pageNo,pageSize,null,null,order);
    }

    @Override
    public PageModel<T> pageByParam(int pageNo, int pageSize, String param, Object value) {
        return pageByParam(pageNo,pageSize,param,value,null);
    }

    @Override
    public PageModel<T> pageByParam(int pageNo, int pageSize, String param, Object value, String order) {
        return pageByParams(pageNo,pageSize,new String[]{param},new Object[]{value},order);
    }
    @Override
    public PageModel<T> pageByParams(int pageNo, int pageSize, String[] params, Object[] values){
        return pageByParams(pageNo,pageSize,params,values,null);
    }

    @Override
    public PageModel<T> pageByParams(int pageNo, int pageSize, String[] params, Object[] values, String order){
        // 创建分页模型对象
        PageModel<T> page = new PageModel<>(pageNo,pageSize);

        //查询记录数
        int count = count(params,values);
        page.setTotalCount(count);

        // 查询数据列表
        Query query = createQuery(params, values, order);

        // 设置分页信息
        query.skip(page.getFirstResult());

        query.limit(page.getPageSize());

        // 封装结果数据
        page.setResult(template.find(query, getEntityClass()));
        return page;
    }

    //============================私有方法=========================

    /**
     * 解析Order字符串为所需参数
     *
     * @param orderStr
     *            排序参数，如[id]、[id asc]、[id asc,name desc]  注意：mongodb字段区分大小写
     * @return Order对象集合
     */
    private List<Sort.Order> parseOrder(String orderStr){
        if(StringUtils.isEmpty(orderStr)){
            return null;
        }
        orderStr = orderStr.trim();
        List<Sort.Order> list = new ArrayList<Sort.Order>();
        String[] orders = orderStr.split(",");
        for(int i=0;i<orders.length;i++){
            String orderItem = orders[i];
            if(StringUtils.isEmpty(orderItem)){
                continue;
            }

            Sort.Order order = null;
            String orderItem_ = orderItem.toLowerCase();
            String orderReg = orderItem_.indexOf("asc")>=0?"asc":orderItem_.indexOf("desc")>=0?"desc":"asc";
            String orderAttr = orderItem_.replace(orderReg,"").trim();
            order = new Sort.Order("asc".equals(orderReg)?Sort.Direction.ASC:Sort.Direction.DESC,orderAttr);
            list.add(order);
        }
        return list;
    }


    /**
     * 解析实体类：获取实体类@Id和@Field注解的字段及其值
     * @param entity
     * @return Map  id key为#fieldname
     */
    private Map<String,Object> parseEntity(T entity) throws Exception{
        Class class_ = getEntityClass();
        Map<String,Object> map = new HashMap<>();

        String mgfieldName = "";
        String fieldName = "";

        Field[] fields = class_.getDeclaredFields();
        for (Field field : fields){

            boolean hasIdFlag = false;
            //解析@Id
            if(field.isAnnotationPresent(Id.class)){
                mgfieldName = fieldName = field.getName();
                hasIdFlag = true;
            }

            //解析@Field注解的字段，获取名称和值
            if(field.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)){
                org.springframework.data.mongodb.core.mapping.Field mgField = field.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
                mgfieldName = mgField.value();
                fieldName = field.getName();
            }

            if(fieldName==null || fieldName.length()==0){
                continue;
            }


            String fieldName_ =  fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());
            String getMethodName = "get"+fieldName_;

            if(mgfieldName==null || mgfieldName.length()==0){
                mgfieldName = fieldName;
            }

            Method method = class_.getDeclaredMethod(getMethodName);
            //修改bug，method.getModifiers()!= Modifier.PUBLIC 比较错误，Modifier.PUBLIC是位运算
            //if(method.getModifiers()!= Modifier.PUBLIC){
            if(!Modifier.isPublic(method.getModifiers())){
                continue;
            }
            Object value = method.invoke(entity);

           map.put((hasIdFlag) ?("#"+mgfieldName):mgfieldName,value);

        }

        return map;
    }

    /**
     * 封装请求参数
     * @param params
     * @param values
     * @param order
     * @return
     */
    private Query createQuery(String[] params, Object[] values, String order) {
        Query query = new Query();
        for(int i=0;params!=null && i<params.length;i++){
            try{
                query.addCriteria(Criteria.where(params[i]).is(values[i]));
            }catch (Exception e){

            }
        }
        if(!StringUtils.isEmpty(order)){
            query.with(new Sort(parseOrder(order)));
        }
        return query;
    }

}
