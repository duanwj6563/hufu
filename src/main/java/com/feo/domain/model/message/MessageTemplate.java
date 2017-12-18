package com.feo.domain.model.message;

/**
 * Created by huang on 2017/11/24.
 */
public class MessageTemplate {

    /**
     * 页面间隔多久可收到推送
     */
    public final static Integer PUSH_TIME = 5;

    /**
     * 待审批模板
     *
     * @param groupName
     * @param strategyName
     * @return
     */
    public static String WAIT_APPROVE(String groupName, String strategyName) {
        return groupName + "军团" + strategyName + "策略已发布，请审批~";
    }

    /**
     * 已审核模板
     *
     * @param strategyName
     * @param username
     * @return
     */
    public static String FINISH_APPROVE(String strategyName, String username) {
        return strategyName + "策略已被" + username + "通过审核";
    }

    /**
     * 已驳回模板
     *
     * @param groupName
     * @param username
     * @param commit
     * @return
     */
    public static String NO_APPROVE(String strategyName, String username, String commit) {
        return strategyName + "策略已被" + username + "驳回，原因：" + commit;
    }

    /**
     * 已批注模板
     *
     * @param strategyName
     * @param username
     * @param commit
     * @return
     */
    public static String POSTIL(String strategyName, String username, String commit) {
        return strategyName + "策略已被" + username + "批注，批注内容：" + commit;
    }

    /**
     * 已备注模板
     *
     * @param strategyName
     * @param username
     * @param commit
     * @return
     */
    public static String COMMENT(String strategyName, String username, String commit) {
        return strategyName + "策略已被" + username + "备注内容：" + commit;
    }

    /**
     * 已追加模板
     *
     * @param strategyName
     * @param username
     * @return
     */
    public static String ADD_TO(String strategyName, String username) {
        return strategyName + "策略被" + username + "追加";
    }


    /**
     * 被精选模板
     *
     * @param strategyName
     * @param username
     * @return
     */
    public static String GOOD_SELECT(String strategyName, String type, String username) {
        return strategyName + type + "已被" + username + "标记为精选";
    }

    /**
     * 分析报告模板
     *
     * @param title
     * @param date
     * @return
     */
    public static String AUALYZE_REPORT(String title, String date) {
        return title + "分析报告已生成，请点击查看第三军团策略与质检分析报告(" + date + ")";
    }


}
