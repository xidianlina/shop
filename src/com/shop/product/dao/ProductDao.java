package com.shop.product.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.shop.product.vo.Product;
import com.shop.utils.PageHibernateCallback;

/**
 * 商品的持久层
 * 
 * @author dell
 * 
 */
public class ProductDao extends HibernateDaoSupport {

	/**
	 * 持久层查询首页热门商品的方法
	 * 
	 * @return
	 */
	public List<Product> findHotProduct() {
		// 使用离线条件查询。
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// 查询热门商品。查询条件是is_hot的值为1。
		criteria.add(Restrictions.eq("is_hot", 1));
		// 倒序排序输出
		criteria.addOrder(Order.desc("pdate"));
		// 执行查询
		List<Product> list = this.getHibernateTemplate().findByCriteria(
				criteria, 0, 10);
		return list;
	}

	/**
	 * 持久层首页上最新商品的查询方法
	 * 
	 * @return
	 */
	public List<Product> findNewProduct() {
		// 离线条件查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// 按日期进行排序查询
		criteria.addOrder(Order.desc("pdate"));
		List<Product> list = this.getHibernateTemplate().findByCriteria(
				criteria, 0, 10);
		return list;
	}

	/**
	 * 持久层根据商品的ID查询商品
	 * 
	 * @param pid
	 * @return
	 */
	public Product findByPid(Integer pid) {
		return this.getHibernateTemplate().get(Product.class, pid);

	}

	/**
	 * 持久层根据一级分类的cid查询商品的总个数
	 * 
	 * @param cid
	 * @return
	 */
	public int findCountCid(Integer cid) {
		String hql = "select count(*) from Product p where p.categorySecond.category.cid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql, cid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * 持久层根据一级分类的cid查询商品的集合
	 * 
	 * @param cid
	 * @param begin
	 * @param limit
	 * @return
	 */
	public List<Product> findByPageCid(Integer cid, int begin, int limit) {
		// select p.* from category c,categorysecond cs,product p where c.cid =
		// cs.cid and cs.csid = p.csid and c.cid = 2
		// select p from Category c,CategorySecond cs,Product p where c.cid =
		// cs.category.cid and cs.csid = p.categorySecond.csid and c.cid = ?
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid = ?";
		// 分页另一种写法:
		List<Product> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Product>(hql, new Object[] { cid },
						begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 持久层格局二级分类的csid查询商品的总个数
	 * 
	 * @param csid
	 * @return
	 */
	public int findCountCsid(Integer csid) {
		String hql = "select count(*) from Product p where p.categorySecond.csid = ?";
		List<Long> list = this.getHibernateTemplate().find(hql, csid);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * 持久层根据二级分类的cid查询商品的集合
	 * 
	 * @param csid
	 * @param begin
	 * @param limit
	 * @return
	 */
	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs where cs.csid = ?";
		List<Product> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Product>(hql, new Object[] { csid },
						begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 持久层查询商品的总记录数
	 * 
	 * @return
	 */
	public int findCount() {
		String hql = "select count(*) from Product";
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/**
	 * 持久层带分页的查询商品的方法
	 * 
	 * @param begin
	 * @param limit
	 * @return
	 */
	public List<Product> findByPage(int begin, int limit) {
		String hql = "from Product order by pdate desc";
		List<Product> list = this.getHibernateTemplate().execute(
				new PageHibernateCallback<Product>(hql, null, begin, limit));
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 持久层保存商品的方法
	 * 
	 * @param product
	 */
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
	}

	/**
	 * 持久层删除商品的方法
	 * 
	 * @param product
	 */
	public void delete(Product product) {
		this.getHibernateTemplate().delete(product);
	}

	/**
	 * 持久层修改商品的方法
	 * 
	 * @param product
	 */
	public void update(Product product) {
		this.getHibernateTemplate().update(product);
	}

}