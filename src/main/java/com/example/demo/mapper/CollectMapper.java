package com.example.demo.mapper;

import com.example.demo.entity.Collect.Collect;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectMapper {
    List<Collect> gettopcolection(String user_id, int page_list);

    List<Collect> reloadcollect(String user_id, int last_id, int page_list);

    boolean addcollect(Collect collect);

    boolean updatecollect(Collect collect);

    List<Collect> getmycollect(String user_id, int page_list);

    List<Collect> reloadmycollect(String user_id, int last_id, int page_list);

    boolean delcollect(String user_id,int collect_id);

    List<Collect> searchcollect(String search_text, int page_list, String user_id);

    List<Collect> reloadsearchcollect(String search_text, int last_id, int page_list,String user_id);

    int getmycollectnum(String user_id);
}
