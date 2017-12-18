package com.feo.port.rest.dto.report;

import java.io.Serializable;

public class PageParameters implements Serializable {
    private Integer page = 0;//默认第一页
    private Integer size = 10;//默认十条数据

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public PageParameters() {
    }
}
