# flowpath
仿钉钉自定义流程，基于flowable实现自定义流程，抄送，加签，条件，回退自定义，web工作流程自定义，java+vue+flowable+mysql+springboot

# 1、演示地址
访问链接：http://47.121.199.169:8081

# 2、内置功能
  1、流程模型的新建、修改、展示
  2、流程中提供不同的人员审核方式(指定人员、角色、组)、会签、或签、抄送、监听、条件功能
  3、流程中审核操作提供拒绝、退回、同意、已阅功能，以及加签功能。
  4、已办流程、待办流程、我的流程的数据查询
# 3、拓展功能
提供不同审核类型查询审核人信息、回退操作以及终止操作时回调方法，可供业务层按需使用



## 组件集成
# 1、环境要求
Jdk >= 1.8


# 2、引入pom
<dependency>
    <groupId>com.figo.common.flowable</groupId>
    <artifactId>figo-common-flowable-starter</artifactId>
    <version>1.0.3</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/resources/lib/figo-common-flowable-starter-1.0.3.jar</systemPath>
</dependency>

# 3、数据库脚本
导入组件项目中sql脚本，位置：src/main/resources/sql/flowable_init.sql

##  组件使用
# 主要分为两个部分，
1、前后端通用功能，集成前后端后可直接使用
2、开放接口，根据业务需要与前端联调对接或者集成至业务中封装

# 1、通用功能
集成前后端组件后，提供现成的流程定义(流程模型的绘制以及增删改查)相关的功能；如下图所示；


# 2、开放功能
2.1流程的发起、撤回
流程的发起、撤回根据实际业务绑定表单，设置全局变量
2.2 流程的操作
审核操作的拒绝、退回、同意、已阅；不同用户的发起流程列表、已办列表、待办列表提供了封装好的controller，根据业务需要使用；如果controller不满足业务，可通过调用对应的service (IFlowTaskService) 与业务结合完成逻辑;如下图：


# 2.3扩展功能
**IFlowBaseService接口，提供三个基础通用接口**
1、根据type审核人信息获取 businessUserList(Integer type)
2、根据key和type获取名称 findUserByKeyAndType(List<String> key, Integer type)
参数key为：   需要查询数据的主键
参数type为：  用户信息，1表示用户列表；2表示角色；3表示组
以上两个方法必须实现并且完善type为1、2、3的逻辑；

# 3 、查询业务分类 findBizDict()
例如itss里面根据不同类型做不同的业务操作
**IFlowBusinessService接口，提供两个业务接口**
1、回退操作时触发方法 taskReturn(Task task, Map<String, Object> variables)
2、终止操作时触发方法 stopProcess(Task task, Map<String, Object> variables)
参数task：当前任务数据 
参数variables： 发起流程时自定义存储的数据
以上两个方法根据实际业务实现响应的逻辑

# 3、其他
数据库中提供分类字典表(act_category_dic)，默认数据：请假和报销；根据业务层需要在字典表增删改字典表数据。


