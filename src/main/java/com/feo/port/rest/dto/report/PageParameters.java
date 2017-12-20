package com.feo.port.rest.dto.report;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParameters implements Serializable {
    private Integer page = 0;//默认第一页
    private Integer size = 10;//默认十条数据

    public PageParameters() {
    }
}
