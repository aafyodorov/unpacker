## Task:

### Условие: <br>
Написать на Java программу распаковывания строки. На вход поступает строка вида число[строка], на выход — строка, 
содержащая повторяющиеся подстроки.


### Пример: <br>

Вход: 3[xyz]4[xy]z
Выход: xyzxyzxyzxyxyxyxyz

### Ограничения: <br>

- одно повторение может содержать другое. Например: 2[3[x]y] = xxxyxxxy
- допустимые символы на вход: латинские буквы, числа и скобки []
- числа означают только число повторений
- скобки только для обозначения повторяющихся подстрок
- входная строка всегда валидна.


### Дополнительное задание: <br>


Проверить входную строку на валидность.

### Решение: <br>

Исходные коды выложить на GitHub или прислать по e-mail. Ссылки на GitHub и файлы с решением присылать на e-mail, 
указанный в письме с подтверждением регистрации на курс.

## Solution:

Test task was solved in two ways: recursive and iterative. <br>
Class Unpacker contains two public methods `unpackRecursion()` and `unpackIterative()`, which unpack the input string 
recursive or iterative accordingly. Method `Validator.validate()` validates resulting string. <br>
All test are in the `src/main/test/java`.
