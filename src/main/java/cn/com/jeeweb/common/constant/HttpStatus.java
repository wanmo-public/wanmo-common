package cn.com.jeeweb.common.constant;

import java.net.HttpURLConnection;

/**
 * Http 返回状态码
 */
public class HttpStatus {
    /**
     * 操作成功 200
     */
    public static final int SUCCESS = HttpURLConnection.HTTP_OK;

    /**
     * 对象创建成功 201
     */
    public static final int CREATED = HttpURLConnection.HTTP_CREATED;

    /**
     * 请求已经被接受 202
     */
    public static final int ACCEPTED = HttpURLConnection.HTTP_ACCEPTED;

    /**
     * 操作已经执行成功，但是没有返回数据 204
     */
    public static final int NO_CONTENT = HttpURLConnection.HTTP_NO_CONTENT;

    /**
     * 资源已被移除 301
     */
    public static final int MOVED_PERM = HttpURLConnection.HTTP_MOVED_PERM;

    /**
     * 重定向 303
     */
    public static final int SEE_OTHER = HttpURLConnection.HTTP_SEE_OTHER;

    /**
     * 资源没有被修改 304
     */
    public static final int NOT_MODIFIED = HttpURLConnection.HTTP_NOT_MODIFIED;

    /**
     * 参数列表错误（缺少，格式不匹配） 400
     */
    public static final int BAD_REQUEST = HttpURLConnection.HTTP_BAD_REQUEST;

    /**
     * 未授权 401
     */
    public static final int UNAUTHORIZED = HttpURLConnection.HTTP_UNAUTHORIZED;

    /**
     * 访问受限，授权过期 403
     */
    public static final int FORBIDDEN = HttpURLConnection.HTTP_FORBIDDEN;

    /**
     * 资源，服务未找到 404
     */
    public static final int NOT_FOUND = HttpURLConnection.HTTP_NOT_FOUND;

    /**
     * 不允许的http方法 405
     */
    public static final int BAD_METHOD = HttpURLConnection.HTTP_BAD_METHOD;

    /**
     * 资源冲突，或者资源被锁 409
     */
    public static final int CONFLICT = HttpURLConnection.HTTP_CONFLICT;

    /**
     * 不支持的数据，媒体类型 415
     */
    public static final int UNSUPPORTED_TYPE = HttpURLConnection.HTTP_UNSUPPORTED_TYPE;

    /**
     * 系统内部错误 500
     */
    public static final int INTERNAL_ERROR = HttpURLConnection.HTTP_INTERNAL_ERROR;

    /**
     * 接口未实现 501
     */
    public static final int NOT_IMPLEMENTED = HttpURLConnection.HTTP_NOT_IMPLEMENTED;
}
