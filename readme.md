### 软件配置操作说明：

Jdk版本1.8；

mysql版本：5.5.36,mysql安装配置详情见csdn：[MySQL5.5安装和navicat安装配置图解-CSDN博客](https://blog.csdn.net/qq_39962271/article/details/122513215)

登录用户信息表，请参照mysql,schedule下的user

项目直接导入idea后，idea会自动加载maven项目，请等待加载完毕；

加载完毕后，找到\src\main\java\one\ui\LoginInterface.java 文件，点击运行即可；

### 可能遇到的问题：

 运行后，登录时，控制台报错：如下↓

![img](./images/wps3.jpg) 

解决办法：

将目录设置为resources root 目录格式；重新运行就可以了；

![img](./images/wps4.jpg) 