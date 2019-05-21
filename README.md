**项目说明** 
- 采用SpringBoot、MyBatis、Shiro框架，开发的一套权限系统，极低门槛，拿来即用。设计之初，就非常注重安全性，为企业系统保驾护航，让一切都变得如此简单。
- 支持MySQL
<br>

**具有如下特点** 
- 页面交互使用Vue2.x，极大的提高了开发效率
- 引入swagger文档支持，方便编写API接口文档
<br>

**项目结构**
```
VisionPlatform
    ├─  common  公共模块
    │
    ├─  api  API服务（后端开放给前端页面的api，也可以开发给移动端使用过的api，都写在这里）
    │    ├─annotation   可自定义注解
    │    ├─config       配置
    │    ├─controller   控制层
    │    ├─dao          数据访问层
    │    ├─entity       实体对象
    │    ├─exception    异常定义
    │    ├─form         表单
    │    ├─interception 拦截器
    │    ├─resolver     解析器
    │    ├─service      服务层
    │    │ 
    │    └─resources    资源文件
    │        ├─mapper   MyBatis文件
    │        └─application.yml...   一些配置文件
    │ 
    ├─  web  前端页面展示模块
    │    ├─job     服务于前端的后端页面
    │    ├─config       配置
    │    ├─controller   控制层
    │    ├─dao          数据访问层
    │    ├─entity       实体对象
    │    ├─service      业务逻辑层
    │    ├─utils        工具包
    │    │
    │    └─resources 
    │        ├─mapper   MyBatis文件
    │        ├─statics  静态资源
    │        ├─template 系统页面
    │        │    ├─modules      模块页面
    │        │    ├─index.html   AdminLTE主题风格（默认主题）
    │        │    └─index1.html  Layui主题风格
    │        └─application.yml   全局配置文件
    │
    └─  Doc  文档存放目录

```

<br>

 **技术选型：** 
- 核心框架：Spring Boot 2.1
- 安全框架：Apache Shiro 1.4
- 视图框架：Spring MVC 5.0
- 持久层框架：MyBatis 3.5
- 数据库连接池：Druid 1.1
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x
<br>

**软件需求** 
- JDK1.8
- MySQL5.5+
- Maven3.0+
<br>

**本地部署** 
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库renren_security，数据库编码为UTF-8
- 执行db/mysql.sql文件，初始化数据【按需导入表结构及数据】
- 修改application-dev.yml文件，更新MySQL账号和密码
- 在renren-security目录下，执行mvn clean install  

***【web】***
- Eclipse、IDEA运行WebApplication.java，则可启动项目【web】
- web访问路径：http://localhost:8080/VisionPlatform_web
- web的api文档的访问地址：http://localhost:8080/VisionPlatform_web/swagger-ui.html

***【api】***
- Eclipse、IDEA运行ApiApplication.java，则可启动项目【api】
- api的文档访问路径：http://localhost:8081/renren-api/swagger-ui.html

***【common】***  
- common模块提供基础工具支持，不可直接运行。

**参考资料** 
- 开发文档：https://www.renren.io/guide/security
- 官方社区：https://www.renren.io/community