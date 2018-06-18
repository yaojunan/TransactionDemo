package com.smart.pojo;

import com.smart.dao.ForumDao;
import com.smart.dao.PostDao;
import com.smart.dao.TopicDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BbtForum {

    public ForumDao forumDao;

    public TopicDao topicDao;

    public PostDao postDao;

    public void addTopic(Topic topic){
        topicDao.addTopic(topic);
//        if(true) throw new PessimisticFailureException("faile");
    }

    public Forum getForum(int forumId){
        return null;
    }

    public void updateForum(Forum forum){

    }

    public int getForumNum(){
        return 1;
    }
}
