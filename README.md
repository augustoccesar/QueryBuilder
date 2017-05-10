<<<<<<< HEAD
# QueryBuilder [![Build Status](https://travis-ci.org/augustoccesar/QueryBuilder.svg?branch=develop)](https://travis-ci.org/augustoccesar/QueryBuilder) [![Code Climate](https://codeclimate.com/github/augustoccesar/QueryBuilder/badges/gpa.svg)](https://codeclimate.com/github/augustoccesar/QueryBuilder) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/f203baba091846e3922fabf893f569e6?branch=develop)](https://www.codacy.com/app/augustoccesar/QueryBuilder?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=augustoccesar/QueryBuilder&amp;utm_campaign=Badge_Grade)
=======
# QueryBuilder 
[![Build Status](https://travis-ci.org/augustoccesar/QueryBuilder.svg?branch=master)](https://travis-ci.org/augustoccesar/QueryBuilder) 
[![Coverage Status](https://coveralls.io/repos/github/augustoccesar/QueryBuilder/badge.svg?branch=master)](https://coveralls.io/github/augustoccesar/QueryBuilder?branch=master) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f203baba091846e3922fabf893f569e6?branch=master)](https://www.codacy.com/app/augustoccesar/QueryBuilder?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=augustoccesar/QueryBuilder&amp;utm_campaign=Badge_Grade) 
[![GitHub version](https://badge.fury.io/gh/augustoccesar%2FQueryBuilder.svg)](https://badge.fury.io/gh/augustoccesar%2FQueryBuilder)
>>>>>>> hotfix/add-readme-to-master

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