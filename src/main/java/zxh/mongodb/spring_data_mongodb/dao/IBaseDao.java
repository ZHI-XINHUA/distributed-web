package zxh.mongodb.spring_data_mongodb.dao;

import zxh.mongodb.spring_data_mongodb.util.PageModel;

import java.io.Serializable;
import java.util.List;

/**
 * 操作mongodb数据库的接口
 */
public interface IBaseDao<T> {

    /**
     * 保存
     * @param entity
     */
    void save(T entity);

    /**
     * 更新
     * @param entity
     */
    void update(T entity);

    /**
     * 保存或更新
     * @param entity
     */
    void saveOrUpdate(T entity);

    /**
     * 删除
     * @param ids
     * @return
     */
    void delete(Serializable... ids);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    T findOne(Serializable id);

    /**
     * 查询所有
     * @return
     */
    List<T> findAll();


    /**
     * 查询所有记录并排序
     * @param order  排序字段，例如：id或id asc、或id asc,name desc。为空则不排序，不指定排序方式则默认升序排序
     * @return
     */
    List<T> findAll(String order);


    /**
     * 根据传入参数查询
     * @param paramName 参数名
     * @param paramValue 参数值
     * @return
     */
    List<T> findByParam(String paramName,Object paramValue);

    /**
     * 根据传入参数查询
     * @param paramName 参数名
     * @param paramValue 参数值
     * @param order  排序字段，例如：id或id asc、或id asc,name desc<br>
     *            为空则不排序，不指定排序方式则默认升序排序
     * @return
     */
    List<T> findByParam(String paramName,Object paramValue,String order);

    /**
     * 根据传入参数查询
     * @param paramNames 参数名数组
     * @param paramValues 参数值数组
     * @return
     */
    List<T> findByParams(String[] paramNames,Object[] paramValues);


    /**
     * 根据传入参数查询
     * @param paramNames 参数名数组
     * @param paramValues 参数值数组
     * @param order  排序字段，例如：id或id asc、或id asc,name desc<br>
     *            为空则不排序，不指定排序方式则默认升序排序
     * @return
     */
    List<T> findByParams(String[] paramNames,Object[] paramValues,String order);


    /**
     * 根据参数查询唯一结果
     * @param paramName
     * @param paramValue
     * @return
     */
    T uniqueByParam(String paramName,Object paramValue);

    /**
     * 根据多个参数查询唯一结果
     * @param paramNames
     * @param paramValues
     * @return
     */
    T uniqueByParams(String[] paramNames,Object[] paramValues);


    /**
     * 查询个数
     * @param paramNames
     * @param paramValues
     * @return
     */
    int count(String[] paramNames,Object[] paramValues);


    /**
     * 分页查询
     * @param pageNo  当前页码
     * @param pageSize 页容量
     * @return
     */
    PageModel<T> pageAll(int pageNo, int pageSize);

    /**
     * 分页查询所有结果集合 并排序<br>
     * [分页]
     *
     * @param pageNo
     *            当前页码
     * @param pageSize
     *            页容量
     * @param order
     *            排序字段，例如：id或id asc、或id asc,name desc<br>
     *            为空则不排序，不指定排序方式则默认升序排序
     * @return 分页模型对象（不会为null）
     */
    PageModel<T> pageAll(int pageNo, int pageSize, String order);

    /**
     * 根据参数分页查询结果集合<br>
     * [分页]
     *
     * @param pageNo
     *            当前页码
     * @param pageSize
     *            页容量
     * @param param
     *            参数
     * @param value
     *            参数值
     * @return 分页模型对象（不会为null）
     */
    PageModel<T> pageByParam(int pageNo, int pageSize, String param, Object value);

    /**
     * 根据参数分页查询结果集合并排序<br>
     * [分页]
     *
     * @param pageNo
     *            当前页码
     * @param pageSize
     *            页容量
     * @param param
     *            参数
     * @param value
     *            参数值
     * @param order
     *            排序字段，例如：id或id asc、或id asc,name desc<br>
     *            为空则不排序，不指定排序方式则默认升序排序
     * @return 分页模型对象（不会为null）
     */
    PageModel<T> pageByParam(int pageNo, int pageSize, String param, Object value, String order);






}
