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
    conditions REGEXP '\\bDIAB1.*'
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