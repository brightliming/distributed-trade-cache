### 服务模块

KunPeng模块：

服务名称 | 端口 | 描述
---|---|---
KunPeng-Auth| 8101| 微服务认证服务器 
KunPeng-Server-Meeting| 8201 | 微服务子系统，视频会议模块
KunPeng-Server-Job|8204 | 微服务子系统，任务调度模块
KunPeng-Gateway|8301|微服务网关

第三方模块：

服务名称 | 端口 | 描述
---|---|---
Nacos| 8001 |注册中心，配置中心 
MySQL| 3306 |MySQL 数据库 
Redis| 6379 | K-V 缓存数据库 
Elasticsearch|9200 | 日志存储
Logstash|4560|日志收集
Kibana|5601|日志展示
Prometheus|8403~8409|Prometheus APM
Skywalking|11800、12800、8080|Skywalking APM
Sentinel|80|sentinel限流控制台

### 目录结构
```
├─kunpeng-apm                        ------ 微服务APM模块
│  ├─sentinel                     ------ alibaba sentinel sentinel控制台相关配置, docker文件
│  ├─prometheus-grafana           ------ prometheus grafana apm相关配置，docker文件
│  └─skywalking-elk               ------ skywalking elk相关配置，docker文件
├─kunpeng-auth                       ------ 微服务认证服务器
├─kunpeng-cloud                      ------ 整个项目的父模块
│  ├─sql                          ------ SQL脚本
│  └─docker compose               ------ 项目相关docker compose文件
├─kunpeng-common                     ------ 通用模块
│  ├─kunpeng-common-core                   ------ 系统核心依赖包
│  ├─kunpeng-common-datasource-starter     ------ 系统数据库自动装配starter
│  ├─kunpeng-common-doc                    ------ 文档模块的核心依赖包
│  ├─kunpeng-common-doc-gateway-starter    ------ 网关聚合微服务子系统api文档自动装配starter
│  ├─kunpeng-common-doc-starter            ------ 微服务子系统api文档自动装配starter
│  ├─kunpeng-common-redis-starter          ------ 系统Redis自动装配starter
│  ├─kunpeng-common-security-starter       ------ 微服务子系统安全配置自动装配starter
│  ├─kunpeng-common-sentinel               ------ 微服务子限流降级配置自动装配starter
│  └─kunpeng-common-sentinel-gateway       ------ 微服务子限流降级配置自动装配starter
├─kunpeng-gateway                    ------ 微服务网关
└─kunpeng-server                     ------ 微服务子系统
   ├─kunpeng-server-meeting            ------ 微服务子系统系统核心模块
   └─kunpeng-server-job                ------ 微服务子系统任务调度模块
