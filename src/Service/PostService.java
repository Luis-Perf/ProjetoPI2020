package Service;

import Model.Post;
import DAO.PostDAO;

public class PostService {
	PostDAO postDAO = new PostDAO();
	
	public int criar(Post post) {
		java.util.Date data = new java.util.Date();
		java.sql.Date dataSql = new java.sql.Date(data.getTime());
		post.setDataPost(dataSql);
		return postDAO.criar(post);
	}
}