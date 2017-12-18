package com.feo.port.rest.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CommonPage {

    //    内容列表1
    private List<Object> content=new ArrayList<>();
    //    页码
    private int pageNumber=1;
    //    总条目
    private BigInteger totalElements;
    //    总页数
    private int totalPages;
    //    每页总条数
    private int size=5;

    private String sort;

    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public BigInteger getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(BigInteger totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages() {
        BigInteger totalPage=totalElements.divide(BigInteger.valueOf(size));
        BigInteger m=totalElements.mod(BigInteger.valueOf(size));
        int n=m.intValue();
        int totalPages=totalPage.intValue();
        if (n!=0){
            this.totalPages = totalPages+1;
        }else {
            this.totalPages=totalPages;
        }
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private int setIndeByPage(int pageNumber, int size) {

        return ((pageNumber<=0 ? 1 :pageNumber) - 1) * size;
    }

}
