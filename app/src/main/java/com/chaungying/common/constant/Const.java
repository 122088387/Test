package com.chaungying.common.constant;

import android.os.Environment;

/**
 * 接口
 *
 */
public interface Const {

    public static class WuYe {

        /**
         * 本地服务器测试地址
         */

//        public static final String URL_BASE = "http://192.168.1.120:80/";
//        public static final String URL_BASE = "http://221.238.40.119:30017/";//测试

        public static final String URL_BASE = "http://139.129.10.71:6060/";//正式
        //public static final String URL_BASE = "http://139.129.10.71:6061/";
//        public static final String URL_BASE = "http://123.56.135.139:8082/";
//        public static final String URL_BASE = "http://101.201.153.165:8080/";
       // public static final String URL_BASE = "http://221.238.40.119:30017 /";

        /**
         * 工作绩效
         */
        public static final String URL_JOP_PER = URL_BASE +
                "propertyInterface/workPerformance/getWorkPerformanceList.action";

        /**
         * 员工绩效筛选头
         */
        public static final String URL_JOP_PER_HEADER = URL_BASE +
                "propertyInterface/workPerformance/getWorkPerformanceFiltrate.action";
        /**
         * 签到 分析：
         */
        public static final String URL_SIGNIN_WORK_PERFORMANCE = URL_BASE +
                "propertyInterface/workPerformance/getSignInWorkPerformance.action";
        /**
         * 完成率分析：
         */
        public static final String URL_SIGNIN_WORK_REPAIRCHARTS_LIST = URL_BASE +
                "propertyInterface/workPerformance/getRepairChartsList.action";
        /**
         * 投诉咨询分析：
         */
        public static final String URL_SIGNIN_WORK_SERVICE_WORK_PERFORMANCE_LIST = URL_BASE +
                "propertyInterface/workPerformance/getServiceWorkPerformanceList.action";
        /**
         * 用车下拉时间：
         */
        public static final String URL_SIGNIN_WORK_CAR_WORK_PERFORMANCE_FILETRATE = URL_BASE +
                "propertyInterface/workPerformance/getCarWorkPerformanceFiltrate.action";
        /**
         * 用车分析：
         */
        public static final String URL_SIGNIN_WORK_CAR_WORK_PERFORMANCE_LIST = URL_BASE +
                "propertyInterface/workPerformance/getCarWorkPerformanceList.action";

        /**
         * 会议室预定记录
         */
        public static final String URL_METTING_ROOM_RECORD = URL_BASE +
                "propertyInterface/meeting/showMyMeetingRecordList.action";

        /**
         * 取消我的会议室预定记录
         */
        public static final String URL_METTING_ROOM_CANCEL = URL_BASE +
                "propertyInterface/meeting/deleteMyMeetingRecord.action";

        /**
         * 用车预定记录
         */
        public static final String URL_USER_CAR_MY_RECORD = URL_BASE +
                "propertyInterface/car/showMyCarRecordList.action";

        /**
         * 取消我的用车预定记录
         */
        public static final String URL_USER_CAR_CANCEL = URL_BASE +
                "propertyInterface/car/deleteMyCarRecord.action";

        /**
         * 查询今天签到次数
         * Body：date=查询日期（yyyy-MM-dd）&memberId=用户id
         */
        public static final String URL_SIGN_IN_COUNT = URL_BASE +
                "propertyInterface/signIn/queryTodaySignIn.action";

        /**
         * 登录接口
         */
        public static final String URL_LOGIN = URL_BASE +
                "propertyInterface/user/login.action";

        /**
         * 现场报修接口
         */
        public static final String URL_ON_SITE_REPAIR = URL_BASE +
                "propertyInterface/repair/showApplicationPage.action";


        /**
         * 报修记录
         */
        public static final String URL_REPAIR_RECORD_LIST = URL_BASE +
                "propertyInterface/repair/showApplicationPageForList.action";
        /**
         * 报修记录列表
         */
        public static final String URL_LIST = URL_BASE +
                "propertyInterface/applicationList/getApplicationListByAppId.action";
        /**
         * 材料明细
         */
        public static final String URL_CAI_LIAO_DETAILS = URL_BASE +
                "/propertyInterface/repair/uploadPageList.action";
        /**
         * 上传图片
         */
        public static final String URL_UP_LOAD_IMAGES = URL_BASE +
                "propertyInterface/repair/uploadImages.action";

        /**
         * 进入单个列表页,报修记录详情页
         */
        public static final String URL_EPAIR_RECORD_DETIALS = URL_BASE +
                "propertyInterface/repair/showRepairById.action";

        /**
         * 会议室
         */
        public static final String URL_METTING_ROOM = URL_BASE +
                "propertyInterface/meeting/showMeetingList.action";

        /**
         * 会议室选择
         */
        public static final String URL_METTING_ROOM_SELECT = URL_BASE +
                "propertyInterface/meeting/showMeetingRecordList.action";

        /**
         * 会议室订单
         */
        public static final String URL_METTING_ROOM_ORDER = URL_BASE +
                "propertyInterface/meeting/addMeetingRecord.action";

        /**
         * 签到
         */
        public static final String URL_SIGN = URL_BASE +
                "propertyInterface/signIn/saveSignIn.action";

        /**
         * 签到统计
         */
        public static final String URL_SIGN_STATISTICAL = URL_BASE +
                "propertyInterface/signIn/querySignIn.action";
        /**
         * 工作单列表的接口
         */
        public static final String URL_WORK_LIST = URL_BASE +
                "propertyInterface/repair/showApplications.action";


        /**
         * 用车管理
         */
        public static final String URL_USER_CAR = URL_BASE +
                "/propertyInterface/car/showCarList.action";

        /**
         * 显示车辆所有预定记录
         */
        public static final String URL_USER_CAR_RECORD = URL_BASE +
                "propertyInterface/car/showCarRecordList.action";

        /**
         * 车辆预定
         */
        public static final String URL_USER_CAR_ORDER = URL_BASE +
                "propertyInterface/car/addCarRecord.action";

        /**
         * 每周菜谱
         */
        public static final String URL_AVERY_MENU = URL_BASE +
                "propertyInterface/cookbook/cookbook.action";

        /**
         * 园区导航
         */
        public static final String URL_PARK_LIST = URL_BASE +
                "propertyInterface/district/queryDistrict.action";

        /**
         * 园区导航显示地图界面
         */
        public static final String URL_PARK_LIST_DETAILS = URL_BASE +
                "propertyInterface/district/queryDistrictGPS.action";

        /**
         * 咨询解答详情界面
         */
        public static final String URL_ZI_XUN_DETAILS = URL_BASE +
                "propertyInterface/applicationList/getApplicationDetailById.action";

        /**
         * 新闻通知
         */
        public static final String URL_NEWS_LIST = URL_BASE +
                "propertyInterface/activity/queryActivity.action";

        /**
         * 新闻公告
         */
        public static final String URL_NOTICE_LIST = URL_BASE +
                "propertyInterface/activity/queryActivity.action";

        /**
         * 新闻通知详情
         */
        public static final String URL_NEWS_LIST_DETAILS = URL_BASE +
                "propertyInterface/activity/queryActivityLayout.action";
        /**
         * 工作台
         */
        public static final String URL_WORK_BEANCE = URL_BASE +
                "propertyInterface/work/getWorkList.action";

        /**
         * 我的报修的接口
         */
        public static final String URL_WORK_MY_REPAIR = URL_BASE +
                "propertyInterface/work/getMyRepairList.action";

        /**
         * 抢单详情提交的时候
         */
        public static final String URL_WORK_ORDER_DETAILS_UPLOAD = URL_BASE +
                "propertyInterface/work/grab.action";

        /**
         * 确认接单
         */
        public static final String URL_WORK_TASK_CORNTER_SIGN = URL_BASE +
                "propertyInterface/work/taskCountersign.action";

        /**
         * 涂鸦提交的接口
         */
        public static final String URL_UPLOAD_SINGNATURE = URL_BASE +
                "propertyInterface/repair/uploadSignature.action";

        /**
         * 录音提交的接口
         */
        public static final String URL_UPLOAD_VOICE = URL_BASE +
                "propertyInterface/repair/uploadVoices.action";

        /**
         * 提交
         */
        public static final String URL_SAVE_APPLICATION_PAGE = URL_BASE +
                "propertyInterface/repair/saveApplicationPage.action";

        /**
         * 版本检查
         */
        public static final String URL_VERSION_CHECK = "http://139.129.10.71:31005/AppAPI.ashx";
        /**
         * 显示网页版本页面
         */
        public static final String URL_VERSION_SHOW_PAGE = "http://139.129.10.71:31005/App.aspx";

        /**
         * 使用教程
         */
        public static final String URL_APP_USE_COURSE = "http://139.129.10.71:31005/appintro.html";


        /**
         * 订单回执（报修处理）
         */
        public static final String URL_OREDER_DEAL_WITH = URL_BASE +
                "propertyInterface/repair/showApplicationPage.action";
        /**
         * 订单回执提交接口
         */
        public static final String URL_OREDER_DEAL_WITH_UPLOAD = URL_BASE +
                "propertyInterface/repair/updateApplicationPage.action";

        /**
         * 完成订单 比如订餐的订单
         */
        public static final String URL_FINISH_OREDER = URL_BASE +
                "propertyInterface/repair/updateApplicationDetailStates.action";

        /**
         * 咨询解答 投诉管理 回复
         */
        public static final String URL_ZI_XUN_JIE_DA_DETAILS_REPLY = URL_BASE +
                "propertyInterface/repair/updateApplicationPage.action";

        /*
         * 修改密码
         */
        public static final String URL_UPDATE_PASSWORD = URL_BASE +
                "propertyInterface/user/updatePassword.action";

        /**
         * 通讯录显示园区列表
         */
        public static final String URL_ADDRESS_PARK_LIST = URL_BASE +
                "propertyInterface/";

        /**
         * 通讯录添加联系人
         */
        public static final String URL_ADDRESS_ADD_FRIEND = URL_BASE +
                "propertyInterface/contacts/addContacts.action";

        /**
         * 查看常用联系人
         */
        public static final String URL_SEARCH_OFTEN_CONTACT = URL_BASE +
                "propertyInterface/contacts/queryContacts.action";


        /**
         * 周边商城
         */
        public static final String URL_SELLER_QUERY_SELLER = URL_BASE +
                "propertyInterface/seller/querySeller.action";

        /**
         * 周边商城下拉框
         */
        public static final String URL_SELLER_GET_SELLER_FILTRATE = URL_BASE +
                "propertyInterface/seller/getSellerFiltrate.action";
        /**
         * 商戶中的商品
         */
        public static final String URL_SELLER_GET_SELLER_BY_ID = URL_BASE +
                "propertyInterface/seller/querySellerById.action";
        /**
         * 商戶中的评价接口
         */
        public static final String URL_SELLER_GET_SELLER_EVALUATE = URL_BASE +
                "propertyInterface/seller/querySellerEvaluate.action";

        /**
         * 购物车中下单的接口
         */
        public static final String URL_SELLER_ADD_ORDER = URL_BASE +
                "propertyInterface/order/addOrder.action";


        /**
         * 收藏店铺
         */
        public static final String URL_SELLER_ADD_SELLER_COLLECT = URL_BASE +
                "propertyInterface/seller/addSellerCollect.action";

        /**
         * 取消店铺
         */
        public static final String URL_SELLER_CANCEL_SELLER_COLLECT = URL_BASE +
                "propertyInterface/seller/cancelSellerCollect.action";

        /**
         * 我的订单
         */
        public static final String URL_ORDER_QUERY_ORDER = URL_BASE +
                "propertyInterface/order/queryOrder.action";
        /**
         * 我的订单
         */
        public static final String URL_SELLER_QUERY_ORDER_DETAIL = URL_BASE +
                "propertyInterface/order/queryOrderDetail.action";
        /**
         * 我的收藏
         */
        public static final String URL_SELLER_QUERY_SELLER_COLLECT = URL_BASE +
                "propertyInterface/seller/querySellerCollect.action";
        /**
         * 添加会员常用地址
         */
        public static final String URL_RECEIVER_ADD_RECEIVER = URL_BASE +
                "propertyInterface/receiver/addReceiver.action";
        /**
         * 删除会员常用地址
         */
        public static final String URL_RECEIVER_DEL_RECEIVER = URL_BASE +
                "propertyInterface/receiver/delReceiver.action";

        /**
         * 更新会员常用地址
         */
        public static final String URL_RECEIVER_UPDATE_RECEIVER = URL_BASE +
                "propertyInterface/receiver/updateReceiver.action";
        /**
         * 会员默认地址更换
         */
        public static final String URL_RECEIVER_UPDASTE_RECEIVER_ENABLE = URL_BASE +
                "propertyInterface/receiver/updateReceiverEnable.action";

        /**
         * 会员常用地址列表
         */
        public static final String URL_RECEIVER_RECEIVER_LIST = URL_BASE +
                "propertyInterface/receiver/receiverList.action";
        /**
         * 订单状态的管理
         */
        public static final String URL_ORDER_UPDATE_ORDER_STATE = URL_BASE +
                "propertyInterface/order/updateOrderState.action";

        /**
         * 商户订单列表
         */
        public static final String URL_SELLER_QUERY_ORDER = URL_BASE +
                "propertyInterface/seller/queryOrder.action";

        /**
         * 订单评价的提交
         */
        public static final String URL_ORDER_ADD_EVALUATE = URL_BASE +
                "propertyInterface/order/addEvaluate.action";

        /**
         * 非派送  验证码消费
         */
        public static final String URL_ORDER_MEMEBER_OK = URL_BASE +
                "propertyInterface/order/memberOk.action";

        /**
         * 退款申请
         */
        public static final String URL_ORDER_REFUND = URL_BASE +
                "propertyInterface/order/refund.action";


    }

    /**
     * 数据库所存的KEY
     */
    public static class SPDate {
        /**
         * 用户ID
         */
        public static final String ID = "id";
        /**
         * 登陆名
         */
        public static final String LONGING_NAME = "loginName";
        /**
         * 登录密码
         */
        public static final String PASS_WORD = "WuYePassword";
        /**
         * 是否自动登录
         */
        public static final String LOGIN_AUTO = "WuYeAutoLogin";
        /**
         * 是否自动声音
         */
        public static final String AUTO_VOICE = "WuYeAutoVoice";
        /**
         * 是否自动登录
         */
        public static final String AUTO_VIBRATION = "WuYeAutovVibration";
        /**
         * 用户名
         */
        public static final String USER_NAME = "userName";
        /**
         * 单位信息
         */
        public static final String USER_COMPANY = "WuYeCompany";
        /**
         * 小区ID
         */
        public static final String USER_DISTRICT_ID = "districtId";

        /**
         * 极光绑定是的macId
         */
        public static final String MAC_ID = "macId";
        public static final String MAC_ID_ORIGINAL = "macId_original";
        /**
         * 是否第一次进入应用
         */
        public static final String IS_FIRST = "WuYeIsFirst";

        //经度
        public static final String LONGITHDE = "WuYeLongitude";
        //纬度
        public static final String LATITUDE = "WuYeLatitude";
    }

    /**
     * 数据库所存联系人界面的key
     */
    public static class SpAddress {
        /**
         * 联系人中paramKey
         */
        public static final String ADDRESS_KEY = "WuYeAddressKey";

    }


    public static class SAVE_MEDAR {
        /**
         * 声音
         */
        public static final String VOICE_PATH = Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/tempVoice/";
        /**
         * 拍照
         */
        public static final String SIGN_PATH = Environment
                .getExternalStorageDirectory().getPath() + "/tempHand/";
        /**
         * 签字
         */
        public static final String PIC_PATH = Environment
                .getExternalStorageDirectory().getPath() + "/tempPic/";

    }

    public static class WXAPPID {
        /**
         * 微信APPID
         */
        public static final String ID = "wx41a06671b7e5f8e9";

    }
}
