# ReadLargeFile

We have a file of words , there can be 1 or more spaces between every 2 words. 
The file is approximately 10GB size. 
Now we need to upload the frequency of every word in a table as output.

- We'll be using BufferedReader for reading the data of max 4kb at a time
- For storing data, we are using PostgresQl 



Table Schema  -
```
TABLE words(
    id SERIAL PRIMARY KEY,
    word VARCHAR NOT NULL,
    occurs int
);

 ```




