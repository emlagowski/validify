# Validify - Java validation library

![GitHub tag (latest SemVer)](https://img.shields.io/github/v/tag/emlagowski/validify?style=for-the-badge)
![GitHub](https://img.shields.io/github/license/emlagowski/validify?style=for-the-badge)

This library purpose is to make validation process more straightforward. 
It helps to validate objects and keep information about errors occurred during validation.

## Usage example

You can combine multiple validators with logical operators like 'AND' and 'OR'. 

Example below shows how to achieve validation that checks if 
(value is 1-2 length string with only digits characters)
or 
(value is predefined 'N/A' value)
or
(value is one of other predefined allowed values).

File: `io.github.emlagowski.validify.CoreValidationsExample`
```java
List<String> allowedValues = Arrays.asList("A", "B", "C");
String allowedValue = "N/A";

Validation<String> validation = allCharactersAreDigits().and(lengthInRange(1, 2))
        .or(is(allowedValue))
        .or(isIn(allowedValues));

ValidationResult validationResult = validation.apply("value that not apply");

validationResult.getMessages().forEach(validationMessage -> System.out.println(validationMessage.getMessage()));
```

Result of above is

```shell script
Not all character are digits in 'value that not apply'
Length of 'value that not apply' not in range [1, 2]
'value that not apply' is not 'N/A'
'value that not apply' is not in [A, B, C]
```
