package cn.injava.es.spring.domain;

import java.util.Date;

/**
 * 天狼星用户行为的日志
 *
 * Created by Green Lei on 2015/12/4 9:52.
 */
public class Log {
    private long auto_id;
    private String id;
    private String app_name;
    private String app_root_path;
    private String server_ip;
    private int server_port;
    private String uri;
    private long user_id;
    private String module;
    private String sub_module;
    private String refer;
    private String remote_ip;
    private String device_id;
    private String params;
    private String cookies;
    private String run_environment;
    private Date request_happen_time;
    private int is_intercepted;
    private Date create_time;

    public long getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(long auto_id) {
        this.auto_id = auto_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_root_path() {
        return app_root_path;
    }

    public void setApp_root_path(String app_root_path) {
        this.app_root_path = app_root_path;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public int getServer_port() {
        return server_port;
    }

    public void setServer_port(int server_port) {
        this.server_port = server_port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSub_module() {
        return sub_module;
    }

    public void setSub_module(String sub_module) {
        this.sub_module = sub_module;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getRemote_ip() {
        return remote_ip;
    }

    public void setRemote_ip(String remote_ip) {
        this.remote_ip = remote_ip;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getRun_environment() {
        return run_environment;
    }

    public void setRun_environment(String run_environment) {
        this.run_environment = run_environment;
    }

    public Date getRequest_happen_time() {
        return request_happen_time;
    }

    public void setRequest_happen_time(Date request_happen_time) {
        this.request_happen_time = request_happen_time;
    }

    public int getIs_intercepted() {
        return is_intercepted;
    }

    public void setIs_intercepted(int is_intercepted) {
        this.is_intercepted = is_intercepted;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
