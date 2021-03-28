# exchange_rates
Fetch latest &amp; period exchange rates for given currency

#### API End Points:
1. Get Latest: `GET: /exchange/{base}/{currency}`
2. Get Historic: `GET: /exchange/historic/{startDate}/{endDate}`
3. Config Period: `GET: /config/{cronExpression}`

#### DB Script:
```
CREATE TABLE exchange_rates
   (
   exchange_date_time timestamp without time zone PRIMARY KEY,
   base_currency character varying(20) NOT NULL,
   destination_currency character varying(20) NOT NULL,
   exchange_value DECIMAL(20, 10) NOT NULL
   );
```

#### Requirement details:
1. Java version:(Java 15) openjdk 15 2020-09-15
2. DB: Postgresql (table name:exchange_rates)
3. API to fetch exchange rates, I've used: https://exchangerate.host/#. Base API: https://api.exchangerate.host