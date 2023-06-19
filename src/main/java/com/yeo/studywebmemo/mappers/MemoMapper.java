package com.yeo.studywebmemo.mappers;

import com.yeo.studywebmemo.entities.MemoEntity;
import com.yeo.studywebmemo.models.PagingModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
//인터페이스? 구현설계도?
@Mapper //xml파일을 찾아와서 이어줌
public interface MemoMapper { //Dao

    // 인터페이스 이름이랑 xml id랑 같아야 찾아
    int updateByIndex(@Param(value ="index")int index,//Param? 값을 하나씩 던질때
                      @Param(value = "text")String text);

//    @Delete("delete from `study_web`.`memos` where `index` = #{index} limit 1")
    int deleteByIndex(@Param(value = "index") int index);
    int insert(MemoEntity memoEntity);

    MemoEntity[] selectAll(); //xml파일에 id="selectAll"의 내용을 배열로 돌려줌
    int selectCount(@Param(value ="searchCriterion")String searchCriterion,
                    @Param(value="searchQuery")String searchQuery);
    MemoEntity[] selectByPage(@Param(value="pagingModel") PagingModel pagingModel,
                              @Param(value ="searchCriterion")String searchCriterion,
                              @Param(value="searchQuery")String searchQuery);

}
