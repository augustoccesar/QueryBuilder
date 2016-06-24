# QueryBuilder
Simple helper to build SQL.

## Configuration
If `TriggerBuilder` or `TableBuilder` is going to be used, its needed to define which type of database is being used.
The only one current available is SQLite. To set it, in the beginning of the code (or in the start of the server) you need
to use the method in `Configuration`:
```java
Configuration.setDatabase(Database.SQLITE);
```

## Classes Constants

### ColumnType (Only SQLITE for now)
```java
ColumnType.VARCHAR
ColumnType.INTEGER
ColumnType.DECIMAL
ColumnType.BLOB
```

### Join.Type
```java
Join.Type.LEFT_JOIN
Join.Type.RIGHT_JOIN
Join.Type.INNER_JOIN
```

### Order.Type
```java
Order.Type.ASC
Order.Type.DESC
```

## Methods Usage of `SelectBuilder`

### new SelectBuilder()
Start building the select.

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

### .join(Join join)
```java
.join(
    new Join(Join.Type.INNER_JOIN).table("user_profile up").on("u.id = up.user_id")
)

.join(
    new Join(Join.Type.LEFT_JOIN).table("status s").on("u.id = s.user_id")
)
```

### .joins(Join... joins)
```java
.joins(
    new Join(Join.Type.INNER_JOIN).table("user_profile up").on("u.id = up.user_id"),
    new Join(Join.Type.LEFT_JOIN).table("status s").on("u.id = s.user_id")
)

```

### .where(Condition conditionBase)
```java
.where(
    new Condition().column("u.id").isEqualTo(Comparisons.VARIABLE)
)

.where(
    new Condition().column("u.id").isEqualTo(Comparisons.VARIABLE)
        .and(
            new Condition().column("u.username").isLike(Comparisons.VARIABLE)
        )
        .or(
            new Condition().column("u.id").isGreaterThan(20)
        )
)
```

### .order(Order... orders)
```java
.order(
    new Order().by("u.id", Order.Type.DESC)
)

.order(
    new Order().by("u.id", Order.Type.DESC),
    new Order().by("u.name", Order.Type.ASC)
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
Generate the select String.


## Methods Usage of `InsertBuilder`

### new InsertBuilder()
Start building the insert.

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
Define if want to build the insert with values. Will throw `ColumnWithoutValue` exception if `new InsertColumn()` doesn't contain the second parameter, that is the value.

### .build()
Generate the insert String.


## Methods Usage of `UpdateBuilder`

### new UpdateBuilder()
Start building the update.

### .table(String tableName)
```java
.table("users")
```

### .columns(UpdateColumn... updateColumns)
```java
.columns(
    new UpdateColumn().column("active").set(true)
)
```

### .where(Condition conditionBase)
```java
.where(
    new Condition().column("id").isEqualTo(Comparisons.VARIABLE)
)
```

### .withValues()
Define if want to build the insert with values. Will throw `ColumnWithoutValue` exception if `new UpdateColumn()` doesn't contain the second parameter, that is the value.

### .build()
Generate the update String.


## Methods Usage of `TriggerBuilder` (Only for SQLite for now)

### new TriggerBuilder()
Start building the trigger.

### .withName(String triggerName)
```java
.withName("after_insert_user")
```

### .on(String tableName)
```java
.on("user")
```

### .when(Time time, Action action)
```java
.when(Time.AFTER, Action.INSERT)
```

### .execute(QueryBuilder... whatToExecute)
```java
.execute(
        new InsertBuilder()
        .insert(
            new InsertColumn("user_id", new TriggerData(TriggerData.Object.NEW, "id"))
        )
        .into("user_profile")
        .withValues()
)
```

### .build()
Generate the trigger String.


## Methods Usage of `TableBuilder` (Only for SQLite for now)

### new TableBuilder()
Start building the table.

### .withName(String tableName)
```java
.withName("user_profile")
```

### .columns(CreateColumn... createColumns)
```java
.columns(
        new CreateColumn()
                .withName("id")
                .ofType(ColumnType.INTEGER)
                .primaryKey(true),
        new CreateColumn()
                .withName("user_id")
                .ofType(ColumnType.INTEGER)
                .nullable(false),
        new CreateColumn()
                .withName("name")
                .ofType(ColumnType.VARCHAR)
                .nullable(false)
)
```

### foreignKeys(ForeignKey... foreignKeys)
```java
.foreignKeys(
        new ForeignKey().column("user_id").references("user", "id")
)
```


## Examples

```java
QueryBuilder queryBuilder = new InsertBuilder()
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
QueryBuilder queryBuilder = new InsertBuilder()
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
QueryBuilder queryBuilder = new SelectBuilder()
                .select("i.name", "i.value")
                .from("items i")
                .where(
                    new Condition.column("i.id").isEqualTo(Comparisons.VARIABLE)
                );

String sql = queryBuilder.build();
System.out.println(sql);
// Output: SELECT i.name AS i_name, i.value AS i_value FROM item i WHERE i.id = ?
```

```java
QueryBuilder subQuery = new SelectBuilder()
                .select("u.id")
                .from("user u")
                .join(
                    new Join(Type.INNER_JOIN).table("user_profile up").on("u.id = up.user_id")
                )
                .where(
                    new Condition().column("up.valid").isEqualTo(true)
                );

QueryBuilder queryBuilder = new SelectBuilder()
        .select("i.id", "i.name")
        .from("items i")
        .where(
            new Condition().column("i.user_id").isIn(subQuery)
            .and(
                new Condition().column("i.deleted_at").isNull()
            )
        )
        .order(
            new Order().by("i.id", Order.Type.DESC)
        );

String sql = queryBuilder.build();
System.out.println(sql);
// Output: SELECT i.id AS i_id, i.name AS i_name FROM items i WHERE i.user_id IN ( SELECT u.id AS u_id FROM user u INNER JOIN user_profile up ON u.id = up.user_id WHERE up.valid = true ) AND i.deleted_at IS NULL ORDER BY i.id DESC
```

```java
QueryBuilder queryBuilder = new SelectBuilder()
        .select(
                "b.id", "b.link", "b.name",
                "aut.id", "aut.link", "aut.name",
                "bsh.promo", "bsh.position"
        )
        .from("book b")
        .joins(
                new Join(Join.Type.INNER_JOIN).table("author aut").on("b.author_id = aut.id"),
                new Join(Join.Type.LEFT_JOIN).table("bookshelf bsh").on("b.id = bsh.book_id")
        )
        .where(
                new Condition().column("bsh.store_id").isEqualsTo(3)
                .and(
                        new Condition().column("b.deleted_at").isNull()
                .and(
                        new Condition().column("bsh.state_id").isEqualsTo(1)
                        .or(
                                new Condition().column("bsh.state_id").isEqualsTo(2)
                        )
                ))
        )
        .order(
                new Order().by("bsh.position", Order.Type.ASC)
        )
        .limit((long) 10)
        .offset(0L);
String sql = queryBuilder.build();
System.out.println(sql);
// Output: SELECT b.id AS b_id, b.link AS b_link, b.name AS b_name, aut.id AS aut_id, aut.link AS aut_link, aut.name AS aut_name, bsh.promo AS bsh_promo, bsh.position AS bsh_position FROM book b INNER JOIN author aut ON b.author_id = aut.id LEFT JOIN bookshelf bsh ON b.id = bsh.book_id WHERE bsh.store_id = 3 AND ( b.deleted_at IS NULL AND ( bsh.state_id = 1 OR bsh.state_id = 2 ) ) ORDER BY bsh.position ASC LIMIT 10 OFFSET 0
```

## Next steps

- Multiple databases support
