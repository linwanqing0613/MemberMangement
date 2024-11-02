# 會員管理系統

這是一個基於 Spring Boot 的會員管理系統，提供會員的新增、查詢、更新和刪除功能。

## 功能

- **會員管理**：新增、查詢、更新和刪除會員資訊。
- **條件查詢**：支持根據姓名、餘額和出生日期等條件進行查詢。
- **分頁查詢**：支持對會員資料進行分頁顯示。

## 技術棧

- **後端技術**：
  - Spring Boot
  - Spring Data JPA
  - H2 Database
  - Maven
- **測試**：
  - Spring Test

## 數據庫設計
### 會員表

以下是會員表的 SQL 建表語句：

```sql
CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(10, 2),
    birth_date DATE,
    age INT
);
```
## API 文檔

### 1. 新增會員

- **HTTP 方法**: `POST`
- **URL**: `/members`
- **RequestBody**:
  ```json
  {
    "name": "Alice",
    "balance": 1000.50,
    "birth_date": "1990-01-01",
    "age": 34
  }


### 2. 查詢所有會員
- **HTTP 方法**: `GET`
- **URL**: `/members`


### 3. 根據 ID 獲取會員
- **HTTP 方法**: `GET`
- **URL**: `/members/{id}`

### 4. 更新會員
- **HTTP 方法**: `PUT`
- **URL**: `/members/{id}`
- **RequestBody**:
    ```json
    {
        "name": "Alice Smith",
        "balance": 1200.00,
        "birth_date": "1990-01-01",
        "age": 34
    }

### 5. 刪除會員
- **HTTP 方法**: `DELETE`
- **URL**: `/members/{id}`

### 6. 根據條件查詢會員
- **HTTP 方法**: `GET`
- **URL**: `/members/search`
    - name (可選): 會員姓名
    - minBalance (可選): 最低餘額
    - maxBalance (可選): 最高餘額
    - birth_date (可選): 出生日期
    - age (可選): 年齡
    - page (可選): 分頁數
    - size (可選): 每頁大小
