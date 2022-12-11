# Manager of Employee
Desktop application for managing employees, teams and projects in companies. Project contains 3 tabs: employee, team and project. User can create simple rows and connections between tabs.

## Technologies:
1. Frontend:
  - Swing
2. Backend:
  - Java 7
  - Maven ver. 1.8
  - Hibernate ver. 4.3.6
  - log4j ver. 1.2.17
  - JUnit ver. 4.11
  - Mockito ver. 1.8.4
3. Database:
  - MySQL(XAMPP) ver. 3.2.1

## Tabs description:
|                             | Employee                               | Team                                    | Project           |
|:---------------------------:|:--------------------------------------:|:---------------------------------------:|:----------------: |
| Single row contain          |ID, name, surname,<br /> salary, team, project|ID, name, project,<br /> team leader, employees|ID, name, teams    |
| Actions                     | add, update, remove                    |add, update, remove                      |add, update, remove|
| Contain single reference to | team, project                          | project                                 |                   |
| Contain multi reference to  |                                        |employee                                 | team              |

## Team leader:
- every employee can be
- from the list of employees in team
- max 1 per team

## Screens from running application

1) Employee tab:<br />
![alt text][employee_tab]

2) Team tab:<br />
![alt text][team_tab]

3) Project tab:<br />
![alt text][project_tab]

4) Main options:<br />
![alt text][options_menu]

5) Employee menu tab:<br />
![alt text][employee_menu]

5) Employee window from menu tab:<br />
![alt text][employee_menu_window]

[employee_tab]: https://github.com/palprz/manager-of-employee/blob/master/markdown_img_employee_tab.png
[team_tab]: https://github.com/palprz/manager-of-employee/blob/master/markdown_img_team_tab.png
[project_tab]: https://github.com/palprz/manager-of-employee/blob/master/markdown_img_project_tab.png
[options_menu]: https://github.com/palprz/manager-of-employee/blob/master/markdown_img_options_menu.png
[employee_menu]: https://github.com/palprz/manager-of-employee/blob/master/markdown_img_employee_menu.png
[employee_menu_window]: https://github.com/palprz/manager-of-employee/blob/master/markdown_img_employee_menu_window.png
