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

Feign是一个声明式WebService客户端，使用Feign能让编写Web Service客户端更加简单，它的使用方法是定义一个服务接口然后在上面添加注解，Feign也支持可拔插式的编码器和解码器，Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters，Feign可以与Eureka和Ribbon组合使用以支持负载均衡。

Feign是一个声明式的Web服务客户端，让编写Web服务客户端变得非常容易，只需创建一个接口并在接口上添加注解即可。

Feign旨在使编写Java Http客户端变得更容易，前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解，现在是一个微服务接口上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。

Feign集成了Ribbon，利用Ribbon维护了Payment的服务列表信息，并且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用。

**Feign和OpenFeign两者区别**

Feign：

+ Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端。
+ Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。
+ Feign的使用方式是：使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务。

OpenFeign：

+ OpenFeign是Spring Cloud 在Feign的基础上支持了SpringMVC的注解，如@RequesMapping等等。
+ OpenFeign的@FeignClient可以解析SpringMVC的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。

**OpenFeign日志打印功能**

Feign 提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解 Feign 中 Http 请求的细节。
说白了就是对Feign接口的调用情况进行监控和输出。

日志级别

+ NONE：默认的，不显示任何日志。
+ BASIC：仅记录请求方法、URL、响应状态码及执行时间。
+ HEADERS：除了 BASIC 中定义的信息之外，还有请求和响应的头信息。
+ FULL：除了 HEADERS 中定义的信息之外，还有请求和响应的正文及元数据。

## SpringCloud Hystrix 断路器

**服务雪崩：**

多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务，这就是所谓的“扇出”，如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”。

对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和，比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加、备份队列、线程和其他系统资源紧张，导致整个系统发生更多的级联故障，这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统，所以，通常当你发现一个模块下的某个实例失败后，这时候这个模块依然还会接收流量，然后这个有问题的模块还调用了其他的模块，这样就会发生级联故障，或者叫雪崩。

Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。

“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝），向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。

**服务降级**

服务器忙，请稍后再试，不让客户端等待并立刻返回一个友好提示，fallback。

哪些情况会出发降级？

+ 程序运行异常
+ 超时
+ 服务熔断触发服务降级
+ 线程池/信号量打满也会导致服务降级

秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行。

使用@HystrixCommand注解，一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法

例如
`
@HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler", commandProperties = {
@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
})
`

是否调用兜底fallback方法是取决于 @HystrixProperty 中的 value = "4000"，只要这个value的值大于服务端8001的睡眠时间或进来就直接异常，比如说 10/0 就会走80的fallback方法，与application.yml中配置的ribbon的那个feign的超时时间以及hystrix的时间无关

**问题**
1. 每个业务都有一个兜底方法，太冗余
   @DefaultProperties(defaultFallback = “”)  统一的处理方法
2. 和业务代码耦合
   创建兜底fallback类，需要增加配置feign:hystrix:enabled: true
   
controller中超时时间配置不生效原因：

关键在于feign:hystrix:enabled: true的作用，官网解释“Feign将使用断路器包装所有方法”，也就是将@FeignClient标记的那个service接口下所有的方法进行了hystrix包装（类似于在这些方法上加了一个@HystrixCommand），这些方法会应用一个默认的超时时间为1s，所以你的service方法也有一个1s的超时时间，service1s就会报异常，controller立马进入备用方法，controller上那个3秒那超时时间就没有效果了。

**服务熔断**

类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示， 就是保险丝：服务的降级->进而熔断->恢复调用链路。

**熔断类型**
1. 熔断打开
  请求不再进行调用当前服务，内部设置时钟一般为MTTR（平均故障处理时间)，当打开时长达到所设时钟则进入半熔断状态
2. 熔断关闭
  熔断关闭不会对服务进行熔断
3. 熔断半开
  部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断

**断路器在什么情况下开始起作用**

涉及到断路器的三个重要参数：请求总数阈值、快照时间窗、错误百分比阈值

请求总数阈值：在快照时间窗内，必须满足请求总数阀值才有资格熔断。默认为20，意味着在10秒内，如果该hystrix命令的调用次数不足20次，即使所有的请求都超时或其他原因失败，断路器都不会打开

快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒【这个时间窗口期是打开短路器之后到尝试恢复，期间拒绝请求的时间】

错误百分比阈值：当请求总数在快照时间窗内超过了阀值，比如发生了30次调用，如果在这30次调用中，有15次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%阀值情况下，这时候就会将断路器打开

**断路器开启或者关闭的条件**

1. 当满足一定的阀值的时候（默认10秒内超过20个请求次数）
2. 当失败率达到一定的时候（默认10秒内超过50%的请求失败）
3. 到达以上阀值，断路器将会开启
4. 当开启的时候，所有请求都不会进行转发
5. 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发；如果成功，断路器会关闭；若失败，继续开启。重复4和5

**断路器打开之后**

再有请求调用的时候，将不会调用主逻辑，而是直接调用降级fallback，通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果

**原来的主逻辑如何恢复**

对于这一问题，hystrix也为我们实现了自动恢复功能。当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间窗内，降级逻辑是临时的成为主逻辑，当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复；如果这次请求依然有问题，断路器继续进入打开状态，休眠时间窗重新计时。

**服务限流**

秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟N个，有序进行。

## SpringCloud Gateway 网关

**三大核心概念**

1、Route(路由)
路由是构建网关的基本模块，它由ID，目标URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由

2、Predicate(断言)
参考的是Java8的java.util.function.Predicate，开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数)，如果请求与断言相匹配则进行路由

3、Filter(过滤)
指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改。

web请求，通过一些匹配条件，定位到真正的服务节点。并在这个转发过程的前后，进行一些精细化控制。
predicate就是我们的匹配条件；而filter，就可以理解为一个无所不能的拦截器。有了这两个元素，再加上目标uri，就可以实现一个具体的路由了

**Predicate的使用**

Spring Cloud Gateway将路由匹配作为Spring WebFlux HandlerMapping基础架构的一部分，Spring Cloud Gateway包括许多内置的Route Predicate工厂，所有这些Predicate都与HTTP请求的不同属性匹配，多个Route Predicate工厂可以进行组合

Spring Cloud Gateway 创建 Route 对象时，使用 RoutePredicateFactory 创建 Predicate 对象，Predicate 对象可以赋值给 Route。 Spring Cloud Gateway 包含许多内置的Route Predicate Factories。所有这些谓词都匹配HTTP请求的不同属性。多种谓词工厂可以组合，并通过逻辑and。

**断言**
after, before, between, cookie, header, host, method, path, query, readBodyPredicateFactory, remoteAddr, weight, cloudFoundryRoutService

**Filter的使用**
路由过滤器可用于修改进入的HTTP请求和返回的HTTP响应，路由过滤器只能指定路由进行使用

Spring Cloud Gateway 内置了多种路由过滤器，他们都由GatewayFilter的工厂类来产生

生命周期 pre post

种类 GatewayFilter GlobalFilter

自定义过滤器 implements GlobalFilter,Ordered ,但是自定义的过滤器报错，报错Cannot access javax.servlet.Filter。所以又在pom中引入javax.servlet依赖。重写Filter的方法，问题解决。
`
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>servlet-api</artifactId>
<version>2.5</version>
</dependency>
`

## SpringCloud Config 分布式配置中心

SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置。

SpringCloud Config分为服务端和客户端两部分。

服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息，加密/解密信息等访问接口

客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容。

**作用**

- 集中管理配置文件
- 不同环境不同配置，动态化的配置更新，分环境部署比如dev/test/prod/beta/release
- 运行期间动态调整配置，不再需要在每个服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自己的信息
- 当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
- 将配置信息以REST接口的形式暴露：post、curl访问刷新均可…

**映射**
windows下修改hosts文件，增加映射：127.0.0.1 config-3344.com

**配置读取规则**
{application} 就是应用名称，对应到配置文件上来，就是配置文件的名称部分，例如我上面创建的配置文件

{profile} 就是配置文件的版本，我们的项目有开发版本、测试环境版本、生产环境版本，对应到配置文件上来就是以 application-{profile}.yml 加以区分，例如application-dev.yml、application-sit.yml、application-prod.yml

{label} 表示 git 分支，默认是 master 分支，如果项目是以分支做区分也是可以的，那就可以通过不同的 label 来控制访问不同的配置文件了

创建config client

将读取application.yml的配置放入bootstrap.yml中，因为bootstap先加载，优先级高于application

**分布式配置的动态刷新问题**

Linux运维修改Gitee上的配置文件内容做调整
刷新3344，发现ConfigServer配置中心立刻响应
刷新3355，发现ConfigClient客户端没有任何响应
3355没有变化除非自己重启或者重新加载
难到每次运维修改配置文件，客户端都需要重启？？噩梦

**Config客户端之动态刷新**

避免每次更新配置都要重启客户端微服务3355，我们需要它能动态刷新，下面来修改3355模块

POM引入actuator监控
修改YML，暴露监控端口
@RefreshScope业务类Controller修改

执行curl -X POST "http://localhost:3355/actuator/refresh"

## SpringCloud Bus 消息总线

每次修改配置文件，都要执行刷新命令，还是不方便

Spring Cloud Bus 配合 Spring Cloud Config 使用可以实现配置的动态刷新

Spring Cloud Bus是用来将分布式系统的节点与轻量级消息系统链接起来的框架，它整合了Java的事件处理机制和消息中间件的功能。Spring Cloud Bus目前支持RabbitMQ和Kafka。

Spring Cloud Bus能管理和传播分布式系统间的消息，就像一个分布式执行器，可用于广播状态更改、事件推送等，也可以当作微服务间的通信通道。

**总线**

在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播一些需要让其他连接在该主题上的实例都知道的消息。

基本原理

ConfigClient实例都监听MQ中同一个topic(默认是springCloudBus)。当一个服务刷新数据的时候，它会把这个信息放入到Topic中，这样其它监听同一Topic的服务就能得到通知，然后去更新自身的配置。

**设计思想**

利用消息总线触发一个客户端/bus/refresh,而刷新所有客户端的配置

不合适
打破了微服务的职责单一性，因为微服务本身是业务模块，它本不应该承担配置刷新的职责
破坏了微服务各节点的对等性
有一定的局限性。例如，微服务在迁移时，它的网络地址常常会发生变化，此时如果想要做到自动刷新，那就会增加更多的修改

利用消息总线触发一个服务端ConfigServer的/bus/refresh端点，而刷新所有客户端的配置

配置中心3344添加消息总线支持

给客户端添加消息总线支持

curl -X POST "http://localhost:3344/actuator/bus-refresh"

动态刷新定点通知

curl -X POST "http://localhost:3344/actuator/bus-refresh/springcloud-config-dev:3355"

看的的教程这里使用的是curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"
但是实际上spring:application:name 并不是 config-client 原因是bootstrap里是config-client，但是相同的配置在从config中读取的application中也有，根据springboot读取顺序和覆盖逻辑，现在3355的name是springcloud-config-dev

## SpringCloud Stream 消息驱动

屏蔽底层消息中间件的差异,降低切换成本，统一消息的编程模型

Spring Cloud Stream是用于构建与共享消息传递系统连接的高度可伸缩的事件驱动微服务框架，该框架提供了一个灵活的编程模型，它建立在已经建立和熟悉的Spring熟语和最佳实践上，包括支持持久化的发布/订阅、消费组以及消息分区这三个核心概念

**为什么用Cloud Stream**

问题：为什么要引入SpringCloud Stream

举例：对于我们Java程序员来说，可能有时要使用ActiveMQ,有时要使用RabbitMQ,甚至还有RocketMQ以及Kafka，这之间的切换似乎很麻烦，我们很难，也没有太多时间去精通每一门技术，那有没有一种新技术的诞生，让我们不再关注具体MQ的细节，自动的给我们在各种MQ内切换。

简介：Spring Cloud Stream 是一个用来为微服务应用构建消息驱动能力的框架。它可以基于 Spring Boot 来创建独立的、可用于生产的 Spring 应用程序。Spring Cloud Stream 为一些供应商的消息中间件产品提供了个性化的自动化配置实现，并引入了发布-订阅、消费组、分区这三个核心概念。通过使用 Spring Cloud Stream，可以有效简化开发人员对消息中间件的使用复杂度，让系统开发人员可以有更多的精力关注于核心业务逻辑的处理。但是目前 Spring Cloud Stream 只支持 RabbitMQ 和 Kafka 的自动化配置。

一句话：屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型。

**Binder**

在没有绑定器这个概念的情况下，我们的SpringBoot应用要直接与消息中间件进行信息交互的时候，由于各消息中间件构建的初衷不同，它们的实现细节上会有较大的差异性，通过定义绑定器作为中间层，完美地实现了应用程序与消息中间件细节之间的隔离。Stream对消息中间件的进一步封装，可以做到代码层面对中间件的无感知，甚至于动态的切换中间件(rabbitmq切换为kafka)，使得微服务开发的高度解耦，服务可以关注更多自己的业务流程

Binder可以生成Binding，Binding用来绑定消息容器的生产者和消费者，它有两种类型，INPUT和OUTPUT，INPUT对应于消费者，OUTPUT对应于生产者。

Stream中的消息通信方式遵循了发布-订阅模式

**Stream标准流程套路**

Binder：很方便的连接中间件，屏蔽差异

Channel：通道，是队列Queue的一种抽象，在消息通讯系统中就是实现存储和转发的媒介，通过Channel对队列进行配置

Source和Sink：简单的可理解为参照对象是Spring Cloud Stream自身，从Stream发布消息就是输出，接受消息就是输入

**分组消费与持久化**

8802/8803作为集群都会收到消息，有重复消费问题

分组和持久化属性group

相同组会存在竞争关系，只会有一个服务消费

通过上述，解决了重复消费问题，再看看持久化

停止8802/8803并去除掉8802的分组group: atguiguA，8803的分组group: atguiguA没有去掉
8801先发送4条消息到rabbitmq
先启动8802，无分组属性配置，后台没有打出来消息
再启动8803，有分组属性配置，后台打出来了MQ上的消息

## SpringCloud Sleuth 分布式请求链路跟踪

在微服务框架中，一个由客户端发起的请求在后端系统中会经过多个不同的的服务节点调用来协同产生最后的请求结果，每一个前段请求都会形成一条复杂的分布式服务调用链路，链路中的任何一环出现高延时或错误都会引起整个请求最后的失败

Spring Cloud Sleuth提供了一套完整的服务跟踪的解决方案，在分布式系统中提供追踪解决方案并且兼容支持了zipkin

**zipkin**

SpringCloud从F版起已不需要自己构建Zipkin Server了，只需调用jar包即可

https://repo1.maven.org/maven2/io/zipkin/zipkin-server/2.24.0/

`java -jar zipkin-server-2.12.9-exec.jar`

运行控制台：http://localhost:9411/zipkin/

术语—完整的调用链路： 表示一请求链路，一条链路通过Trace Id唯一标识，Span标识发起的请求信息，各span通过parent id 关联起来

一条链路通过Trace Id唯一标识，Span标识发起的请求信息，各span通过parent id 关联起来

**名词解释：**

Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识
span：表示调用链路来源，通俗的理解span就是一次请求信息

## SpringCloud Alibaba Nacos服务注册和配置中心
