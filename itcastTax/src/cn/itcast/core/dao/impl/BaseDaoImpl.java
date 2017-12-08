package cn.itcast.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.core.dao.BaseDao;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements 	BaseDao<T> {
    
	Class<T> clazz;
	/**
	 * 简而言之就是获得超类的泛型参数的实际类型
	 */
	public BaseDaoImpl(){
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();//子类实例化后的到BaseDaoImpl<User>
		clazz = (Class<T>) pt.getActualTypeArguments()[0];//返回表示此类型实际类型参数的 Type 对象的数组
	}
	@Override
	public void save(T entity) {
      getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findObjectById(id));
	}

	@Override
	public T findObjectById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findObjects() {
		Query query = getSession().createQuery("from "+clazz.getSimpleName());
		return query.list();
	}

}
