# rpc-study
学习rpc实现原理

需要改进之处:

1.  Server端作为一个单独的可执行jar包执行,同一执行入口

2. 使用netty来替代程序中的ServerSocket解决非阻塞问题

3. 引入新的序列化方案进一步替代程序中现有的java原生序列化方案

4. 功能模块的拆分
