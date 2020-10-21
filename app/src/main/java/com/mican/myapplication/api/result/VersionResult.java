package com.mican.myapplication.api.result;

import java.io.Serializable;

/**
 * @name lzq
 * @class name：com.mican.view_module.contract.result
 * @class describe
 * @time 2020/10/13 9:54 AM
 * @change
 * @chang
 * @class describe
 */
public class VersionResult implements Serializable {

    /**
     * createTime : 2019-11-21 21:06:42
     * versionCode : 3
     * versionName : 0.0.3
     * url : https://rebate-test.oss-cn-hangzhou.aliyuncs.com/rebate-test/1573975447711_1920*1080.jpg
     * md5 : 4a58ecb03082750cc7e174db5be2b58b
     * isForce : 0
     * introduce : 修复bug
     */

    public String createTime;
    public Integer versionCode;
    public String versionName;
    public String url;
    public String md5;
    public Integer isForce;
    public String introduce;
}
