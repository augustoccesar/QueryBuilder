# QueryBuilder

Simple helper to build queries.

## Methods Usage

### new QueryBuilder()

Start building the query.

### .insert(InsertColumn... insertColumns)

```java
.insert(
    new InsertColumn("name"),
    new InsertColumn("username")
)

.insert(
    new InsertColumn("name", "Augusto Cesar"),
    new InsertColumn("username", "augustoccesar")
)
```

### .into(String tableName)

```java
.into("users")
```

### .withValues()

Define if want to build the insert SQL with values. Will throw `ColumnWithoutValue` exception if `new InsertColumn()` doesn't contain the second parameter, that is the value.

### .select(String... fieldsWithTableAlias)

```java
.select("i.name")

.select("i.id", "i.name")
```

### .count(String field)

```java
.count("i.id")
```

### .from(String tableWithAlias)

```java
.from("items i")

.from("users u")
```

### .join(Join.Type Type, String tableNameAndPrefix, String joinOn)

```java
.join(Join.Type.INNER_JOIN, "user_profile up", "u.id = up.user_id")

.join(Join.Type.LEFT_JOIN, "status s", "u.id = s.user_id")
```

### .conditions(Condition conditionBase)

```java
.conditions(
    Condition.build("u.id", Comparisons.EQUALS, Comparisons.VARIABLE)
)

.conditions(
    Condition.build("u.id", Comparisons.EQUALS, Comparisons.VARIABLE)
        .and(Condition.build("u.username", Comparisons.LIKE, Comparisons.VARIABLE))
        .or(Condition.build("u.id", Comparisons.GREATER_THAN, 20))
)
```

### .order(Order... orders)

```java
.order(
    Order.build("u.id", Order.Type.DESC)
)

.order(
    Order.build("u.id", Order.Type.DESC),
    Order.build("u.name", Order.Type.ASC)
)
```


### .limit(Long value)

```java
.limit(10L)

.limit(Long.valueOf(10))
```

### .offset(Long value)

```java
.offset(20L)

.offset(Long.valueOf(20))
```

### .build()

Generate the sql String.

## Examples

```java
QueryBuilder queryBuilder = new QueryBuilder()
                .insert(
                        new InsertColumn("name"),
                        new InsertColumn("username")
                )
                .into("user");
                
String sql = queryBuilder.build();
System.out.println(sql);
// Output: INSERT INTO user ( name , username ) VALUES ( ? , ? )
```

```java
QueryBuilder queryBuilder = new QueryBuilder()
                .insert(
                        new InsertColumn("name", "Augusto Cesar"),
                        new InsertColumn("username", "augustoccesar")
                )
                .into("user")
                .withValues();
                
String sql = queryBuilder.build();
System.out.println(sql);
// Output: INSERT INTO user ( name , username ) VALUES ( 'Augusto Cesar' , 'augustoccesar' )
```

```java
QueryBuilder queryBuilder = new QueryBuilder()
                .select("i.name", "i.value")
                .from("items i")
                .conditions(
                        Condition.build("i.id", Comparisons.EQUALS, Comparisons.VARIABLE)
                );
                
String sql = queryBuilder.build();
System.out.println(sql);
// Output: SELECT i.name AS i_name, i.value AS i_value FROM item i WHERE i.id = ?
```

```java
QueryBuilder subQuery = new QueryBuilder()
                .select("u.id")
                .from("user u")
                .join(Join.Type.INNER_JOIN, "user_profile up", "u.id = up.user_id")
                .conditions(
                        Condition.build("up.valid", Comparisons.EQUALS, true)
                );

QueryBuilder queryBuilder = new QueryBuilder()
        .select("i.id", "i.name")
        .from("items i")
        .conditions(
                Condition.build("i.user_id", Comparisons.IN, subQuery)
                            .and(Condition.build("i.deleted_at", Comparisons.IS_NULL, null))
        )
        .order(
                Order.build("i.id", Order.Type.DESC)
        );
        
String sql = queryBuilder.build();
System.out.println(sql);
// Output: SELECT i.id AS i_id, i.name AS i_name FROM items i WHERE i.user_id IN ( SELECT u.id AS u_id FROM user u INNER JOIN user_profile up ON u.id = up.user_id WHERE up.valid = true ) AND i.deleted_at IS NULL ORDER BY i.id DESC
```

## Next steps

- `update` methods.
- Tables cration.
- Triggers creation.