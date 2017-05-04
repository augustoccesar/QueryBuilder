## v2.0.0 First Goals
The first goals to this new version, it should be able to do the following functionalities:

### Expected functions

```java
new QueryBuilder()
  .select("u.name")
  .from("users u");
// SELECT u.name AS u_name FROM users u
```

```java
new QueryBuilder("qb")
  .select("u.name")
  .from("users u");
// (SELECT u.name AS u_name FROM users u) AS qb
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}");
// SELECT u.name AS u_name FROM users u
```

```java
new QueryBuilder()
  .select("{u}name{custom_alias}")
  .from("users{u}");
// SELECT u.name AS custom_alias FROM users u
```

```java
new QueryBuilder()
  .select("{u}name{_}")
  .from("users{u}");
// SELECT u.name FROM users u
```

```java
new QueryBuilder()
  .select("{u}*name{_}")
  .from("users{u}");
// SELECT DISTINCT u.name FROM users u
```

```java
new QueryBuilder()
  .select("{u}name", "{up}phone")
  .from("users{u}")
  .join(Join.INNER, "user_profile{up}", "{u}id", "{up}user_id");
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id
```

```java
new QueryBuilder()
  .select("{u}name", "{up}phone")
  .from("users{u}")
  .innerJoin("user_profile{up}", "{u}id", "{up}user_id");
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id
```

```java
Join join = new Join(Join.INNER).table("user_profile{up}").on("{u}id", "{up}user_id")
new QueryBuilder()
  .select("{u}name", "{up}phone")
  .from("users{u}")
  .join(join);
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id
```

```java
Join join = new Join(Join.INNER).table("user_profile{up}").on("{u}id = {up}user_id")
new QueryBuilder()
  .select("{u}name", "{up}phone")
  .from("users{u}")
  .join(join);
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id
```

```java
Join join = new Join(Join.INNER).table("user_profile{up}").on("u.id = up.user_id")
new QueryBuilder()
  .select("{u}name", "{up}phone")
  .from("users{u}")
  .join(join);
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id
```

```java
Join join1 = new Join(Join.INNER).table("user_profile{up}").on("{u}id", "{up}user_id")
Join join2 = new Join(Join.INNER).table("user_data{ud}").on("{u}id", "{ud}user_id")
new QueryBuilder()
  .select("{u}name", "{up}phone", "{ud}public")
  .from("users{u}")
  .joins(join1, join2);
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id
```

```java
Join join1 = new Join(Join.INNER).table("user_profile{up}").on("{u}id", "{up}user_id")
Join join2 = new Join(Join.INNER).table("user_data{ud}").on("{u}id", "{ud}user_id")
new QueryBuilder()
  .select("{u}name", "{up}phone", "{ud}public")
  .from("users{u}")
  .join(join1)
  .join(join2);
// SELECT u.name AS u_name, up.phone AS up_phone FROM users u INNER JOIN users_profile up ON u.id = up.user_id INNER JOIN user_data ud ON u.id = ud.user_id
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}")
  .where("{u}name", Condition.EQUALS, "Augusto");
// SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}")
  .where(new Condition().column("{u}name").equals("Augusto"));
// SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}")
  .where(Condition.eq("{u}name", "Augusto"));
// SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto'
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}")
  .where(
    Condition.eq("{u}name", "Augusto"),
    Condition.gte("{u}age", 21)
  );
// SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' AND u.age >= 21
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}")
  .where(
    Condition.or(
      Condition.eq("{u}name", "Augusto"),
      Condition.eq("{u}name", "Teste")
    )
  );
// SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' OR u.name = 'Teste'
```

```java
new QueryBuilder()
  .select("{u}name")
  .from("users{u}")
  .where(
    Condition.eq("{u}name", "Augusto"),
    Condition.gte("{u}age", 21),
    Condition.or(
      Condition.eq("{u}nationality", "Brazilian"),
      Condition.eq("{u}nationality", "Italian")
    )
  );
// SELECT u.name AS u_name FROM users u WHERE u.name = 'Augusto' AND u.age >= 21 AND (u.nationality = 'Brazilian' OR u.nationality = 'Italian')
```

```java
Condition.EQUALS
Condition.DIFFERENT
Condition.IN
Condition.IS_NULL
Condition.IS_NOT_NULL
Condition.LIKE
Condition.NOT_LIKE
Condition.GREATER_THAN
Condition.GREATER_THAN_OR_EQUAL
Condition.LESS_THAN
Condition.LESS_OR_EQUAL
```

```java
Join.INNER
Join.LEFT
Join.RIGHT
```
