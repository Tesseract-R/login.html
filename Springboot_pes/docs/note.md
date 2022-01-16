JDK 8

# `Gradle`

1. 使用Spring Initializer创建项目 http://start.spring.io

2. （在根目录）Gradle编译项目 > gradle build

3. 启动项目 > java -jar …/.jar

Gradle Wrapper: 统一所有人的项目配置



<hr>

`config`注解往往与`bean`注解结合使用，使用`SpringIOC`路径为`bean`的来源，告诉spring注册

# `Thymeleaf`

- Java模板引擎，能够处理HTML、XML、JavaScript、CSS，类似JSP、Freemarker
- 自然模板 - 原型即页面
- 语法优雅易懂
- 遵从Web标准，支持HTML5

标准方言：

``` html
<span th:text="...">
<span data-th-text="...">
```

- 标准表达式
    - 变量：${…}
    - 消息：#{…}
    - 选择：*{…} // 在当前选择的对象而不是整个上下文变量映射上执行
    - 链接：@{…}
        - 绝对路径
        - 相对路径@{../}
        - 相对于服务器@{~/}
        - 相对于协议@{//}
    - 分段：th:insert 或 th:replace
        - insert：简单插入指定的片段作为正文的主标签
        - replace：用指定实际片段来替换其主标签
        - include：不插入片段，只插入此片段的内容

- 设置属性值
    - th:attr
    - 布尔值 th:selected=false

- 迭代器
    - th:each

- 条件语句
    - th:if 、th:unless
    - th:switch + th:case

- 模板布局

    - th:fragment

- 属性优先级

    - 模板 > 迭代器 > 条件语句 > 本地变量 > 属性 > 特定属性 > Text > Remove

- 注释

    -   ```html
        <--!/*   */-->
        ```


- 内联

    - [[…]] text
    - [(…)] utext
    - 禁用内联表达式：th:inline=“none”

- 基本对象

- 工具对象

# 架构

按业务功能进行分层

- 表示层（控制器+视图）
    - Controller
    - View
- 业务层（模型）
    - Entity
    - VO
    - Service
- 数据访问层（DAO）
    - Repository

# `Bootstrap`

常用组件、样式：

- Typography
- Table
- Form
- Button
- Dropdown
- Button Group
- Input Group
- Navbar
- Pagination
- Tag
- Alert
- Modal Dialog
- Progress Bar
- List Group
- Card
- Tooltip

# 权限管理（Spring Security）

- 显式访问控制
    - 与单个功能联系
    - 更灵活
- 隐式访问控制
    - 与角色联系

























