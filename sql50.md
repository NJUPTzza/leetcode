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