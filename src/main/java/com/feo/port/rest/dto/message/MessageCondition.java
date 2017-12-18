package com.feo.port.rest.dto.message;

import io.swagger.annotations.ApiModelProperty;

/**
 * 消息查询条件
 * Created by huang on 2017/11/27.
 */
public class MessageCondition {

    @ApiModelProperty("页码")
    private Integer page = 1;

    @ApiModelProperty("每页多少")
    private Integer size = 20;

    @ApiModelProperty("消息类型 0，军团动态 1，SOP反馈 2，系统通知")
    private Integer contentType;

    @ApiModelProperty("信息状态：0，未读 1，已读")
    private Integer status;

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

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
