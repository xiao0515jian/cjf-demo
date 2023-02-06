package com.cjf.demo.constants;

/**
 * @author : chenjianfeng
 * @date : 2022/8/31
 */
public class Constant {
    /**查询成功 */
    public static final String SUCCESS = "操作成功";

    /**查询失败 */
    public static final String FAILURE = "操作失败";

    /**成功编码 */
    public static final Long SUCCESS_CODE = 200L;

    /**成功编码 */
    public static final Long FAILURE_CODE = 501L;

    /**版本号不能为空 */
    public static final String VERSION_NOT_FOUND = "版本号不能为空";

    /**主键不能为空 */
    public static final String ID_NOT_FOUND = "主键不能为空";

    /**请完成所有发布 */
    public static final String PLEASE_COMPLETE_RELEASE = "请完成所有发布";

    /**逗号 */
    public static final String COMMA = ",";

    /**数字1 */
    public static final Integer OEN = 1;

    /**数字0 */
    public static final Integer ZERO = 0;

    /**字符串1 */
    public static final String ONE = "1";

    /**字符串1 */
    public static final String TWO = "2";

    /**文件名称不正确 */
    public static final String MODE_ERR = "文件名称不正确";

    /** 任务ID不能为空*/
    public static final String TASK_NOT_FOUND = "任务ID不能为空";

    /** 目标市场预测不能为空*/
    public static final String TARGET_MARKET_NOT_FOUND = "目标市场预测不能为空";

    /** 目标份额不能为空*/
    public static final String TARGET_SHARE_NOT_FOUND = "目标份额不能为空";

    /** 本条数据不存在*/
    public static final String DATA_NOT_FOUND = "数据不存在";

    /** 空字符*/
    public static final String NULL_CHAR = "";

    /** %字符*/
    public static final String PERCENT_CHAR = "%";

    /**发票还原后销售能力不能为空*/
    public static final String PARAM_ONE_NOT_FOUND = "发票还原后销售能力不能为空";

    /**发票修正*/
    public static final String PARAM_TWO_NOT_FOUND = "发票修正不能为空";

    /** 计算结果不能为空*/
    public static final String COMPUTE_RESULT_NOT_FOUND = "计算结果不能为空";

    /**
     * 经销商对应前12月aak指标的key
     */
    public static final String DEALER_FOR_AAK_CACHE_KEY = "SalesTarget:first12monthAak:dealer:";
    /**
     * 经销商对应省份code的缓存key
     */
    public static final String DEALER_CODE_FOR_PROVINCE_CACHE_KEY = "SalesTarget:dealerList:province";
    /**
     * 经销商对应城市code的缓存key
     */
    public static final String DEALER_CODE_FOR_CITY_CACHE_KEY = "SalesTarget:dealerList:city";
    /**
     * 每个月经销商列表缓存对应的key
     */
    public static final String DEALER_CODE_LIST_CACHE_KEY = "SalesTarget:dealerList:dealerCode";
    /**
     * 经销商对应城市code的缓存key
     */
    public static final String DEALER_CODE_ISNEW_CACHE_KEY = "SalesTarget:dealerList:isnew:";

    /**
     * 车型对应前12月aak指标的key
     */
    public static final String DEALER_CARS_FOR_AAK_CACHE_KEY = "SalesTarget:first12monthAak:dealerCode:cars:";

    /** 3：燃油车 */
    public static final String CAR_TYPE = "3";

    /** wps start*/
    /**用户操作权限，write：可编辑，read：预览*/
    public static final String PERMISSION_WRITE = "write";
    public static final String PERMISSION_READ = "read";
    /** 水印类型， 0为无水印； 1为文字水印 */
    public static final int WATERMARK_TYPE = 1;
    /** 文字水印的文字 */
    public static final String WATERMARK_VALUE = "WPS";
    /** 水印透明度 */
    public static final Double WATERMARK_ROTATE = -0.50;
    /**
     * 限制预览页数
     * 1. 用户操作权限 user.permission 为 write 时，限制不生效；
     * 2. 用户操作权限 user.permission 为 read 时，
     * previewPages 默认值为 0，不限制预览页数；
     * previewPages >= 1 时，限制生效，限制的页数为 previewpages 字段的值
     */
    public static final int PREVIEW_PAGES = 0;
    /** 重命名权限，1为打开该权限，0为关闭该权限，默认为0 */
    public static final int USER_ACL_RENAME = 0;
    /** 历史版本权限，1为打开该权限，0为关闭该权限，默认为1 */
    public static final int USER_ACL_HISTORY = 0;
    /** 复制权限，1为打开该权限，0为关闭该权限 */
    public static final int USER_ACL_COPY = 1;
    /** 导出权限，1为打开该权限，0为关闭该权限 */
    public static final int USER_ACL_EXPORT = 1;
    /** 打印权限，1为打开该权限，0为关闭该权限 */
    public static final int USER_ACL_PRINT = 0;
    /** 版本展示前缀 */
    public static final String VERSION_KEY = "V";
    public static final String CREATE_PERSON_FOUR = "臧笑宇";
    public static final String CURRENT_VERSION = "当前版本";
    public static final String ERR_PROVINCE_TARGET = "请补全省销售目标！";

    /** wps end*/

    /** 管理员角色ID */
    public static final Integer ADMIN_ROLE = 4001;
    /** 公司领导角色ID */
    public static final Integer LEADER_ROLE = 4005;
    /** 无权限 */
    public static final String NO_POWER = "无权限操作！";
    public static final String COL_SUM = "合计";
    public static final String ROW_SUM = "合计";
    public static final String ZERO_STR = "0";

    public static final int INTEGER_0 = 0;
    public static final int INTEGER_1 = 1;

    public static final String INTEGER_STR_1 = "1";

    public static final int INTEGER_NEGATIVE_1 = -1;
    public static final int INTEGER_2 = 2;
    public static final int INTEGER_3 = 3;
    public static final int INTEGER_4 = 4;
    public static final String NEGATIVE_ONE = "-1";
    public static final String INNER_LIST = "list";
    public static final String YEAR_MONTH = "date";
    public static final String SPLIT = "-";
    public static final String COEFFICIENT = "0.0001";
}
