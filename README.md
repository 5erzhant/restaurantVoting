[![Codacy Badge](https://app.codacy.com/project/badge/Grade/13ac94aa173a49eaabdbe03fe01c4a89)](https://app.codacy.com/gh/5erzhant/restaurantVoting/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

Проектирование и реализация REST API с использованием Hibernate/Spring/SpringMVC (предпочтительно Spring-Boot!) без фронтенда.

Задача:

Построить систему голосования для принятия решения о том, где пообедать.

2 типа пользователей: admin и обычные пользователи
Администратор может ввести ресторан и его обеденное меню дня (обычно 2-5 позиций, только название блюда и цена)
Меню меняется каждый день (обновления делают администраторы)
Пользователи могут проголосовать за ресторан, в котором они хотят пообедать сегодня
Учитывается только один голос для каждого пользователя
Если пользователь проголосует повторно в тот же день:
Если это до 11:00, мы предполагаем, что он передумал.
Если это после 11:00, то уже слишком поздно, голосование не может быть изменено
Каждый ресторан предлагает новое меню каждый день.

P.S.: Убедиться, что все работает с последней версией, которая есть на github :)
P.P.S.: API будет использоваться фронтенд-разработчиком для создания фронтенда поверх этого.

