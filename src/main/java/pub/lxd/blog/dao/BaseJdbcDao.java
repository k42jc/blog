package pub.lxd.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 * 基础jdbcDao
 * @author xudong_liao<br/>
 * @date 2015年12月24日<br/>
 */
@Repository
public class BaseJdbcDao {
	/*@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	protected List<Map<String,Object>> queryList(String sql,Map<String,Object> paramMap){
		return jdbcTemplate.queryForList(sql, paramMap);
	}
	
	protected Map<String,Object> queryOne(String sql,Map<String,Object> paramMap){
		return jdbcTemplate.queryForMap(sql, paramMap);
	}*/
}
