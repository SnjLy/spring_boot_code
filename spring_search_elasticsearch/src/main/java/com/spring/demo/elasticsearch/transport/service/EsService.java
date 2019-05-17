package com.spring.demo.elasticsearch.transport.service;

/**
 * com.spring.demo.elasticsearch.transport.service
 *
 * @author: liuyong
 * @date: 2019-05-17
 */
public interface EsService {


    /**
     * 创建索引
     */
    void createIndex();

    /**
     * 插入索引文档
     * @param object
     */
    void insert(Object object);

    /**
     * 批量插入文档
     * @param object
     */
    void bulkInsert(Object object);

    /**
     * 删除文档数据和索引
     */
    void delete();

    /**
     * 根据查询条件删除文档数据和索引
     */
    void deleteByQuery();

    /**
     * es查询文档
     */
    void query();


}
