# 流程

**_学习方式：约定 > 配置 > 编码(启动注解)_**

## 1. RestTemplate

RestTemplate提供了多种便捷访问远程Http服务的方法， 是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集。

使用restTemplate访问restful接口非常的简单粗暴无脑。

+ url：REST请求地址。
+ requestMap：请求参数。
+ ResponseBean.class：HTTP响应转换被转换成的对象类型。

## 2. SpringCloud Eureka 服务注册与发现

**服务治理**

Spring Cloud 封装了 Netflix 公司开发的 Eureka 模块来实现服务治理。

在传统的rpc远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，管理服务于服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册。

**服务注册**

Eureka采用了CS的设计架构，Eureka Server 作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用 Eureka的客户端连接到 Eureka Server并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控系统中各个微服务是否正常运行。

在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息 比如 服务地址通讯地址等以别名方式注册到注册中心上。另一方（消费者|服务提供者），以该别名的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用RPC远程调用框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的一个依赖关系(服务治理概念)。在任何rpc远程框架中，都会有一个注册中心(存放服务地址相关信息(接口地址))。

**Eureka两组件**

Eureka包含两个组件：Eureka Server和Eureka Client

+ Eureka Server 提供服务注册服务

各个微服务节点通过配置启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。

+ EurekaClient 通过注册中心进行访问

是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）。

## 3。Consul

Consul 是一套开源的分布式服务发现和配置管理系统，由 HashiCorp 公司用 Go 语言开发。

提供了微服务系统中的服务治理、配置中心、控制总线等功能。这些功能中的每一个都可以根据需要单独使用，也可以一起使用以构建全方位的服务网格，总之Consul提供了一种完整的服务网格解决方案。

它具有很多优点。包括： 基于 raft 协议，比较简洁； 支持健康检查, 同时支持 HTTP 和 DNS 协议 支持跨数据中心的 WAN 集群 提供图形界面 跨平台，支持 Linux、Mac、Windows。

+ 服务发现：提供HTTP和DNS两种发现方式
+ 健康监测：支持多种方式，HTTP、TCP、Docker、Shell脚本定制化监控
+ KV存储：Key、Value的存储方式
+ 多数据中心：Consul支持多数据中心
+ 可视化Web界面

使用步骤：
1. 下载：https://www.consul.io/downloads.html
2. 下载完成后只有一个consul.exe文件，硬盘路径下双击运行，查看版本号信息
3. 直接在软件目录中输入 cmd 进入命令控制台
4. `consul --version` 查看 consul 版本号
5. `consul agent -dev` 启动
6. 通过地址可以访问Consul的首页：http://localhost:8500

_**CAP**_

C：Consistency（强一致性）

A：Availability（可用性）

P：Partition tolerance（分区容错性）

## SpringCloud Ribbon 负载均衡服务调用

Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。

简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。

LB负载均衡(Load Balance)是什么？

简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA（高可用），常见的负载均衡有软件Nginx，LVS，硬件 F5等

Ribbon本地负载均衡客户端 VS Nginx服务端负载均衡区别？

Nginx是服务器负载均衡，客户端所有请求都会交给nginx，然后由nginx实现转发请求。即负载均衡是由服务端实现的。

Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。

集中式LB：

即在服务的消费方和提供方之间使用独立的LB设施(可以是硬件，如F5, 也可以是软件，如nginx), 由该设施负责把访问请求通过某种策略转发至服务的提供方。

进程内LB：

将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。

Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。

Ribbon在工作时分成两步：

第一步先选择 EurekaServer ,它优先选择在同一个区域内负载较少的server。

第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址；其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权。

总结：Ribbon其实就是一个软负载均衡的客户端组件，他可以和其他所需请求的客户端结合使用，和eureka结合只是其中的一个实例。

**注意:** POM 中既可以加 ribbon 的依赖也可以不用加，因为 eureka 已经集成了 ribbon

**核心组件：IRule**

警告：这个自定义配置类不能放在@ComponentScan所扫描的当前包下以及子包下，否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊化定制的目的了。

**Ribbon负载均衡算法**

负载均衡算法：rest接口第几次请求数 % 服务器集群总数量 = 实际调用服务器位置下标 ，每次服务重启动后rest接口计数从1开始.

`List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
List [0] instances = 127.0.0.1:8002
List [1] instances = 127.0.0.1:8001`

8001+ 8002 组合成为集群，它们共计2台机器，集群总数为2， 按照轮询算法原理：

当总请求数为1时：1 % 2 =1 对应下标位置为1 ，则获得服务地址为127.0.0.1:8001

当总请求数位2时：2 % 2 =0 对应下标位置为0 ，则获得服务地址为127.0.0.1:8002

当总请求数位3时：3 % 2 =1 对应下标位置为1 ，则获得服务地址为127.0.0.1:8001

当总请求数位4时：4 % 2 =0 对应下标位置为0 ，则获得服务地址为127.0.0.1:8002

如此类推… 

## SpringCloud OpenFeign 服务接口调用



