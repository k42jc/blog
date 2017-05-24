package com.techeffic.blog.dal.datasource.ha;

import javax.sql.DataSource;

/**
 * Created by liaoxudong on 2017/5/16.
 */
public interface IDataSourcePostProcessor {

    DataSource postProcess(DataSource dataSource);
}
