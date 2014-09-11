package net.duguying.cms.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class Post extends Model<Post>{
	public static final Post dao = new Post();
	
	/**
	 * 分页列表
	 * @param page 页码
	 * @param num 每页数目
	 * @return Page<Post>类型
	 */
	public static Page<Post> getArticlePage(int page, int num){
		Page<Post> posts =  Post.dao.paginate(page, num,  "select *",  "from post order by post_time desc");
		return posts;
	}
	
	public static Page<Post> searchArticle(String keyword, int page, int num){
		Page<Post> posts =  Post.dao.paginate(page, num,  "select *",  "from post where content like '%"+keyword+"%' or title like '%"+keyword+"%' order by post_time desc");
		return posts;
	}
	
	/**
	 * 获取总页数
	 * @return
	 */
	public static int totalPage(int num){
		List<Record> totalItems = Db.find("select count(*) as num from post");
		String item = totalItems.get(0).get("num").toString();
		int total = Integer.parseInt(item);
		return (total/num) + ((0==total%num)?(0):(1));
	}
	
	/**
	 * 更新记录
	 * @param title 标题
	 * @param author 作者
	 * @param content 内容
	 * @param byUsername 查找条件-用户名
	 * @param byId 查找条件-id
	 * @return
	 */
	public static boolean updateArticle(String title, String content, String byUsername, int byId){
		int rst = Db.update("UPDATE post SET title='"+title+"',content='"+content+"' WHERE id="+byId+" and author='"+byUsername+"'");
		if(rst>0){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean deleteByIdAndAuthor(int id, String username){
//		Sqls
		return false;
	}
}
