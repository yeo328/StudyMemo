package com.yeo.studywebmemo.services;

import com.yeo.studywebmemo.entities.MemoEntity;
import com.yeo.studywebmemo.mappers.MemoMapper;
import com.yeo.studywebmemo.models.PagingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class MemoService {
    public static final int PAGE_COUNT = 10;
    private final MemoMapper memoMapper;

    @Autowired
    public MemoService(MemoMapper memoMapper){
        this.memoMapper = memoMapper;
    }

    public MemoEntity[] getAll(int requestPage){
        return this.memoMapper.selectAll();
    }
    public int getCount(String searchCriterion, String searchQuery){
        return this.memoMapper.selectCount(searchCriterion, searchQuery);
    }

    public MemoEntity[] getByPage(PagingModel pagingModel, String searchCriterion, String searchQuery){
        MemoEntity[] memoEntities = this.memoMapper.selectByPage(pagingModel, searchCriterion,searchQuery);
        return memoEntities;
    }
    public boolean write(MemoEntity memoEntity) {
        if (memoEntity.getNickname() == null ||
                memoEntity.getText() == null ||
                memoEntity.getText().length() < 1 ||
                !memoEntity.getNickname().matches("^([가-힣]{2,10})")) {
            System.out.println("정규식 실패!");
            return false;
        }
        memoEntity.setDatetime(new Date());
        return this.memoMapper.insert(memoEntity) > 0;
    }
    public boolean deleteByIndex(int index){
        return this.memoMapper.deleteByIndex(index) > 0;
    }
    public boolean updateByIndex(int index, String text){
        return this.memoMapper.updateByIndex(index, text)>0;
    }
}
