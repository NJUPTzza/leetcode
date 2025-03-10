# sql50
## 查询
### 1757.可回收且低脂的产品
``` sql
SELECT 
    product_id 
FROM 
    Products 
WHERE 
    low_fats = 'Y' AND recyclable = 'Y'
;
```

### 584.寻找用户推荐人
``` sql
-- 判断null值，必须用 IS NULL 或者 IS NOT NULL
SELECT
    name
FROM
    Customer
WHERE
    referee_id != 2 OR referee_id IS NULL
;
```

### 595.大的国家
``` sql
SELECT
    name,
    population,
    area
FROM
    World
WHERE
    area >= 3000000 OR population >= 25000000
;
```

### 1148.文章浏览 I
``` sql
SELECT
    -- DISTINCT 去重
    DISTINCT author_id AS id 
FROM
    Views
WHERE
    author_id = viewer_id
ORDER BY
    id
;
```

### 1683.无效的推文
``` sql
SELECT
    tweet_id
FROM
    Tweets
WHERE
    -- CHAR_LENGTH() 返回 字符串中字符的实际个数
    -- LENGTH() 返回 字符串在存储时所占用的字节数
    CHAR_LENGTH(content) > 15
;
```

## 连接
### 1378.使用唯一标识码替换员工ID
``` sql
-- LEFT JOIN 展示前表所有的内容，前表没有后表有的则不展示，前表有后表没有的则填充null并展示
-- RIGHT JOIN 正好相反
SELECT 
    b.unique_id, a.name
FROM
    Employees as a
LEFT JOIN
    EmployeeUNI as b
ON
    a.id = b.id
;
```

### 1068.产品销售分析 I
``` sql
SELECT
    p.product_name, 
    s.year,
    s.price
FROM
    Product AS p
-- JOIN 就是，只展示两个表相互匹配的行，不会出现有一个表没有匹配的行就用NULL来替代
JOIN
    Sales AS s
ON
    s.product_id = p.product_id
;
``` 

### 1581.进店却未进行过交易的顾客
``` sql
SELECT
    customer_id,
    COUNT(customer_id) AS count_no_trans
FROM
    Visits AS v
LEFT JOIN
    Transactions AS t
ON
    v.visit_id = t.visit_id
WHERE
    transaction_id IS NULL
GROUP BY
    customer_id
;

``` 

### 197.上升的温度
``` sql
SELECT
    w2.id AS Id
FROM
    Weather AS w1,
    Weather AS w2
WHERE
    -- DATEDIFF() 用于计算日期之间的差值
    DATEDIFF(w2.recordDate, w1.recordDate) = 1 AND w2.temperature > w1.temperature
;
```

### 1661.每台机器的进程平均运行时间
``` sql
SELECT
    a1.machine_id,
    ROUND(AVG(a2.timestamp - a1.timestamp), 3) as processing_time
FROM 
-- 用逗号来隐式内连接时，用 WHERE 不用 ON，显式用 JOIN 就得用 ON
    Activity AS a1
JOIN
    Activity AS a2
ON
    a1.machine_id = a2.machine_id AND a1.process_id = a2.process_id AND a1.activity_type = 'start' AND a2.activity_type = 'end'
GROUP BY
    machine_id
;
```

### 577.员工奖金
``` sql
SELECT
    e.name,
    b.bonus
FROM
    Employee AS e
LEFT JOIN
    Bonus AS b
ON
    e.empId = b.empId
WHERE
    b.bonus < 1000 OR b.bonus IS NULL
;
```

## 聚合函数
### 620.有趣的电影
``` sql
SELECT 
    *
FROM
    cinema
WHERE
    -- mod(id, 2) 表示 id % 2
    description != 'boring' AND mod(id, 2) = 1
ORDER BY
    rating DESC
;
```

### 1251.平均售价
``` sql
SELECT
    product_id,
    -- SUM(x) 计算 x 列的总和
    -- ROUND() 四舍五入到小数点后某位
    -- IFNULL 检查是否为NULL，是NULL就返回0，不是NULL就返回值
    IFNULL(ROUND(SUM(sales) / SUM(units), 2), 0) AS average_price
-- 表一定要有别名 比如这里 FROM() T 这个 T 一定要加
FROM (
    SELECT
        p.product_id AS product_id,
        p.price * u.units AS sales,
        u.units AS units
    FROM    
        Prices AS p
    LEFT JOIN
        UnitsSold AS u 
    ON
        p.product_id = u.product_id
    AND
        -- 计算时间可以这样，用 BETWEEN AND
        u.purchase_date BETWEEN p.start_date AND p.end_date
) T
GROUP BY
    product_id
;
```

### 1075.项目员工 I
``` sql
SELECT
    project_id,
    ROUND(AVG(experience_years), 2) AS average_years
FROM
    -- 两张表用逗号连接，其实就是隐式内连接，就是两边都不补充NULL
    Project AS p,
    Employee AS e
WHERE
    p.employee_id = e.employee_id
GROUP BY
    project_id
;
```

### 1633.各赛事的用户注册率
``` sql
SELECT
    contest_id,
    -- 注意COUNT()里就算是false也会计数
    -- 所以如果是两表连接的JOIN写法，然后用COUNT(user_id)，即使Users表中只有3个字段
    -- 也会按照Register表中的字段数来统计，因为操作的是两表JOIN后的表
    ROUND(COUNT(user_id) * 100 / (SELECT COUNT(*) FROM Users), 2) AS percentage
FROM
    Register 
GROUP BY
    contest_id
ORDER BY
    -- ASC为升序（默认是升序），DESC是降序
    percentage DESC,
    contest_id
;
```

### 1211.查询结果的质量和占比
``` sql
SELECT
    query_name,
    ROUND(AVG(rating / position), 2) AS quality,
    -- IF(condition, value_if_true, value_if_false)
    ROUND(SUM(IF(rating < 3, 1, 0)) * 100/ COUNT(*), 2) AS poor_query_percentage
FROM
    Queries
GROUP BY
    query_name
;
```

## 排序和分组
### 2356.每位教师所教授的科目种类的数量
``` sql
SELECT
    -- COUNT() 是一个聚合函数，用于统计满足特定条件的记录数量
    -- DISTINCT 关键字用于去除重复值
    -- COUNT(DISTINCT subject_id) 会统计每个教师所教授的不同学科的数量，即对于同一个教师，重复的学科只会被计算一次
    teacher_id, COUNT(DISTINCT subject_id) AS cnt
FROM
    Teacher
-- GROUP BY 子句用于将查询结果按照指定的列进行分组
GROUP BY
    teacher_id
```

### 1141.查询近30天活跃用户数
``` sql 
SELECT
    activity_date AS day,
    -- 因为加了 activity_date AS day 和 GROUP BY activity_date 所以COUNT统计的是每天的
    -- DISTINCT() 去重
    COUNT(DISTINCT(user_id)) AS active_users
FROM
    activity
WHERE
    -- DATE_SUB() 用于从一个日期或时间值中减去指定的时间间隔，从而得到一个新的日期或时间值
    activity_date BETWEEN DATE_SUB('2019-07-27', INTERVAL 29 DAY) AND '2019-07-27'
GROUP BY
    activity_date
;
```

### 1084.销售分析 III
``` sql
-- SELECT 子句中的字段如果两个表都有，必须指定表名，否则无法判断你要哪个表中的字段
SELECT
    s.product_id,
    p.product_name
FROM
    Product AS p
JOIN
    Sales AS s
ON
    p.product_id = s.product_id
GROUP BY
    s.product_id
HAVING 
    MIN(sale_date) >= '2019-01-01' AND MAX(sale_date) <= '2019-03-31'
;
```

### 596.超过 5 名学生的课
``` sql
SELECT
    class
FROM
    -- 这相当于新建一张表
    -- 表只有class和对应class有几个学生，这两个字段
    (
        SELECT
            class, COUNT(student) AS num
        FROM   
            Courses
        GROUP BY
            class
    ) AS c 
WHERE
    num >= 5
;
```

## 高级查询和连接
### 1731. 每位经理的下属员工数量
``` sql
SELECT 
    b.employee_id, 
    b.name,
    -- COUNT(*) 统计的是满足 a.reports_to = b.employee_id 这个连接条件的记录总数
    COUNT(*) AS reports_count,
    -- ROUND 函数用于对一个数值进行四舍五入操作，将其保留指定的小数位数
    -- AVG 是一个聚合函数，用于计算一组数值的平均值
    ROUND(AVG(a.age), 0) AS average_age
FROM
    Employees a
JOIN
    Employees b
ON
    a.reports_to = b.employee_id
GROUP BY
    b.employee_id
ORDER BY
    b.employee_id
;
```

### 1789.员工的直属部门
``` sql
-- 上半部分是求 (当员工只加入一个部门的时候，那这个部门将默认为他的直属部门，虽然表记录的值为'N')
SELECT
    employee_id, department_id
FROM
    Employee
GROUP BY
    employee_id
-- HAVING 是和 GROUP BY 一起用的，只返回符合 HAVING 条件的分组
-- 即这里只返回只有一个 department_id 的人
HAVING
    COUNT(department_id) = 1
-- UNION 是将两个SELECT数据合成一个结果集一起返回，并会去掉这两堆数据中重复的数据
UNION
-- 下半部分是求 (当一个员工加入超过一个部门的时候，他需要决定哪个部门是他的直属部门)
SELECT
    employee_id, department_id
FROM
    Employee
WHERE
    primary_flag = 'Y'
;
```

### 610.判断三角形
``` sql
SELECT
    x,
    y,
    z,
    -- CASE 满足哪一个条件，就走哪一条
    CASE
        WHEN x + y > z AND x + z > y AND y + z > x THEN 'Yes'
        ELSE 'No'
    END AS triangle
FROM
    triangle
;
``` 

### 180.连续出现的数字
``` sql
SELECT
    DISTINCT l1.num AS ConsecutiveNums
FROM
    Logs AS l1,
    Logs AS l2,
    Logs AS l3
WHERE
    l1.id = l2.id - 1 AND l2.id = l3.id - 1 AND l1.num = l2.num AND l2.num = l3.num
;
```

## 子查询
### 1978.上级经理已离职的公司员工
``` sql
SELECT 
    employee_id
FROM
    Employees
WHERE
    salary < 30000
AND 
    manager_id NOT IN (SELECT employee_id FROM Employees)
ORDER BY
    employee_id
```

### 626.换座位
``` sql
SELECT
    -- CASE 就是执行下面符合 WHEN 条件的语句
    (CASE
        -- 奇数id且id不等于记录总数，即不是最后一个id，则把 id + 1
        WHEN MOD(id, 2) != 0 AND counts != id THEN id + 1
        -- id正好是最后一个id，则不变
        WHEN MOD(id, 2) != 0 AND counts = id THEN id
        -- 偶数id，则把 id - 1
        ELSE id - 1
    END) AS id,
    student
FROM
    seat,
    -- 这样操作其实就是把seat表和seat_counts表组合，即seat每行都加一个counts字段，且counts都是一样的，是seat记录总数
    (SELECT
        COUNT(*) AS counts
    FROM
        seat
    ) AS seat_counts
ORDER BY
    -- ASC 升序
    id ASC
;
```

### 1341.电影评分
``` sql
-- 评论电影数量最多且字典序较小的用户名
(
    SELECT
        u.name AS results
    FROM
        MovieRating AS mr
    JOIN
        Users AS u 
    ON
        mr.user_id = u.user_id
    GROUP BY
        mr.user_id
    ORDER BY
        COUNT(mr.user_id) DESC,
        u.name
    LIMIT 1
)
-- UNION 是将两个查询的结果合并后去重，而 UNION ALL 则是简单的合并，不会去重
UNION ALL
-- 2020年2月份平均评分最高且字典序较小的电影名
(
    SELECT
        m.title AS results
    FROM
        MovieRating AS mr
    JOIN
        Movies AS m 
    ON
        mr.movie_id = m.movie_id
    WHERE
        mr.created_at >= '2020-02-01' AND mr.created_at < '2020-03-01'
    GROUP BY
        mr.movie_id
    ORDER BY
        AVG(mr.rating) DESC,
        m.title
    LIMIT 1
)
;
```
### 1321.餐馆营业额变化增长
``` sql
-- 该题本质是新建两张表，最里层的表是每天的消费总额，中间层表为每7天的消费总额
SELECT
    visited_on,
    amount,
    ROUND(amount / 7, 2) AS average_amount
FROM
    -- L表，统计每天及其前6天的消费总额
    (
        SELECT
            visited_on,
            -- OVER() 表示定义一个窗口，一般跟在一个函数后面，表示函数只处理这一个窗口中的数据
            -- 下面这句话表示统计每一行及其前面六行，amount的总和
            -- 如果前面不足六行，就统计足够的最多行数
            SUM(amount) OVER (ORDER BY visited_on ROWS 6 PRECEDING) AS amount
        FROM   
            -- T表，统计每一天的消费总额
            (
                SELECT 
                    visited_on,
                    SUM(amount) AS amount
                FROM
                    Customer
                GROUP BY
                    visited_on
            ) AS T
    ) AS L
WHERE
    -- MIN() 函数返回指定列中的最小值
    DATEDIFF(visited_on, (SELECT MIN(visited_on) FROM Customer)) >= 6
;
```

## 高级字符串函数/正则表达式/子句
### 1667.修复表中的名字
``` sql
SELECT
    user_id,
    -- CONCAT 函数用于将多个字符串连接成一个字符串
    -- UPPER 函数用于将字符串中的所有字母转换为大写形式
    -- LEFT 函数用于从字符串的左侧开始截取指定长度的子字符串
    -- LOWER 函数用于将字符串中的所有字母转换为小写形式
    -- RIGHT 函数用于从字符串的右侧开始截取指定长度的子字符串
    -- LENGTH 函数用于返回字符串的长度
    CONCAT(UPPER(LEFT(name, 1)), LOWER(RIGHT(name, LENGTH(name) - 1))) AS name
FROM
    Users
ORDER BY
    user_id
;
```

### 1527.患某种疾病的患者
- 正则表达式方法
``` sql
SELECT
    patient_id,
    patient_name,
    conditions
FROM
    Patients
WHERE
    -- REGEXP 正则表达式操作符
    conditions REGEXP '^DIAB1|\\sDIAB1'
;
```

- 模糊查询
``` sql
SELECT 
    patient_id, patient_name, conditions
FROM 
    Patients
WHERE 
    conditions LIKE 'DIAB1%' OR conditions LIKE '% DIAB1%';
```

### 196.删除重复的电子邮箱
``` sql
DELETE
    p1
FROM
    Person p1,
    Person p2
WHERE
    p1.email = p2.email AND p1.id > p2.id
;
``` 

### 176.第二高的薪水     
``` sql
SELECT
    -- 不加IFNULL也可以，因为子查询如果没有返回值就会返回NULL
    IFNULL(
        (
            SELECT 
                DISTINCT salary
            FROM 
                Employee
            ORDER BY
                salary DESC
            -- LIMIT 1 只返回一条记录
            -- OFFSET 1 跳过第一条记录
            LIMIT 1 OFFSET 1
        ),
        NULL
    ) AS SecondHighestSalary
```