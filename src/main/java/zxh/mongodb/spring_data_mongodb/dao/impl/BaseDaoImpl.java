package zxh.mongodb.spring_data_mongodb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;
import zxh.mongodb.spring_data_mongodb.dao.IBaseDao;
import zxh.mongodb.spring_data_mongodb.util.PageModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  操作mongodb数据库
 */
public abstract  class BaseDaoImpl<T> implements IBaseDao<T>{

    /**
     * 获取T的class方法，实现类要实现
     * @return
     */
    protected abstract Class<T> getEntityClass();

    @Autowired
    private MongoTemplate template;

    @Override
    public void save(T entity) {
        template.save(entity);
    }

    @Override
    public void update(T entity) {
       // entity.getClass()
        //template.updateFirst()
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
        Query query  = new Query();
        query.with(new Sort(parseOrder(order))); //添加排序
        return template.find(query,getEntityClass());
    }

    @Override
    public List<T> findByParam(String paramName, Object paramValue) {
        return null;
    }

    @Override
    public List<T> findByParam(String paramName, Object paramValue, String order) {
        return null;
    }

    @Override
    public List<T> findByParams(String[] paramNames, Object[] paramValues) {
        return null;
    }

    @Override
    public List<T> findByParams(String[] paramNames, Object[] paramValues, String order) {
        return null;
    }

    @Override
    public T uniqueByParam(String paramName, Object paramValue) {
        return null;
    }

    @Override
    public T uniqueByParams(String[] paramNames, Object[] paramValues) {
        return null;
    }

    @Override
    public int count(String[] paramNames, Object[] paramValues) {
        return 0;
    }

    @Override
    public PageModel<T> pageAll(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PageModel<T> pageAll(int pageNo, int pageSize, String order) {
        return null;
    }

    @Override
    public PageModel<T> pageByParam(int pageNo, int pageSize, String param, Object value) {
        return null;
    }

    @Override
    public PageModel<T> pageByParam(int pageNo, int pageSize, String param, Object value, String order) {
        return null;
    }


    //============================私有方法=========================

    /**
     * 解析Order字符串为所需参数
     *
     * @param orderStr
     *            排序参数，如[id]、[id asc]、[id asc,name desc]
     * @return Order对象集合
     */
    private List<Sort.Order> parseOrder(String orderStr){
        if(StringUtils.isEmpty(orderStr)){
            return null;
        }
        List<Sort.Order> list = new ArrayList<Sort.Order>();
        String[] orders = orderStr.split(",");
        for(int i=0;i<orders.length;i++){
            String orderItem = orders[i];
            if(StringUtils.isEmpty(orderItem)){
                continue;
            }

            Sort.Order order = null;
            String[] item = orderItem.split(" ");
            if(item.length==1){
                //没有明确升降则默认升序
                order = new Sort.Order(Sort.Direction.ASC,item[0]);
            }else if(item.length==2){
                order = new Sort.Order(item[1].trim().equalsIgnoreCase("asc")?Sort.Direction.ASC: Sort.Direction.DESC,item[0]);
            }else{
                throw new RuntimeException("排序字段参数解析出错");
            }
            list.add(order);
        }
        return list;
    }

}
