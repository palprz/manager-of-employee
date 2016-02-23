# Manager Of Employee [August 2015]
Desktop application for managing employees, teams and projects in companies. Project contain 3 tabs: employee, team and project. User can create simple rows and connections between tabs.

## Technologies
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

## Connections
|                             | Employee                               | Team                                    | Project           |
|:---------------------------:|:--------------------------------------:|:---------------------------------------:|:----------------: |
| Single row contain          |ID, name, surname,<br /> salary, team, project|ID, name, project,<br /> team leader, employees|ID, name, teams    |
| Actions                     | add, update, remove                    |add, update, remove                      |add, update, remove|
| Contain single reference to | team, project                          | project                                 |                   |
| Contain multi reference to  |                                        |employee                                 | team              |

## Team leader
- every employee can be
- from the list of employees in team
- max 1 per team

## Screens from running application