# Distance Project.

> 欢迎来到**Disdtance**开黑交流软件后台。主要用于帖子讨论，游戏玩家开黑等业务为基础而建立的项目。

Cloud依赖版本管理

- SpringBoot: 2.3.12.RELEASE
- SpringCloud: Hoxton.SR12
- CloudAlibaba: 2.2.9.RELEASE

开发环境下载链接

- [Nacos 2.2.0](https://github.com/alibaba/nacos)
- [Java 8u151-b12](https://repo.huaweicloud.com/java/jdk/8u151-b12/)
- [redis](https://redis.io/)

# 模块介绍

> 主要介绍项目模块列表，每个模块的用处以及他们的位置介绍。

模块列表

- [distance](https://github.com/amaoai/open-black/tree/master/distance)
- [open-module-libs](https://github.com/amaoai/open-black/tree/master/open-module-libs)
- [open-module-libs/commons-module](https://github.com/amaoai/open-black/tree/master/open-module-libs/commons-module)
- [open-module-libs/spring-boot-module](https://github.com/amaoai/open-black/tree/master/open-module-libs/spring-boot-module)
- [open-module-libs/spring-boot-module/boot-mysql-module](https://github.com/amaoai/open-black/tree/master/open-module-libs/spring-boot-module/boot-mysql-module)
- [open-module-libs/spring-boot-module/cloud-alibaba-module](https://github.com/amaoai/open-black/tree/master/open-module-libs/spring-boot-module/cloud-alibaba-module)

----

[distance](https://github.com/amaoai/open-black/tree/master/distance) 模块为主要的业务模块，项目中所有核心业务都在该模块内开发实现。

[open-module-libs](https://github.com/amaoai/open-black/tree/master/open-module-libs) 模块主要用于整合开发工具包，以及各个依赖的管理。父模块只需要配置好开头列出开的三大框架版本即可（``SpringBoot``, ``SpringCloud``, ``CloudAlibaba``）。

[open-module-libs/spring-boot-module](https://github.com/amaoai/open-black/tree/master/open-module-libs/spring-boot-module) 模块下管理了spring的各种依赖包，例如 [boot-mysql-module](https://github.com/amaoai/open-black/tree/master/open-module-libs/spring-boot-module/boot-mysql-module) 模块它的作用就是整合了 ``mysql``, ``mybatis-plus``, ``druid`` 等框架

目的就是为了引入一个模块，就可以将所有必备的配套工具整合一并引入。
