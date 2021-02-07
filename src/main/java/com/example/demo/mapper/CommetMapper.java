package com.example.demo.mapper;

import com.example.demo.entity.Commet.Commet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommetMapper {

   List<Commet> getcommet(int collect_id, int page_list);

   List<Commet> reloadcommet(int collect_id, int page_list, int last_id);

   boolean addcommet(Commet commet);
}
