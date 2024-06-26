package com.example.PageStorage.common.pagination;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaginationCollection<T> {

    private final List<T> itemsWithNextCursor; // 현재 스크롤의 요소 + 다음 스크롤의 요소 1개 (다음 스크롤이 있는지 확인을 위한)
    private final int countPerScroll; //스크롤 1회에 조회할 데이터의 개수

    public static <T> ScrollPaginationCollection<T> of(List<T> itemsWithNextCursor, int size) {
        return new ScrollPaginationCollection<>(itemsWithNextCursor, size);
    }

    //현재 스크롤이 마지막 스크롤인지 확인하기 위한 메소드
    //countPerScroll 의 숫자 이하로 조회되면 마지막 스크롤
    public boolean isLastScroll() {
        return this.itemsWithNextCursor.size() <= countPerScroll;
    }

    //마지막 스크롤일 경우 itemsWithNextCursor 를 return 하고
    //마지막 스크롤이 아닐 경우 다음 스크롤의 데이터 1개를 제외하고 return
    public List<T> getCurrentScrollItems() {
        if (isLastScroll()) {
            return this.itemsWithNextCursor; //마지막 스크롤이면 요소 전체
        }
        return this.itemsWithNextCursor.subList(0, countPerScroll); //스크롤 1회에 조회할 데이터의 개수만큼 가져오기
    }

    //현재 스크롤의 데이터 중 마지막 데이터를 cursor로 사용하고 이를 return
    public T getNextCursor() {
        return itemsWithNextCursor.get(countPerScroll - 1);
    }

}
