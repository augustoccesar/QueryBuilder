# QueryBuilder 
[![Build Status](https://travis-ci.org/augustoccesar/QueryBuilder.svg?branch=master)](https://travis-ci.org/augustoccesar/QueryBuilder) 
[![Coverage Status](https://coveralls.io/repos/github/augustoccesar/QueryBuilder/badge.svg?branch=master)](https://coveralls.io/github/augustoccesar/QueryBuilder?branch=master) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f203baba091846e3922fabf893f569e6?branch=master)](https://www.codacy.com/app/augustoccesar/QueryBuilder?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=augustoccesar/QueryBuilder&amp;utm_campaign=Badge_Grade) 
[![GitHub version](https://badge.fury.io/gh/augustoccesar%2FQueryBuilder.svg)](https://badge.fury.io/gh/augustoccesar%2FQueryBuilder)
[![Join the chat at https://gitter.im/QueryBuilder](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/QueryBuilder/Lobby?utm_source=share-link&utm_medium=link&utm_campaign=share-link)

Guide
-----

It all starts with the `SelectBuilder`. When a `SelectBuilder` is created you can start nesting methods to
build the queries.

#### Fields ####

##### `.select()` #####
Select the fields that are going to be queried.
```java
.select("{u}name")                  // Generates 'SELECT u.name AS u_name'
.select("{u}name{custom_alias}")    // Generates 'SELECT u.name AS custom_alias'
.select("{_}name")                  // Generates 'SELECT name'
.select("{u}name{_}")               // Generates 'SELECT u.name'
.select("{u}*name")                 // Generates 'SELECT DISTINCT u.name AS u_name'
```

#### Tables ####

##### `.from()` #####
```java
.from("users{u}")   // Generates 'FROM users u'
.from("users{_}")   // Generates 'FROM users'
.from("users u")    // Generates 'FROM users u'
.from("users")      // Generates 'FROM users'
```

#### Conditions ####

##### `.where()` #####
```java
.where("{u}name", Comparison.EQUALS, "Augusto") // Generates 'WHERE u.name = 'Augusto''
.where(Condition.eq("{u}name", "Augusto"))      // Generates 'WHERE u.name = 'Augusto''
.where(
        Condition.or(
                Condition.eq("{u}name", "Augusto"),
                Condition.eq("{u}las_name", "Silva")
        )
)                                               // Generates 'WHERE u.name = 'Augusto' OR u.last_name = 'Silva''
```
All possible `Condition` builders are defined in [Condition](#condition) description.

#### Joins ####

Possible [Join](#join) builders are listed in his description.

##### `.join()` #####
```java
.join(new Join(Join.INNER).table("users_profile{up}").on("{u}id", "{up}user_id")) // Generates 'INNER JOIN users_profile up ON u.id = up.user_id'
```

##### `.joins()` #####
```java
.joins(
        new Join(Join.INNER).table("users_profile{up}").on("{u}id", "{up}user_id"),
        new Join(Join.INNER, "users_data{ud}", "{u}id", "{ud}user_id")
)   // Generates 'INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN users_data ud ON u.id = ud.user_id'
```

##### `.innerJoin()` #####
```java
.innerJoin("users_profile{up}", "{u}id", "{up}user_id") // Generates 'INNER JOIN users_profile up ON u.id = up.user_id'
```

##### `.leftJoin()` #####
```java
.leftJoin("users_profile{up}", "{u}id", "{up}user_id") // Generates 'LEFT JOIN users_profile up ON u.id = up.user_id'
```

##### `.rigthJoin()` #####
```java
.rigthJoin("users_profile{up}", "{u}id", "{up}user_id") // Generates 'RIGHT JOIN users_profile up ON u.id = up.user_id'
```

#### Order ####

##### `.order()` #####
```java
.order(Order.by("{u}id", Order.DESC))   // Generates 'ORDER BY u.id DESC'
.order(Order.asc("{u}id"))              // Generates 'ORDER BY u.id ASC'
.order(Order.desc("{u}id"))             // Generates 'ORDER BY u.id DESC'
```

#### Limit ####
```java
.limit(10) // Generates 'LIMIT 10'
```

#### Union ####

##### `.union()` #####
```java
.union(new SelectBuilder().select("{u}id", "{u}name").from("users{u}")) // Generates 'UNION (SELECT u.id AS u_id, u.name AS u_name FROM users u)'
```

##### `.unionAll()` #####
```java
.unionAll(new SelectBuilder().select("{u}id", "{u}name").from("users{u}")) // Generates 'UNION ALL (SELECT u.id AS u_id, u.name AS u_name FROM users u)'
```

#### Aggregation ####
All possible [Aggregation]() are listed in his description
```java
.select(Aggregation.count("{u}id")) // Generates 'COUNT (u.id) AS count_u_id'
.select(Aggregation.count("{u}id", "custom_alias")) // Generates 'COUNT (u.id) AS custom_alias'
```
And the `Group By` clause constructed by
```java
.groupBy("{u}name") // Generates 'GROUP BY u.name'
```

Constructors
------------

#### `Condition` ####
Build conditions to be used in the [.where()](). Possible options:
```java
Condition.eq("{u}name", "Augusto")                              // Generates 'u.name = 'Augusto''
Condition.neq("{u}name", "John")                                // Generates 'u.name <> 'John''
Condition.in("{u}job", Arrays.asList("Developer", "Designer"))  // Generates 'u.job IN ('Developer', 'Designer')'
Condition.isNull("{u}deleted_at")                               // Generates 'u.deleted_at IS NULL'
Condition.isNotNull("{u}last_login")                            // Generates 'u.last_login IS NOT NULL'
Condition.like("{u}name", "usto")                               // Generates 'u.name LIKE '%usto%''
Condition.nlike("{u}name", "esar")                              // Generates 'u.name NOT LIKE '%esar%''
Condition.gt("{u}salary", 4000)                                 // Generates 'u.salary > 4000'
Condition.gte("{u}age", 21)                                     // Generates 'u.age >= 21'
Condition.lt("{u}login_attempts", 5)                            // Generates 'u.login_attempts < 5'
Condition.lte("{u}products", 5)                                 // Generates 'u.products <= 5'
Condition.and(
        Condition.eq("{u}name", "Augusto"),
        Condition.eq("{u}age", 23)
)                                                               // Generates 'u.name = 'Augusto' AND u.age = 23'
Condition.or(
        Condition.eq("{u}name", "Augusto"),
        Condition.eq("{u}last_name", "Silva")
)                                                               // Generates 'u.name = 'Augusto' OR u.last_name = 'Silva''
Condition.and(
        Condition.eq("{_}name", "Augusto"),
        Condition.gte("{_}age", 21),
        Condition.or(
                Condition.eq("{_}nationality", "Brazilian"),
                Condition.eq("{_}nationality", "Japanese"),
                Condition.and(
                        Condition.eq("{_}job", "Diplomat"),
                        Condition.eq("{_}job_active", true)
                )
        )
)                                                               // Generates 'name = 'Augusto' AND age >= 21 AND ( nationality = 'Brazilian' OR nationality = 'Japanese' OR ( job = 'Diplomat' AND job_active = true ) )'
```

#### `Join` ####
```java
new Join(Join.LEFT).table("user_profile{up}").on("{u}id", "{up}user_id")    // Generates 'LEFT JOIN user_profile up ON u.id = up.user_id'
new Join(Join.LEFT).table("user_profile{up}").on("{u}id = {up}user_id")     // Generates 'LEFT JOIN user_profile up ON u.id = up.user_id'
new Join(Join.INNER, "user_profile{up}", "{u}id", "{up}user_id")            // Generates 'INNER JOIN user_profile up ON u.id = up.user_id'
new Join(Join.INNER, "user_profile{up}", "{u}id = {up}user_id" )            // Generates 'INNER JOIN user_profile up ON u.id = up.user_id'
```

#### `Comparison` ####
Comparisons used if building inline [.where()] conditions.
```java
Comparison.EQUALS
Comparison.IN
Comparison.IS_NULL
Comparison.IS_NOT_NULL
Comparison.LIKE
Comparison.NOT_LIKE
Comparison.DIFFERENT
Comparison.GREATER_THAN
Comparison.GREATER_THAN_OR_EQUALS
Comparison.LESS_THAN
Comparison.LESS_THAN_OR_EQUALS
```

#### `Aggregation` ####
Aggregations used for creating [.select()] clause aggregations.
```java
Aggregation.AVERAGE
Aggregation.COUNT
Aggregation.MAX
Aggregation.MIN
Aggregation.SUM

Aggregation.average("{u}score") // Generates 'AVG (u.score) AS avg_u_score'
Aggregation.count("{u}id")      // Generates 'COUNT (u.id) AS cout_u_id'
Aggregation.max("{u}score")     // Generates 'MAX (u.score) AS max_u_score'
Aggregation.min("{u}score")     // Generates 'MIN (u.score) AS min_u_score'
Aggregation.sum("{u}score")     // Generates 'SUM (u.score) AS sum_u_score'
```
All `Aggregation` builders can have and extra parameter that is used to sed a custom alias
to the aggregation

Installation
------------

#### Gradle

```gradle
repositories {
    (...)
    maven { url 'https://jitpack.io' }
}
```

```gradle
dependencies {
    compile 'com.github.augustoccesar:QueryBuilder:$version'
}
```

#### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.augustoccesar</groupId>
    <artifactId>QueryBuilder</artifactId>
    <version>$version</version>
</dependency>
```