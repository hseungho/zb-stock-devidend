# zb-stock-devidend

## API
### 특정 회사의 배당금 조회
```
Method: GET
URL: /finance/dividend/{companyName}
Content-Type: "application/json"

>> RESPONSE
{
  "companyName": string,
  "dividend": [
      {
        "date": string (e.g. yyyy. mm. dd),
        "price": double
      }
  ]
}
<<
```

### 배당금 검색 - 자동완성
```
Method: GET
URL: /company/autocomplete?keyword={keyword}
Content-Type: "application/json"

>> RESPONSE
{
  "result": string[]
}
<<
```

### 회사 리스트 조회
```
Method: GET
URL: /company
Content-Type: "application/json"

>> RESPONSE
{
  "result": [
    {
      "companyName": string,
      "ticker": string
    }
  ]
}
<<
```

### 관리자 기능 - 배당금 저장
```
Method: POST
URL: /company
Content-Type: "application/json"
        
>> REQUEST
{
  "ticker" : string
}
<<

>> RESPONSE
{
  "ticker": string,
  "companyName": string
}
<<
```

### 관리자 기능 - 배당금 삭제
```
Method: DELETE
URL: /company?ticker={ticker}
Content-Type: "application/json"
```

### 회원가입
```
Method: POST
URL: /signup
Content-Type: "application/json"

>> REQUEST
{
  "username": string,
  "password": string,
  "roles": string[]
}
<<

>> RESPONSE
{
  "username": string,
  "roles": string[],
  "createdAt": timestamp,
  "updatedAt": timestamp
}
<<
```

### 로그인
```
Method: POST
URL: /signin
Content-Type: "application/json"

>> REQUEST
{
  "username": string,
  "password": string
}
<<

>> RESPONSE
{
"username": string,
"roles": string[],
"createdAt": timestamp,
"updatedAt": timestamp
}
<<
```

